package com.decathlon.finance.taxreport.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public class ExcelUtil {

    public static boolean isVersion2003(String fileName ) throws IOException
    {
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        boolean is2003 = false;
        if ("xls".equals(extension)) {
            is2003 =  true;
        } else if ("xlsx".equals(extension)) {
            is2003 =false;
        } else {
            throw new IOException("不支持的文件类型");
        }
        return is2003;
    }

    public void exportMainDataExcel(String title, String[] headers, List<Map<String, Object>> dataset, HttpServletResponse response) {
        HSSFWorkbook book = this.createBookAndSheet(headers, dataset);
        this.downloadFile(title, book, response);
    }

    private HSSFWorkbook createBookAndSheet(String[] headers, List<Map<String, Object>> dataset) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("companyInfo");
        sheet.setDefaultColumnWidth(14);
        // Header Field Style
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setColor(HSSFColor.WHITE.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(font);
        // Data Field Style
        HSSFCellStyle style2 = workbook.createCellStyle();
        // style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);

        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        return workbook;
    }

    private void downloadFile(String title, HSSFWorkbook workbook, HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("application/vnd..ms-excel");
            response.setHeader("content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(title + "_"+ new Date().getTime() + ".xls", "utf-8"));
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFiscalNumber(String costCenterNum)
    {
        String fiscalNum = "";
        if(costCenterNum != null)
        {
            fiscalNum = "0"+costCenterNum.substring(1,5);
            fiscalNum = Integer.valueOf(fiscalNum).toString();
        }
        return fiscalNum;
    }

    public static String getStoreNum(String costCenterNum)
    {
        String storeNum = "";
        if(costCenterNum != null)
        {
            storeNum = costCenterNum.substring(6);
            storeNum = Integer.valueOf(storeNum).toString();
        }
        return  storeNum;
    }
}
