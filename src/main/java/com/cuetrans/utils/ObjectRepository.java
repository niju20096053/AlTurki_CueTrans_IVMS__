package com.cuetrans.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ObjectRepository {

	public static String[][] getAllObjects(String sheetName , String browserName) throws IOException {
		String[][] data = null;
		int i, j ;
		String cellValue = "";
		XSSFSheet wSheet;

		try {
			FileInputStream fis = new FileInputStream("./data/" + sheetName + ".xlsx");
			XSSFWorkbook wBook = new XSSFWorkbook(fis);
			/*if(browserName.equalsIgnoreCase("android"))
			{*/
				 wSheet = wBook.getSheet("ObjectRepository");
			/*}
			else
			{
				 wSheet = wBook.getSheet("WebObjectRepository");
			}*/
			int rowCount = wSheet.getLastRowNum() + 1;
			int colCount = wSheet.getRow(0).getLastCellNum() ;
			data = new String[rowCount][colCount-1];
			int k = 0;
			for (i = 0; i < rowCount; i++) {
				try {
					XSSFRow row = wSheet.getRow(i);
					int l= 0;
					for (j = 0; j < colCount; j++) {
					if(j%2==0) {
						try {
							cellValue = row.getCell(j).getStringCellValue();
						} catch (Exception e) {
							e.printStackTrace();
						}

						data[k][l] = cellValue;
						
						l++;
					}
					}k++;
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
	
	
	
	public static String[][] getAllObjectLocators(String sheetName , String browserName) throws IOException {
		String[][] data = null;
		int i, j;
		String cellValue = "";
		XSSFSheet wSheet;

		try {
			FileInputStream fis = new FileInputStream("./data/" + sheetName + ".xlsx");
			XSSFWorkbook wBook = new XSSFWorkbook(fis);
			/*if(browserName.equalsIgnoreCase("android"))
			{*/
				 wSheet = wBook.getSheet("ObjectRepository");
			/*}
			else
			{
				 wSheet = wBook.getSheet("WebObjectRepository");
			}*/
			int rowCount = wSheet.getLastRowNum() + 1;
			int colCount = wSheet.getRow(0).getLastCellNum() ;
			data = new String[rowCount][colCount-1];
			for (i = 0; i < rowCount; i++) {
				try {
					XSSFRow row = wSheet.getRow(i);
					for (j = 0; j < colCount-1; j++) {
					
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
