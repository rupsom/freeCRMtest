package com.crm.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.crm.qa.base.TestBase;

public class TestUtil extends TestBase{

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 10;
	public static String screenshotName = null;
	
	public static String TESTDATA_SHEET_PATH = "D:\\Selenium_Projects\\FreeCRMTest\\src\\main\\java"
			+ "\\com\\crm\\qa\\testdata\\FreeCRMTestData.xlsx";
	
	static Workbook book;
	static Sheet sheet;
	static Cell cell;
	public static int runRowNum;
	
	public static void switchToFrame(String name){
		driver.switchTo().frame(name);
	}
	
	public static void switchToFrame(int number){
		driver.switchTo().frame(number);
	}
	
	public static void captureScreenshot(String testMethodName) throws IOException{
		
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		FileUtils.copyFile(srcFile, new File("D:/Selenium_Projects/FreeCRMTest/screenshots/"+testMethodName+"_"+screenshotName));
	}
	
	public static Object[][] getTestData(String sheetName) throws FileNotFoundException{
		FileInputStream file = new FileInputStream(TESTDATA_SHEET_PATH);
		
		try {
			book = WorkbookFactory.create(file);
			sheet = book.getSheet(sheetName);
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		for(int i = 0; i < sheet.getLastRowNum(); i++){
			for(int k = 0; k < sheet.getRow(0).getLastCellNum(); k++){
				data[i][k] = sheet.getRow(i+1).getCell(k).toString();
//				runRowNum = i+1;
//				System.out.println(runRowNum);
			}
		}
		
		return data;
		
	}
}
