package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.crm.qa.listeners.WebEventListener;
import com.crm.qa.util.TestUtil;

public class TestBase {

	public static WebDriver driver = null;
	public static Properties prop = null;
	public static Logger log = Logger.getLogger(TestBase.class);
	
	public static EventFiringWebDriver e_driver = null;
	public static WebEventListener eventListener;
	
	public TestBase(){
		try{
			prop = new Properties();
			FileInputStream ip = new FileInputStream("D:\\Selenium_Projects\\FreeCRMTest\\src\\main\\"
					+ "java\\com\\crm\\qa\\config\\config.properties");
			prop.load(ip);
			log.info("Loaded Prop Info");
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void initialize(){
		String browserName = prop.getProperty("browser");

			if(browserName.equals("chrome")){
				System.setProperty("webdriver.chrome.driver", "D:\\Selenium_Projects\\FreeCRMTest\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("Chrome open");
				log.debug("Chrome Debug");
			}
			else if (browserName.equals("firefox")){
				System.setProperty("webdriver.gecko.driver", "D:\\Selenium_Projects\\FreeCRMTest\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
			
		else{
			log.info("Browser is unavailable");
		}
		
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		log.info("App URL is launched - "+prop.getProperty("url"));
	}
}
