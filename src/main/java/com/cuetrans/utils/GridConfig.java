package com.cuetrans.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GridConfig {

	public static String[][] getGridConfig(String sheetName) throws IOException {
		String[][] data = null;
		int i, j;
		String cellValue = "";

		try {
			FileInputStream fis = new FileInputStream("./data/" + sheetName + ".xlsx");
			XSSFWorkbook wBook = new XSSFWorkbook(fis);
			XSSFSheet wSheet = wBook.getSheet("GridProperties");
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
