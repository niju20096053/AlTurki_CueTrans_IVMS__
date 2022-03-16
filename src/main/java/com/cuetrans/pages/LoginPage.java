package com.cuetrans.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.cuetrans.wrappers.ProjectWrappers;
import com.relevantcodes.extentreports.ExtentTest;

public class LoginPage extends ProjectWrappers {

	public LoginPage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {

		this.driver = driver;
		this.test = test;

	Thread.sleep(5000);
		/*if (!verifyTitle("Login")) {
			reportStep("This is not the Login Page", "FAIL");
		}*/
	}

	public LoginPage enterUserId(String userId) {
		enter(locatorProp.getProperty("Login.Username"),prop.getProperty("Login.Username"), userId);
		return this;
	}
	
	public LoginPage enterPassword(String password) {
		enter(locatorProp.getProperty("Login.Password"), prop.getProperty("Login.Password"), password);
		return this;
	}
	
	public HomePage clickSubmit() throws InterruptedException {
		click(locatorProp.getProperty("Login.Login"), prop.getProperty("Login.Login"));
		Thread.sleep(10000);
		return new HomePage(driver, test);
	}
	
	public LoginPage enterPasswordWrong(String password) {
		enter(locatorProp.getProperty("Login.Password"), prop.getProperty("Login.Password"), password);
		return this;
	}
}
