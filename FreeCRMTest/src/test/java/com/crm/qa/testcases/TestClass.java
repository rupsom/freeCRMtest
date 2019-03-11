package com.crm.qa.testcases;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Random;


public class TestClass
{
	    //This code will be your start point of execution
		@Test
	    public void testCode()
	    {    	
	        Random randomNo = new Random();
	        Date d = new Date();
	        TestListenerAdapter listener = new TestListenerAdapter();
	        TestNG testng = new TestNG();

	        //Here you are changing Output directory and archive it for further 
	        //use, OUTPUT FOLDER WILL BE ADDED BY APPPENDING RANDOM NUMBER ON IT
	        testng.setOutputDirectory("test-output"+randomNo.nextInt()+"_"+d.toString().replace(":", "_").replace(" ", "_"));

	        //ADD ALL TEST CLASSES WHERE YOUR TESTNG CODE IS PRESENT WITH @Test 
	        testng.setTestClasses(new Class[]{LoginPageTest.class});
	        testng.addListener(listener);
	        testng.run();
	    }
}
