package com.cuetrans.utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestCaseDetailsProvider {
	public static String[][] getAllTestDetails(String sheetName) throws IOException {
		String[][] data = null;
		int i,j;
		String cellValue = "";
		
		try {
				FileInputStream fis = new FileInputStream("./data/"+sheetName+".xlsx");
				XSSFWorkbook wBook = new XSSFWorkbook(fis);
				XSSFSheet wSheet = wBook.getSheet("TestDetails");
				int rowCount = wSheet.getLastRowNum() + 1;
				int colCount = wSheet.getRow(0).getLastCellNum();
				data = new String[rowCount][colCount];
				for (i = 0; i < rowCount; i++) {
					try {
						XSSFRow row = wSheet.getRow(i);
						for (j = 0; j < colCount; j++) {
							try {
							//if (row.getCell(j).getCellType() == 1) {
								cellValue = row.getCell(j).getStringCellValue();
							//} else if (row.getCell(j).getCellType() == 0) {
								//cellValue = "" + row.getCell(j).getNumericCellValue();
							//}
							}
							catch(Exception e) {
								
							}

							data[i][j] = cellValue;
						}
					} catch (NullPointerException e) {
						e.printStackTrace();

					}
				}
				wBook.close();

			} catch (NullPointerException e) {
				e.printStackTrace();

			}
		
		return data;
	}
	
	}