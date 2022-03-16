package com.cuetrans.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.cuetrans.wrappers.ProjectWrappers;
import com.relevantcodes.extentreports.ExtentTest;

public class CreateVehiclePage extends ProjectWrappers {

	public CreateVehiclePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {

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
	
	public CreateVehiclePage enterVehicleCode() throws InterruptedException {
		enter(locatorProp.getProperty("CreateVechicle.Code"),prop.getProperty("CreateVehicle.Code"), prop.getProperty("VehicleCode"));
		Thread.sleep(5000);
		return this;
	}
	
	public CreateVehiclePage enterVehicleDescription() throws InterruptedException {
		enter(locatorProp.getProperty("CreateVechicle.Desc"),prop.getProperty("CreateVehicle.Desc"), prop.getProperty("VehicleDesc"));
		Thread.sleep(5000);
		return this;
	}
	
	
}
