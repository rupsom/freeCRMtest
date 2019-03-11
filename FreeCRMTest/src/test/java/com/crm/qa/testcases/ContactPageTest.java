package com.crm.qa.testcases;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;

public class ContactPageTest extends TestBase{
	
	public HomePage homePage = null;
	public LoginPage loginPage = null;
	public ContactsPage contactsPage = null;
	
	String sheetName = "contacts";
	
	@BeforeMethod
	public void setup() throws InterruptedException
	{
		initialize();
		contactsPage = new ContactsPage();
		loginPage = new LoginPage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		TestUtil.switchToFrame("navmenu");
		contactsPage = homePage.clickOnContactsLink();
	}
	
	@DataProvider
	public Object[][] getCRMTestData() throws IOException{
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}
	
	
	@Test(priority=1, dataProvider="getCRMTestData")
	public void validateCreateContact(String title, String firstName, String lastName, String company){
		homePage.clickOnNewContactLink();
		contactsPage.createNewContact(title, firstName, lastName, company);
	}

}
