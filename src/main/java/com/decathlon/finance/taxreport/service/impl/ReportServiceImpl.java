package com.decathlon.finance.taxreport.service.impl;

import com.decathlon.finance.taxreport.config.CustomConfigUtils;
import com.decathlon.finance.taxreport.model.*;
import com.decathlon.finance.taxreport.service.FiscalInfoService;
import com.decathlon.finance.taxreport.service.PersonInfoService;
import com.decathlon.finance.taxreport.service.ReportService;
import com.decathlon.finance.taxreport.service.StoreInfoService;
import com.decathlon.finance.taxreport.util.Arith;
import com.decathlon.finance.taxreport.util.Constants;
import com.decathlon.finance.taxreport.util.ExcelUtil;
import com.decathlon.finance.taxreport.util.StringUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.*;
import java.util.*;

@Service(value = "reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CustomConfigUtils config;

    @Autowired
    StoreInfoService storeInfoService;

    @Autowired
    PersonInfoService personInfoService;

    @Autowired
    FiscalInfoService fiscalInfoService;

    DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    DecimalFormat twoDf = new DecimalFormat("######0.00");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

    public HttpEntity<byte[]> generatePdf(String fileName, InputStream blIs, InputStream glIs, String reportType, String companyType, String builderName) throws IOException, JRException {
        HttpEntity<byte[]> httpEntity = null ;
        boolean is2003 = ExcelUtil.isVersion2003(fileName);
        if(Constants.REPORT_TYPE_VOUCHER.equals(reportType))
        {
            List lst = generateVoucher(is2003,glIs,companyType, builderName);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lst);
//            InputStream inputStream = getClass().getResourceAsStream(config.getPath()+Constants.JRXML_VOUCHER);
            InputStream inputStream = getClass().getResourceAsStream(Constants.JRXML_VOUCHER);
            httpEntity = generate(dataSource,inputStream,Constants.REPORT_TYPE_VOUCHER);
        }
        else
        {
            List lst = generateSubInfo(is2003, blIs, glIs,companyType);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lst);
            InputStream inputStream = getClass().getResourceAsStream(Constants.JRXML_SUBLEDGER);
            httpEntity = generate(dataSource,inputStream,Constants.REPORT_TYPE_SUBLEDGER);
        }
        return httpEntity;
    }


    private HttpEntity<byte[]> generate( JRBeanCollectionDataSource dataSource, InputStream inputStream, String type) throws JRException
    {
        String filename = "temp.jasper";
        File compiledFile = new File(filename);
        JasperReport jr;

        if (compiledFile.exists()) {
            compiledFile.delete();
        }
        // Convert template to JasperDesign
        JasperDesign jd = JRXmlLoader.load(inputStream);
        // Compile design to JasperReport
        jr = JasperCompileManager.compileReport(jd);
        JRSaver.saveObject(jr, filename);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, new HashMap<String, Object>(), dataSource);
        final byte[] data = getReportPdf(jasperPrint);

        HttpHeaders header = new HttpHeaders();
        //header.setContentType(MediaType.APPLICATION_PDF);
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+type+"-"+df.format(new Date())+".pdf");
        header.setContentLength(data.length);
        compiledFile.delete();
        return new HttpEntity<>(data, header);
    }


    /**
     * Get and Fill Balance date to map.
     */
    private Map<String, Balance> getBalanceData(boolean is2003, InputStream is) throws IOException {
        Map<String, Balance> balanceMap = new HashMap<String, Balance>();
        Workbook workbookBalance;
        Sheet hssfSheetBalance;
        if (is2003) {
            workbookBalance = new HSSFWorkbook(is);
            is.close();
            hssfSheetBalance = (HSSFSheet)workbookBalance.getSheetAt(0);
        } else {
            workbookBalance = new XSSFWorkbook(is);
            is.close();
            hssfSheetBalance = (XSSFSheet)workbookBalance.getSheetAt(0);
        }
        for (int rowNum = 1; rowNum <= hssfSheetBalance.getLastRowNum(); rowNum++) {

            Balance bLce = new Balance();
            SourceData ssD = new SourceData();
            Row hssfRow = hssfSheetBalance.getRow(rowNum);
            Cell currencyCode = hssfRow.getCell(7);
            if (currencyCode == null) continue;
            String currCodeString = currencyCode.getStringCellValue();
            if(!currCodeString.equals("")&&!currCodeString.equals("CNY")) continue;
            Cell codeCell = hssfRow.getCell(0);
            if (codeCell == null) continue;
            bLce.setCode(codeCell.getStringCellValue());
            Cell companyCell = hssfRow.getCell(1);
            if (companyCell == null) continue;
            bLce.setCompany(companyCell.getStringCellValue());
            Cell yearCell = hssfRow.getCell(2);
            if (yearCell == null) continue;
            bLce.setYear(String.valueOf(decimalFormat.format(yearCell.getNumericCellValue())));
            Cell dateCell = hssfRow.getCell(3);
            if (dateCell == null) continue;
            String date = String.valueOf(decimalFormat.format(dateCell.getNumericCellValue()));
            bLce.setMonth(date.substring(4));
            Cell balanceCell = hssfRow.getCell(4);
            if (balanceCell == null) continue;
            bLce.setBalance_m(balanceCell.getNumericCellValue());
            Cell balanceDecMCell = hssfRow.getCell(5);
            if (balanceDecMCell == null) continue;
            bLce.setBalance_dec_y(balanceDecMCell.getNumericCellValue());
            Cell lastMonthCell = hssfRow.getCell(6);
            if (lastMonthCell == null) continue;
            bLce.setBalance_last_m(lastMonthCell.getNumericCellValue());
            balanceMap.put(codeCell.getStringCellValue() + date, bLce);
        }
        return balanceMap;
    }


    /**
     * Get source data which is sorted.
     *
     */
    private List<SourceData> getSortedSourceData(boolean is2003, InputStream is, String reportType, String companyType) throws IOException
    {
        List<SourceData> sortedList = new ArrayList<SourceData>();
        Workbook workbook;
        Sheet hssfSheetBalance;
        if (is2003) {
            workbook = new HSSFWorkbook(is);
            is.close();
            hssfSheetBalance = (HSSFSheet)workbook.getSheetAt(0);
        } else {
            workbook = new XSSFWorkbook(is);
            is.close();
            hssfSheetBalance = (XSSFSheet)workbook.getSheetAt(0);
        }
        if (workbook != null) {
            Sheet hssfSheet = workbook.getSheetAt(0);
            // 循环行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                SourceData sd = new SourceData();
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow == null) continue;
                Cell code1Cell = hssfRow.getCell(0);
                if (code1Cell == null) continue;
                sd.setCode1(code1Cell.getStringCellValue());
                Cell subICACell = hssfRow.getCell(1);
                if (subICACell == null) continue;
                sd.setSubIca(subICACell.getStringCellValue());
                Cell codeCell = hssfRow.getCell(2);
                if (codeCell == null||(codeCell!=null&&"".equals(codeCell.getStringCellValue()))) continue;
                sd.setCode(codeCell.getStringCellValue());
                Cell catCodeDESCCell = hssfRow.getCell(3);
                if (catCodeDESCCell == null) continue;
                sd.setCatCodeDesc(catCodeDESCCell.getStringCellValue());
                Cell companyCell = hssfRow.getCell(4);
                if (companyCell == null) continue;
                sd.setCompany(companyCell.getStringCellValue());
                Cell dateCell = hssfRow.getCell(12);
                Cell yearMonthCell = hssfRow.getCell(8);
                if (yearMonthCell == null) continue;
                String yearMonth = String.valueOf(decimalFormat.format(yearMonthCell.getNumericCellValue()));
                if (dateCell == null) continue;
                String date = dateCell.toString();
                int dayIndexOf = date.indexOf("-");
                if(dayIndexOf == -1)
                {
                    dayIndexOf = date.indexOf("/");
                }
                String day = date.substring(0,dayIndexOf);
                if(day.length()<2)
                {
                    day = "0"+day;
                }
                sd.setDate(yearMonth+day);
                sd.setYear(yearMonth.substring(0,4));
                sd.setMonth(yearMonth.substring(4));
//                if(yearMonth.substring(4).startsWith("0"))
//                {
//                    sd.setMonth(yearMonth.substring(5));
//                }
                sd.setDay(day);
                Cell costCenterCell = hssfRow.getCell(5);
                if (costCenterCell == null) continue;
                sd.setCostCenter(costCenterCell.getStringCellValue());
                Cell docTypeCell = hssfRow.getCell(9);
                if (docTypeCell == null) continue;
                sd.setDocType(docTypeCell.getStringCellValue());
                Cell batchTypeCell = hssfRow.getCell(10);
                if (batchTypeCell == null) continue;
                sd.setBatchType(batchTypeCell.getStringCellValue());
                Cell docNumCell = hssfRow.getCell(11);
                if (docNumCell == null) continue;
                NumberFormat nf = NumberFormat.getInstance();
                String docNum= nf.format(docNumCell.getNumericCellValue()).replace(",","");
                sd.setDocNum(docNum);
                Cell amountCell = hssfRow.getCell(13);
                if (amountCell == null) continue;
                sd.setAmount(twoDf.format(amountCell.getNumericCellValue()));
                Cell explainCell = hssfRow.getCell(17);
                if (explainCell == null) continue;
                sd.setExplain(explainCell.getStringCellValue());
                Cell explainRemCell = hssfRow.getCell(18);
                if (explainRemCell == null) continue;
                sd.setExplainRem(explainRemCell.getStringCellValue());
                Cell rem2Cell = hssfRow.getCell(19);
                if (rem2Cell == null) continue;
                sd.setRem2(rem2Cell.getStringCellValue());
                sortedList.add(sd);
            }
            if(Constants.REPORT_TYPE_SUBLEDGER.equals(reportType))
            {
                Collections.sort(sortedList, new Comparator<SourceData>() {
                    @Override
                    public int compare(SourceData o1, SourceData o2) {
                        int i = -1;
                        if(!"".equals(o1.getCode()) && !"".equals(o2.getCode())) {
                            i = Integer.parseInt(o1.getCode()) - Integer.parseInt(o2.getCode());
                            //i = StringUtil.compareCode(o1.getCode(),o2.getCode());
                            if(i == 0){
                                DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                                try {
                                    i = df1.parse(o1.getDate()).compareTo(df1.parse(o2.getDate()));
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                        return i;
                    }
                });
            }
            else
            {
                Collections.sort(sortedList, new Comparator<SourceData>() {
                    @Override
                    public int compare(SourceData o1, SourceData o2) {
                        int i = -1;
                        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                        try {
                            i = df1.parse(o1.getDate()).compareTo(df1.parse(o2.getDate()));
                            if(i == 0)
                            {
                                i = o1.getBatchType().compareTo(o2.getBatchType());
                                if(i == 0)
                                {
                                    i = o1.getBatchType().compareTo(o2.getBatchType());
                                    if(i == 0 )
                                    {
                                        i = o1.getDocNum().compareTo(o2.getDocNum());
                                    }
                                }
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        return i;
                    }
                });
            }

        }
        return sortedList;
    }

    /**
     * Get All Voucher info
     * @param is2003
     * @param is
     * @return
     * @throws IOException
     */
    private List<VoucherInfo> generateVoucher(boolean is2003, InputStream is, String companyType, String builderName) throws IOException
    {
        List<SourceData> sourceDataList = new ArrayList<SourceData>();
        List<VoucherInfo> voucherInfoList = new ArrayList<VoucherInfo>();
        Map<String,String> storeInfoMap = storeInfoService.getStoreInfoMaps();
        Map<String,String> fiscalInfoMap = fiscalInfoService.getFiscalInfoMaps();
        List<VoucherItem> vouItemList = null;
        sourceDataList = getSortedSourceData(is2003, is, Constants.REPORT_TYPE_VOUCHER, companyType);
        PersonInfo pi = personInfoService.getAllPersonInfo().get(0);
        String managerName = pi.getManagerName();
        String recheckName =  pi.getRecheckName();
        String tempPageInfo = "";
        VoucherInfo vi = null;
        double totalDedit = 0;
        double totalCredit = 0;

        for(SourceData s: sourceDataList) {
            VoucherItem vitem = new VoucherItem();
            String itemPageInfo = s.getYear() + s.getMonth() + s.getDay() + s.getBatchType()+ s.getDocNum();
            if (!tempPageInfo.equals(itemPageInfo)) {
                if (tempPageInfo != "") {
                    VoucherItem lastVi = new VoucherItem("",Constants.STATIC_TEXT_TOTAL_VOUCHER,"","","",String.valueOf(totalDedit),String.valueOf(totalCredit));
                    vouItemList.add(lastVi);
                    JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(vouItemList);
                    vi.setTabledata(itemsJRBean);
                    voucherInfoList.add(vi);
                    totalDedit = 0;
                    totalCredit = 0;
                }
                tempPageInfo = itemPageInfo;
                vouItemList = new ArrayList<VoucherItem>();
                vi = new VoucherInfo();
                vi.setYear(s.getYear());
                vi.setMonth(s.getMonth());
                vi.setDay(s.getDay());
                if(Constants.COMPANY_TYPE_FISCAL.equals(companyType))
                {
                    String fiscalNo = ExcelUtil.getFiscalNumber(s.getCostCenter());
                    vi.setCompanyname(fiscalInfoMap.get(fiscalNo));
                }
                else
                {
                    String storeNo = ExcelUtil.getStoreNum(s.getCostCenter());
                    vi.setCompanyname(storeInfoMap.get(storeNo));
                }
                vi.setDoctype(s.getBatchType());
                vi.setDocnum(s.getDocNum());
                vi.setManager(managerName);
                vi.setRecheck(recheckName);
                vi.setBuilder(builderName);
            }
            if(s.getRem2()!=null && s.getRem2().equals(""))
            {
                vitem.setExplanation(s.getExplain()+"\n"+s.getExplainRem());
            }
            else
            {
                vitem.setExplanation(s.getExplain());
            }
            vitem.setCodedesc(s.getCatCodeDesc());
            vitem.setCorrespondingat("成本中心:"+s.getCostCenter()+"\n科目:"+s.getCode()+"\nICA:"+s.getCode1()+"."+s.getSubIca());
            vitem.setCuramount("");
            vitem.setExcrat("");
            if(Double.parseDouble(s.getAmount()) > 0 ? true : false)
            {
                double absDebit = Math.abs(Double.parseDouble(s.getAmount()));
                vitem.setDebit(twoDf.format(absDebit));
                vitem.setCredit("");
                totalDedit = Arith.add(totalDedit,absDebit);
            }
            else
            {
                double absCebit = Math.abs(Double.parseDouble(s.getAmount()));
                vitem.setCredit(twoDf.format(absCebit));
                vitem.setDebit("");
                totalCredit = Arith.add(totalCredit,absCebit);
            }
            vouItemList.add(vitem);
        }
        VoucherItem lastVi = new VoucherItem("",Constants.STATIC_TEXT_TOTAL_VOUCHER,"","","",String.valueOf(totalDedit),String.valueOf(totalCredit));
        vouItemList.add(lastVi);
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(vouItemList);
        vi.setTabledata(itemsJRBean);
        voucherInfoList.add(vi);
        return voucherInfoList;
    }

    /**
     * Get All subledger info.
     * @param is2003
     * @param glInputStream
     * @return
     * @throws IOException
     */
    private List<SubledgerInfo> generateSubInfo(boolean is2003, InputStream blInputStream , InputStream glInputStream, String companyType)
            throws IOException {
        XSSFWorkbook workbook = null;
        XSSFWorkbook workbookBalance = null;
        List<SourceData> sortedList = new ArrayList<SourceData>();
        List<SubledgerInfo> subledgerinfoList = new ArrayList<SubledgerInfo>();
        Map<String,String> storeInfoMap = storeInfoService.getStoreInfoMaps();
        Map<String,String> fiscalInfoMap = fiscalInfoService.getFiscalInfoMaps();
        Map<String, Balance> balanceMap = null;
        List<SubledgerItem> subItemList = null;
        double balance = 0;
        double totalBorrow = 0;
        double totalLoan = 0;

        //get Balance data
        balanceMap = getBalanceData(is2003,blInputStream);
        sortedList = getSortedSourceData(is2003,glInputStream,Constants.REPORT_TYPE_SUBLEDGER,companyType);
        //finish sorted list

        //start to build pdf map.
        String tempPageInfo = "";
        SubledgerInfo si = null;
            for(SourceData s: sortedList)
            {

                SubledgerItem sitem = new SubledgerItem();
                String itemPageInfo = s.getCode()+s.getYear()+s.getMonth();
                if(!tempPageInfo.equals(itemPageInfo))
                {
                    if(tempPageInfo!="")
                    {
                        SubledgerItem lastRecord = new SubledgerItem("","","","",Constants.STATIC_TEXT_TOTAL_SUBLEDGER,twoDf.format(totalBorrow),twoDf.format(totalLoan),"",twoDf.format(Math.abs(balance)));
                        totalBorrow = 0;
                        totalLoan = 0;
                        balance = 0;
                        subItemList.add(lastRecord);
                        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(subItemList);
                        si.setTabledata(itemsJRBean);
                        subledgerinfoList.add(si);
                    }
                    tempPageInfo = itemPageInfo;
                    subItemList = new ArrayList<SubledgerItem>();
                    si = new SubledgerInfo();
                    si.setAccountCode(s.getCode());
                    si.setAccountDesc(s.getCatCodeDesc());
                    if(Constants.COMPANY_TYPE_FISCAL.equals(companyType))
                    {
                        String fiscalNo = ExcelUtil.getFiscalNumber(s.getCostCenter());
                        si.setCompanyinfo(fiscalInfoMap.get(fiscalNo));
                    }
                    else
                    {
                        String storeNo = ExcelUtil.getStoreNum(s.getCostCenter());
                        si.setCompanyinfo(storeInfoMap.get(storeNo));
                    }
                    si.setYear(s.getYear());
                    si.setMonth(s.getMonth());
                    SubledgerItem firstRecord = new SubledgerItem(s.getMonth(),"","","",Constants.STATIC_TEXT_INNITIAL_SUBLEDGER,"","","","0.00");
                    Balance bc = balanceMap.get(tempPageInfo);
                    if(bc!= null)
                    {
                        if(Integer.valueOf(s.getMonth()) == 1)
                        {
                            balance = bc.getBalance_dec_y();
                            firstRecord.setTbalance(twoDf.format(balance));
                        }
                        else
                        {
                            balance = bc.getBalance_last_m();
                            firstRecord.setTbalance(twoDf.format(balance));
                        }
                    }
                    subItemList.add(firstRecord);
                }

                sitem.setTmonth(s.getMonth());
                sitem.setTday(s.getDay());
                sitem.setTcostcenter(s.getCostCenter());
                sitem.setTdocnum(s.getDocNum());
                sitem.setTexplain(s.getExplain());
                double amount = Double.parseDouble(s.getAmount());
                balance = Arith.add(balance,amount);
                sitem.setTbalance(twoDf.format(Math.abs(balance)));
                if(amount >0)
                {
                    sitem.setTborrow(twoDf.format(Math.abs(amount)));
                    sitem.setTloan("");
                    totalBorrow = Arith.add(totalBorrow,Math.abs(amount));
                }
                else
                {
                    sitem.setTloan(twoDf.format(Math.abs(amount)));
                    sitem.setTborrow("");
                    totalLoan = Arith.add(totalLoan,Math.abs(amount));
                }
                if(balance>0)
                {
                    sitem.setTflag(Constants.STATIC_TEXT_DEBIT_SUBLEDGER);
                }
                else
                {
                    if(balance == 0)
                    {
                        sitem.setTflag(Constants.STATIC_TEXT_LEVEL_SUBLEDGER);
                    }
                    else
                    {
                        sitem.setTflag(Constants.STATIC_TEXT_CREDIT_SUBLEDGER);
                    }
                }
                subItemList.add(sitem);
            }
            SubledgerItem lastRecord = new SubledgerItem("","","","",Constants.STATIC_TEXT_TOTAL_SUBLEDGER,twoDf.format(totalBorrow),twoDf.format(totalLoan),"",twoDf.format(Math.abs(balance)));
            subItemList.add(lastRecord);
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(subItemList);
            si.setTabledata(itemsJRBean);
        subledgerinfoList.add(si);
        return subledgerinfoList;
    }

    private byte[] getReportPdf(JasperPrint jp) throws JRException {
        return JasperExportManager.exportReportToPdf(jp);
    }

    private byte[] getReportCsv(final JasperPrint jp) throws IOException, JRException {
        JRCsvExporter csvExporter = new JRCsvExporter();
        final byte[] rawBytes;
        try (ByteArrayOutputStream csvReport = new ByteArrayOutputStream()) {
            csvExporter.setExporterInput(new SimpleExporterInput(jp));
            csvExporter.setExporterOutput(new SimpleWriterExporterOutput(csvReport));
            csvExporter.exportReport();

            rawBytes = csvReport.toByteArray();
        }
        return rawBytes;
    }

    private byte[] getReportXlsx(final JasperPrint jp) throws JRException, IOException {
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        final byte[] rawBytes;

        try (ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
            xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            xlsxExporter.exportReport();

            rawBytes = xlsReport.toByteArray();
        }
        return rawBytes;
    }

    private String getReportHtml(final JasperPrint jp) throws IOException, JRException {
        HtmlExporter htmlExporter = new HtmlExporter();
        final String rawBytes ;

        try (ByteArrayOutputStream htmlReport = new ByteArrayOutputStream()) {
            htmlExporter.setExporterInput(new SimpleExporterInput(jp));
            htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(htmlReport));
            htmlExporter.exportReport();

            rawBytes = htmlReport.toString();
        }
        return rawBytes;

    }


    public void writeResponseAsPdf(final JasperPrint jp, HttpServletResponse response) throws JRException, IOException {
        FileCopyUtils.copy(JasperExportManager.exportReportToPdf(jp), response.getOutputStream());
    }


    public void writeResponseAsXlsx(final JasperPrint jp, HttpServletResponse response) throws IOException, JRException {
        final byte[] rawBytes;
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        try (ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
            xlsxExporter.setExporterInput(new SimpleExporterInput(jp));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            xlsxExporter.exportReport();

            FileCopyUtils.copy(xlsReport.toByteArray(), response.getOutputStream());

        }
    }
}
