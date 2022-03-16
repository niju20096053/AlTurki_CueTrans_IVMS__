package com.cuetrans.utils;

import java.util.ArrayList;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.cuetrans.wrappers.ProjectWrappers;

public class TestResultCount extends ProjectWrappers implements ITestListener {
	
	public TestResultCount() {
		
	}

	public static int passedCount , failedCount , skippedCount , totalCount;
	
	public static List<ITestNGMethod> totalTests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> passedTests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> failedTests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> skippedTests = new ArrayList<ITestNGMethod>();
		
		
		
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
		System.out.println("Test Execution Started...");
		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
		totalTests.add(result.getMethod());
		passedTests.add(result.getMethod());
		//prop.setProperty("PassedTestCount", passedTests.size());
		
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		totalTests.add(result.getMethod());
		failedTests.add(result.getMethod());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		totalTests.add(result.getMethod());
		skippedTests.add(result.getMethod());
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		totalTests.add(result.getMethod());
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		

		
		
		context.getStartDate();
		
	}

	@Override 
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
		
		context.getEndDate();
		
	}

}
