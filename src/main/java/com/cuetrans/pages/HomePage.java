package com.cuetrans.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.cuetrans.wrappers.ProjectWrappers;
import com.relevantcodes.extentreports.ExtentTest;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class HomePage extends ProjectWrappers {

	public HomePage(RemoteWebDriver driver, ExtentTest test) throws InterruptedException {
		this.driver = driver;
		this.test = test;
		Thread.sleep(5000);

		/*
		 * if (!verifyTitle("Welcome to BCT Intranet")) {
		 * reportStep("This is not the BCT Internet Home page", "FAIL"); }
		 */
	}

	public HomePage verifyHeader() throws InterruptedException {

		if (driver.findElement(By.id(prop.getProperty("Home.Header"))).isDisplayed()) {
			System.out.println("Logged in succesfully...");
			ATUReports.add("Verify Element Present", "Login Successful", "Logged in to Order page", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			ATUReports.add("Verify Element Not Present", "UnSuccessful Login", "Failed to open Order page",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return this;
	}

	public HomePage getUserName() throws InterruptedException {
		getTextByClassName(prop.getProperty("Home.Username"));
		Thread.sleep(5000);
		return new HomePage(driver, test);
	}
	
	public HomePage clickHome() throws InterruptedException {
		click(locatorProp.getProperty("Home.Home"), prop.getProperty("Home.Homme"));
		Thread.sleep(5000);
		return new HomePage(driver, test);
	}

	public BusinessParameterPage openBusinessParameter() throws InterruptedException {
		click(locatorProp.getProperty("Home.Business"), prop.getProperty("Home.Business"));
		Thread.sleep(5000);
		return new BusinessParameterPage(driver, test);
	}

	public CalendarPage openCalendar() throws InterruptedException {
		click(locatorProp.getProperty("Home.Calendar"), prop.getProperty("Home.Calendar"));
		Thread.sleep(5000);
		return new CalendarPage(driver, test);
	}

	public CarrierPage openCarrier() throws InterruptedException {
		click(locatorProp.getProperty("Home.Carrier"), prop.getProperty("Home.Carrier"));
		Thread.sleep(5000);
		return new CarrierPage(driver, test);
	}

	public ChecklistPage openChecklist() throws InterruptedException {
		click(locatorProp.getProperty("Home.Checklist"), prop.getProperty("Home.Checklist"));
		Thread.sleep(5000);
		return new ChecklistPage(driver, test);
	}

	public ConstraintPage openConstraint() throws InterruptedException {
		click(locatorProp.getProperty("Home.Constraint"), prop.getProperty("Home.Constraint"));
		Thread.sleep(5000);
		return new ConstraintPage(driver, test);
	}

	public DriverPage openDriver() throws InterruptedException {
		click(locatorProp.getProperty("Home.Driver"), prop.getProperty("Home.Driver"));
		Thread.sleep(5000);
		return new DriverPage(driver, test);
	}

	public EmployeePage openEmployee() throws InterruptedException {
		click(locatorProp.getProperty("Home.Employee"), prop.getProperty("Home.Employee"));
		Thread.sleep(5000);
		return new EmployeePage(driver, test);
	}

	public LocationPage openLocation() throws InterruptedException {
		click(locatorProp.getProperty("Home.Location"), prop.getProperty("Home.Location"));
		Thread.sleep(5000);
		return new LocationPage(driver, test);
	}

	public NumberingPage openNumbering() throws InterruptedException {
		click(locatorProp.getProperty("Home.Numbering"), prop.getProperty("Home.Numbering"));
		Thread.sleep(5000);
		return new NumberingPage(driver, test);
	}

	public RoutePage openRoute() throws InterruptedException {
		click(locatorProp.getProperty("Home.Route"), prop.getProperty("Home.Route"));
		Thread.sleep(5000);
		return new RoutePage(driver, test);
	}

	public VehiclePage openVehicle() throws InterruptedException {
		click(locatorProp.getProperty("Home.Vehicle"), prop.getProperty("Home.Vehicle"));
		Thread.sleep(5000);
		return new VehiclePage(driver, test);
	}

	public ViolationPage openViolation() throws InterruptedException {
		click(locatorProp.getProperty("Home.Violation"), prop.getProperty("Home.Violation"));
		Thread.sleep(5000);
		return new ViolationPage(driver, test);
	}

	public SafeJourneyPage openSafeJourney() throws InterruptedException {
		click(locatorProp.getProperty("Home.SafeJourney"), prop.getProperty("Home.SafeJourney"));
		Thread.sleep(5000);
		return new SafeJourneyPage(driver, test);
	}
}
