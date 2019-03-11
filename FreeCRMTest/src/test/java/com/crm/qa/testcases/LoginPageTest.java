package com.crm.qa.testcases;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Random;

import javax.imageio.ImageIO;

import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.asprise.ocr.Ocr;
import com.crm.qa.base.TestBase;
import com.crm.qa.listeners.CustomListener;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

@Listeners(CustomListener.class)
public class LoginPageTest extends TestBase{

	public LoginPage loginPage = null;
	public HomePage homePage = null;
	
	public LoginPageTest(){
		super();
		log.debug("Initialize Properties from Base");
	}
	
	@BeforeMethod
	public void setUp(){	
		initialize();
		loginPage = new LoginPage();
	}
	
	@Test(priority=1)
	public void loginPageTitleTest(){
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, "#1 Free CRM software in the cloud for sales and service");
		log.info("Validation Title of Site");
	}
	
	@Test(priority=2, dataProvider="getLoginTestData")
	public void loginTest(String Username, String Password, String run) throws InterruptedException{
		
		if(run.equalsIgnoreCase("Y")){
			homePage = loginPage.login(Username,Password);
			Assert.assertEquals(homePage.verifyHomePageTitle(),"CRMPRO");
			log.info("Validation Login");
		}
		
	}
	
	@Test(priority=3, enabled=false)
	public void imageRead(){
		
//		BufferedImage image = ImageIO.read(new File("Image location"));   
//		String imageText = new Ocr().recognizeCharacters((RenderedImage) image);  
//		System.out.println("Text From Image : \n"+ imageText);  
//		System.out.println("Length of total text : \n"+ imageText.length()); 
		
		Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English

        String s = ocr.recognize(new File[] { new File("C:\\Users\\rrouth\\Desktop\\images.jpg") },
                Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        System.out.println(s);
        ocr.stopEngine();
        
	}
	
	@Test(priority=4, enabled=false)
	public void jdbCtest() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				
		String dbURL = "jdbc:sqlserver://dcsqldev01;databaseName=OP_UAT;integratedSecurity=true";
		Connection conn = DriverManager.getConnection(dbURL);
		if (conn != null) {
		    System.out.println("Connected");
		}
		Statement stmt=conn.createStatement();  
		ResultSet rs=stmt.executeQuery("select top 10 * from calls");  
		while(rs.next())  
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
		conn.close();  
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
	@DataProvider
	public Object[][] getLoginTestData() throws IOException{
		Object [][] data = TestUtil.getTestData("LoginTest");
		return data;
	}
}
