package com.cuetrans.utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MailConfig {

	public static String[][] getMailConfig(String sheetName) throws IOException {
		String[][] data = null;
		int i, j;
		String cellValue = "";
		XSSFSheet wSheet;

		try {
			FileInputStream fis = new FileInputStream("./data/" + sheetName + ".xlsx");
			XSSFWorkbook wBook = new XSSFWorkbook(fis);

			wSheet = wBook.getSheet("MailProperties");

			int rowCount = wSheet.getLastRowNum() + 1;
			int colCount = wSheet.getRow(0).getLastCellNum();
			data = new String[rowCount][colCount];
			for (i = 0; i < rowCount; i++) {
				try {
					XSSFRow row = wSheet.getRow(i);
					for (j = 0; j < colCount; j++) {
						try {
							cellValue = row.getCell(j).getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
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
