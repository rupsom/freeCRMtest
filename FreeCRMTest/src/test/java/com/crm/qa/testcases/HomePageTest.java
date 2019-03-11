package com.crm.qa.testcases;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class HomePageTest extends TestBase{
	
	LoginPage loginPage =null;
	HomePage homePage = null;
	
	public HomePageTest(){
		super();
		log.info("Initialize Properties from Base");
	}
	
	@BeforeMethod
	public void setup() throws InterruptedException{
		initialize();
		loginPage = new LoginPage();
		homePage = new HomePage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test(priority=1)
	public void verifyHomePageTitleTest(){
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(homePageTitle, "CRMPRO");
	}
	
	@Test(priority=2)
	public void verifyUserNameTest(){
		TestUtil.switchToFrame("mainpanel");
		String userName = homePage.verifyUserName();
		Assert.assertEquals(userName, "User: Naveen K");
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
}
