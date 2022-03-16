package com.cuetrans.testcases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.cuetrans.pages.LoginPage;
import com.cuetrans.wrappers.ProjectWrappers;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class , com.cuetrans.utils.TestResultCount.class })

public class VehicleMaster_Create extends ProjectWrappers {
	
	{
		System.setProperty("atu.reporter.config", "./resources/atu.properties");
		ATUReports.currentRunDescription = "Test cases for Create Vehicle";
	}
	
	@BeforeSuite
	public void setSheetName() {
		dataSheetName = "AlTurki";
	}
	
	@Test(dataProvider = "fetchData")
	public void loginPass(String userId, String password) throws InterruptedException {

		ATUReports.setAuthorInfo(prop.getProperty("Authors"), Utils.getCurrentTime(), "1.0");
		ATUReports.setWebDriver(driver);
		ATUReports.setTestCaseReqCoverage("Test case for Create Vehicle");

		new LoginPage(driver, test)
		.enterUserId(userId)
		.enterPassword(password)
		.clickSubmit()
		.getUserName()
		.openVehicle()
		.clickCreateVehicle()
		.enterVehicleCode()
		.enterVehicleDescription();
		
	}   

}
