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

public class BusinessParameter extends ProjectWrappers {
	{
		System.setProperty("atu.reporter.config", "./resources/atu.properties");
		ATUReports.currentRunDescription = "Test cases for CueTrans login Pass, Fail and Skip";
	}
	
	@BeforeSuite
	public void setSheetName() {
		dataSheetName = "AlTurki";
	}
      
	@Test(dataProvider = "fetchData")
	public void loginPass(String userId, String password) throws InterruptedException {

		ATUReports.setAuthorInfo(prop.getProperty("Authors"), Utils.getCurrentTime(), "1.0");
		ATUReports.setWebDriver(driver);
		ATUReports.setTestCaseReqCoverage("Test case for BCT Login pass");

		new LoginPage(driver, test)
		.enterUserId(userId)
		.enterPassword(password)
		.clickSubmit()
		.getUserName()
		.openBusinessParameter();
		
	}   
	
	/*@Test(dataProvider = "fetchData")
	public void loginFail(String userId, String password) throws InterruptedException {

		ATUReports.setAuthorInfo(prop.getProperty("Authors"), Utils.getCurrentTime(), "1.0");
		ATUReports.setWebDriver(driver);
		ATUReports.setTestCaseReqCoverage("Test case for BCT Login fail");

		new LoginPage(driver, test).enterUserId(userId).enterPasswordWrong(password).clickSubmit();

	}

	@Test(dataProvider = "fetchData", dependsOnMethods = "loginFail")
	public void loginSkip(String userId, String password) throws InterruptedException {

		ATUReports.setAuthorInfo(prop.getProperty("Authors"), Utils.getCurrentTime(), "1.0");
		ATUReports.setWebDriver(driver);
		ATUReports.setTestCaseReqCoverage("Test case for BCT Login fail");

		new LoginPage(driver, test).enterUserId(userId).enterPasswordWrong(password).clickSubmit();

	}

	@Test(dataProvider = "fetchData", dependsOnMethods = "loginSkip")
	public void loginSkip2(String userId, String password) throws InterruptedException {

		ATUReports.setAuthorInfo(prop.getProperty("Authors"), Utils.getCurrentTime(), "1.0");
		ATUReports.setWebDriver(driver);
		ATUReports.setTestCaseReqCoverage("Test case for BCT Login fail");

		new LoginPage(driver, test).enterUserId(userId).enterPasswordWrong(password).clickSubmit();

	}*/
}
