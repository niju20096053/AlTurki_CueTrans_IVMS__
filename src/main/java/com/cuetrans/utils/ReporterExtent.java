package com.cuetrans.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class ReporterExtent extends ReporterLogger{

	public ExtentTest test;
	public static int failedTestCount = 0 ;
	public static ExtentReports extent;
	protected static Properties prop;
	public String testCaseName, testDescription, category, authors;
	long snapNumber;

	public void reportStep(String desc, String status) {
		reportStep(desc, status, true);
	}

	public void reportStep(String desc, String status, boolean bSnap) {

		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./resources/object.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bSnap && !status.equalsIgnoreCase("WARN")) {

			try {
				snapNumber = takeSnap();
			} catch (Exception e) {
				e.printStackTrace();
			}
			desc = desc + test.addScreenCapture("./reports/images//*"
					+ /* "SnapSotTC001 */prop.getProperty("TestCaseName") + "SnapShot" + snapNumber + ".jpg");
		}

		if (status.equalsIgnoreCase("PASS")) {
			test.log(LogStatus.PASS, desc);
		} else if (status.equalsIgnoreCase("FAIL")) {
			
			failedTestCount++;
			String failed = ""+failedTestCount;
			prop.setProperty("FailedTestCount", failed);
			
						
			test.log(LogStatus.FAIL, desc);
			throw new RuntimeException("Failed");
		} else if (status.equalsIgnoreCase("WARN")) {
			test.log(LogStatus.WARNING, desc);
		} else if (status.equalsIgnoreCase("INFO")) {
			test.log(LogStatus.INFO, desc);
		}

	}

	public abstract long takeSnap();

	public ExtentReports startResult() {
		extent = new ExtentReports("./reports/report.html", false);
		extent.loadConfig(new File("./resources/extent-config.xml"));
		return extent;

	}

	public void endResult() {
		extent.flush();
	}

	public ExtentTest startTestCase(String testCaseName, String testDescription) {
		test = extent.startTest(testCaseName, testDescription);
		return test;

	}

	public void endTestCase() {
		extent.endTest(test);
	}


}
