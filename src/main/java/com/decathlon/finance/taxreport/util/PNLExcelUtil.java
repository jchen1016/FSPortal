package com.decathlon.finance.taxreport.util;



import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;



public class PNLExcelUtil {
	
	/**
	 * 对外提供读取excel 的方法（根据文件名后缀名判断是2003还是2007excel文件）
	 */
	public static Object readExcel(String fileName,InputStream is) throws IOException {
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
		            .substring(fileName.lastIndexOf(".") + 1);
		
		if ("xls".equals(extension)) {
			return read2003Excel(is);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(is);
           // return  null;

		} else {
			throw new IOException("不支持的文件类型");
		}

	}
	
    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static Object read2003Excel(InputStream inputStream) throws IOException {
        //List<MonthlyParameter> list = new ArrayList<>();
        HSSFWorkbook workbook = null;
        try {
            // 读取Excel文件
            workbook = new HSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 循环工作表
        if (workbook != null) {
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                HSSFSheet hssfSheet = workbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow == null) continue;
                    //CellRangeAddress c = CellRangeAddress.valueOf("B1");

                    // 将单元格中的内容存入集合
                    /*
                    MonthlyParameter entity = new MonthlyParameter();
                    entity.setDataType("REALIZE");
                    
                    HSSFCell cYear = hssfRow.getCell(0);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setYearIn(ExcelUtil.getStringCellValue(cYear));
                    
                    HSSFCell cPlatform = hssfRow.getCell(1);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setPlatform(ExcelUtil.getStringCellValue(cPlatform));
                    
                    HSSFCell cParaDefId = hssfRow.getCell(2);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setParaDefId(ExcelUtil.getLongCellValue(cParaDefId));
                    
                    HSSFCell cSport = hssfRow.getCell(4);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setSportId(ExcelUtil.getIntegerCellValue(cSport));
                    
                    HSSFCell cM01 = hssfRow.getCell(6);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM01(ExcelUtil.getDoubleCellValue(cM01));
                    
                    HSSFCell cM02 = hssfRow.getCell(7);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM02(ExcelUtil.getDoubleCellValue(cM02));
                    
                    HSSFCell cM03 = hssfRow.getCell(8);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM03(ExcelUtil.getDoubleCellValue(cM03));
                    
                    HSSFCell cM04 = hssfRow.getCell(9);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM04(ExcelUtil.getDoubleCellValue(cM04));
                    
                    HSSFCell cM05 = hssfRow.getCell(10);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM05(ExcelUtil.getDoubleCellValue(cM05));
                    
                    HSSFCell cM06 = hssfRow.getCell(11);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM06(ExcelUtil.getDoubleCellValue(cM06));
                    
                    HSSFCell cM07 = hssfRow.getCell(12);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM07(ExcelUtil.getDoubleCellValue(cM07));
                    
                    HSSFCell cM08 = hssfRow.getCell(13);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM08(ExcelUtil.getDoubleCellValue(cM08));
                    
                    HSSFCell cM09 = hssfRow.getCell(14);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM09(ExcelUtil.getDoubleCellValue(cM09));
                    
                    HSSFCell cM10 = hssfRow.getCell(15);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM10(ExcelUtil.getDoubleCellValue(cM10));
                    
                    HSSFCell cM11 = hssfRow.getCell(16);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM11(ExcelUtil.getDoubleCellValue(cM11));
                    
                    HSSFCell cM12 = hssfRow.getCell(17);
                    cYear.setCellType(HSSFCell.CELL_TYPE_STRING);
                    entity.setM12(ExcelUtil.getDoubleCellValue(cM12));
                    
                    list.add(entity);
                    */
                    return "Finish";
                }
            }
        }
        return null;
    }

    /**
     * 读取Office 2007 excel
     */
    private static Object read2007Excel(InputStream inputStream)
	            throws IOException {
        XSSFWorkbook workbook = null;
            try {
            // 读取Excel文件
            workbook = new XSSFWorkbook(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
    }
    return "Finish";
    }
}