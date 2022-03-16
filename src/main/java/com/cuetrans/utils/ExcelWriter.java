package com.cuetrans.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	public boolean generateExcel(Map<String, String> inputMap) throws IOException {

		try {
		// Create a Workbook
		FileInputStream fis =  new FileInputStream(new File("./data/BCT2.xlsx")); // new HSSFWorkbook() for generating `.xls` file
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		/* CreationHelper helps us create instances for various things like DataFormat, 
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		XSSFSheet sheet = workbook.getSheet("TestDetails");//.getRow(1).getCell(1).setCellValue(value);*/createSheet("TestDesc");
		
		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		int rowNum = 0;
		

		for(java.util.Map.Entry<String, String> entry : inputMap.entrySet()) {
			int cellNumb = 0;
			String key = entry.getKey();
			String value = entry.getValue();
			XSSFRow row = sheet.getRow(rowNum);     //.getCell(2); //createRow(rowNum++);
			row.getCell(cellNumb).setCellValue(key);//createCell(0).setCellValue(key);
			cellNumb++;
			row.getCell(cellNumb).setCellValue(value);
			rowNum++;
			
		}
		
		//workbook.close();

		// Resize all columns to fit the content size
		/*for(int i = 0; i < 2; i++) {
			sheet.autoSizeColumn(i);
		}
*/
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(new File("./data/BCT2.xlsx"));
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
		return true;
		}
		catch(Exception e) {
			return false;
//			e.printStackTrace();
		}
	}
}
