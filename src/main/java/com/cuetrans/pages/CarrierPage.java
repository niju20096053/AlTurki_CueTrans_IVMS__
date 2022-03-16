package com.cuetrans.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.cuetrans.wrappers.ProjectWrappers;
import com.relevantcodes.extentreports.ExtentTest;

public class CarrierPage extends ProjectWrappers {

	public CarrierPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {

		this.driver = driver;
		this.test = test;

	Thread.sleep(5000);
		/*if (!verifyTitle("Login")) {
			reportStep("This is not the Login Page", "FAIL");
		}*/
	}

	public HomePage clickHome() throws InterruptedException {
		click(locatorProp.getProperty("Home.Home"), prop.getProperty("Home.Home"));
		Thread.sleep(5000);
		return new HomePage(driver, test);
	}
}
