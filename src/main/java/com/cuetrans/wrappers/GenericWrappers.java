package com.cuetrans.wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.cuetrans.utils.ReporterExtent;
import com.relevantcodes.extentreports.ExtentTest;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

/*
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
*/
public class GenericWrappers extends ReporterExtent implements Wrapper {

	public RemoteWebDriver driver;
	protected static Properties prop;
	protected static Properties locatorProp;
	public String sReturn, rUrl, gText, gTitle, alertText, parWindow, rHubUrl, rHubPort, rPlatform, rBrowser,
			portNumber;
	public static long snapNumb = 1001;
	public int initCount , finalCount;
	
	public Actions action ;

	public GenericWrappers() {
		Properties prop = new Properties();
		try {

			rHubUrl = prop.getProperty("rHub");
			rHubPort = prop.getProperty("rPort");
			rUrl = prop.getProperty("URL");
			rPlatform = prop.getProperty("rPlatform");
			rBrowser = prop.getProperty("rBrowser");

		} catch (Exception e) {

			loggerError("The GRID properties file cannot be loaded from Excel sheet");
			e.printStackTrace();
		}
	}

	public GenericWrappers(RemoteWebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public void loadObjects() {
		prop = new Properties();
		locatorProp = new Properties();
		try {
			prop.load(new FileInputStream(new File("./resources/object.properties")));
			// loggerInfo("The Objects property file has been loaded successfully");
			// locatorProp.load(new FileInputStream(new
			// File("./resources/locator.properties")));
		} catch (FileNotFoundException e) {
			loggerError("The Objects property file is not found");
			e.printStackTrace();
		} catch (IOException e) {
			loggerError("The Objects property file cannot be loaded");
			e.printStackTrace();
		}

	};

	public void unloadObjects() {
		prop = null;
		locatorProp = null;
	}

	public void invokeApp(String browser) {
		invokeApp(browser, false);
	}

	public void invokeApp(String browser, boolean bRemote) {
		// TODO Auto-generated method stub
		try {

			if (bRemote) {
				try {
					DesiredCapabilities dc = new DesiredCapabilities();
					dc.setBrowserName(prop.getProperty("rBrowser"));
					dc.setPlatform(Platform.fromString(rPlatform));
					driver = new RemoteWebDriver(new URL("http://" + rHubUrl + ":" + rHubPort + "/wd/hub"), dc);
					loggerDebug("The browser : '" + rBrowser + "' has been invoked successfully in '" + rPlatform
							+ "' platform");
				} catch (Exception e) {
					loggerError("The browser : '" + rBrowser + "' cannot be invoked in '" + rPlatform + "' platform");
					reportStep("The browser : '" + rBrowser + "' cannot be invoked in '" + rPlatform + "' platform",
							"FAIL");
				}
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				driver = new FirefoxDriver();
				loggerDebug("The browser : '" + browser + "' has been launched successfully");
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver();
				loggerDebug("The browser : '" + browser + "' has been launched successfully");
			} else if (browser.equalsIgnoreCase("android")) {
				DesiredCapabilities capability = new DesiredCapabilities();
				capability.setCapability("platformName", prop.getProperty("PlatformName"));
				capability.setCapability("platformVersion", prop.getProperty("PlatformVersion"));
				;
				capability.setCapability("deviceName", prop.getProperty("DeviceName"));
				capability.setCapability("udid", prop.getProperty("udid"));
				capability.setCapability("appPackage", prop.getProperty("AppPackage"));
				// capability.setCapability("appActivity", prop.getProperty("AppActivity"));
				capability.setCapability("app", prop.getProperty("App"));
				capability.setCapability(CapabilityType.BROWSER_NAME, prop.getProperty("BrowserName"));
				capability.setCapability("deviceOrientation", prop.getProperty("DeviceOrientation"));
				capability.setCapability("appiumVersion", prop.getProperty("AppiumVersion"));
				AppiumDriverLocalService service;
				service = AppiumDriverLocalService.buildDefaultService();
				service.start();
				try {
					driver = new AndroidDriver<WebElement>(
							new URL("http://" + prop.getProperty("portNumber") + "/wd/hub"), capability);
					loggerDebug("The browser : '" + browser + "' has been launched successfully");
					// System.out.println("Andoid browser launched and app launched");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else {
				loggerDebug("The given browser : '" + browser
						+ "' cannot be launched... Please specify a proper browser...");
			}
			driver.manage().window().maximize();
			driver.get(prop.getProperty("URL"));
			loggerDebug("The URL : '" + prop.getProperty("URL") + "' has been opened");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			loggerDebug("Implicitly wait has been set to 30 Secs");
			parWindow = driver.getWindowHandle();
			reportStep("The browser : " + browser + " has been invoked and URL : " + prop.getProperty("URL")
					+ " has been opened ", "PASS");
		} catch (Exception e) {
			loggerError("The browser : '" + browser + "' cannot be opened");
			reportStep("The browser: " + browser + " cannot be invoked", "FAIL");
		}
	}

	public void implicitlyWait(int secs) {
		// TODO Auto-generated method stub
		/*
		 * try { driver.manage().timeouts().implicitlyWait(secs, TimeUnit.SECONDS);
		 * reportStep("Implicitly wait has been set to : "+secs+" Secs.", "PASS"); }
		 * catch ( Exception e ) { reportStep("The Implicitly Wait cannot be set",
		 * "FAIL"); }
		 */
	}

	public long takeSnap() {
		// TODO Auto-generated method stub
		// (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {

			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(".\\reports\\images\\"
					+ /* "SnapSotTC001" */prop.getProperty("TestCaseName") + "\\SnapShot" + snapNumb + ".jpg"));
			snapNumb++;
		} catch (Exception e) {
			loggerError("Unable to take ScreenShot");
			reportStep("Unable to take ScreenShot", "WARN");
		}

		return snapNumb;

	}

	public void enterById(String idValue, String data) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);
			loggerDebug("The data: '" + data + "' has been entered successfully");
			reportStep("The data : '" + data + "' has been entered successfully", "PASS");

		} catch (Exception e) {
			loggerError("The given data : '" + data + "' cannot be entered");
			reportStep("The given data : '" + data + "' cannot be entered", "FAIL");
		}

	}

	public void enterByName(String nameValue, String data) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.name(nameValue)).clear();
			driver.findElement(By.name(nameValue)).sendKeys(data);
			loggerDebug("The data: '" + data + "' has been entered successfully");
			reportStep("The data : '" + data + "' has been entered successfully", "PASS");
		} catch (Exception e) {
			loggerError("The given data : '" + data + "' cannot be entered");
			reportStep("The given data : '" + data + "' cannot be entered", "FAIL");
		}
	}

	public void enterByClassName(String classNameValue, String data) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.className(classNameValue)).clear();
			driver.findElement(By.className(classNameValue)).sendKeys(data);
			loggerDebug("The data: '" + data + "' has been entered successfully");
			reportStep("The data : '" + data + "' has been entered successfully", "PASS");
		} catch (Exception e) {
			loggerError("The given data : '" + data + "' cannot be entered");
			reportStep("The given data : '" + data + "' cannot be entered", "FAIL");
		}
	}

	public void enterByXpath(String xpathValue, String data) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath(xpathValue)).clear();
			driver.findElement(By.xpath(xpathValue)).sendKeys(data);
			loggerDebug("The data: '" + data + "' has been entered successfully");
			reportStep("The data : '" + data + "' has been entered successfully", "PASS");
		} catch (Exception e) {
			loggerError("The given data : '" + data + "' cannot be entered");
			reportStep("The given data : '" + data + "' cannot be entered", "FAIL");
		}
	}

	public void enterByTagName(String tagNameValue, String data) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.tagName(tagNameValue)).clear();
			driver.findElement(By.tagName(tagNameValue)).sendKeys(data);
			loggerDebug("The data: '" + data + "' has been entered successfully");
			reportStep("The data : '" + data + "' has been entered successfully", "PASS");
		} catch (Exception e) {
			loggerError("The given data : '" + data + "' cannot be entered");
			reportStep("The given data : '" + data + "' cannot be entered", "FAIL");
		}
	}

	public void clickById(String idValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.id(idValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS");
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByXpath(String xpathValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath(xpathValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS");
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}

	}

	public void clickByTagName(String tagNameValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.tagName(tagNameValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS");
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}

	}

	public void clickByName(String nameValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.name(nameValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS");
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}

	}

	public void clickByClassName(String classNameValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.className(classNameValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS");

		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");

		}

	}

	public void clickByLinkText(String linkTextValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.linkText(linkTextValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS");
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByIdNoSnap(String idValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.id(idValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByXpathNoSnap(String xpathValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.xpath(xpathValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByTagNameNoSnap(String tagNameValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.tagName(tagNameValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByNameNoSnap(String nameValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.name(nameValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByClassNameNoSnap(String classNameValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.className(classNameValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public void clickByLinkTextNoSnap(String linkTextValue) {
		// TODO Auto-generated method stub
		try {
			driver.findElement(By.linkText(linkTextValue)).click();
			loggerDebug("The element has been clicked successfully");
			reportStep("The element has been clicked successfully", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to click the element");
			reportStep("Unable to click the element", "FAIL");
		}
	}

	public boolean verifyTitle(String titleName) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.getTitle()).equals(titleName)) {
				loggerDebug("Title verified and its matching with the given text : " + titleName);
				reportStep("Title verified and its matching with the given text : " + titleName, "PASS");
				bReturn = true;
			} else {
				loggerDebug("Title verified and its not matching with the given text : " + titleName);
				reportStep("Title verified and its not matching with the given text : " + titleName, "PASS");
			}
		} catch (Exception e) {
			loggerError("Title cannot be verified with the given text : " + titleName);
			reportStep("Title cannot be verified with the given text : " + titleName, "FAIL");
		}
		return bReturn;

	}

	public boolean verifyTextById(String idValue, String text) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.findElement(By.id(idValue)).getText()).equals(text)) {
				loggerDebug("The text has been verified and its matching with the given text : " + text);
				reportStep("The text has been verified and its matching with the given text : " + text, "PASS");
				/*ATUReports.add("Verify Text", text, "Text Verified Succesfully",
						"The data : '" + text + "' verified Succesfully", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				bReturn = true;
			} else {
				ATUReports.add("Verify Text", text, "Given text doesnot match",
						"The text has been verified and its not matching with the given text : " + text + "'",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				loggerDebug("The text has been verified and its not matching with the given text : " + text);
				reportStep("The text has been verified and its not matching with the given text : " + text, "PASS");
			}
		} catch (Exception e) {
			ATUReports.add("Verify Text", text, "Text cannot be verified",
					"The text cannot be verified with the given text : " + text + "", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			loggerError("The text cannot be verified with the given text : " + text);
			reportStep("The text cannot be verified with the given text : " + text, "FAIL");
		}
		return bReturn;
	}

	public boolean verifyTextByName(String nameValue, String text) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.findElement(By.name(nameValue)).getText()).equals(text)) {
				loggerDebug("The text has been verified and its matching with the given text : " + text);
				reportStep("The text has been verified and its matching with the given text : " + text, "PASS");
				/*ATUReports.add("Verify Text", text, "Text Verified Succesfully",
						"The data : '" + text + "' verified Succesfully", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				bReturn = true;
			} else {
				loggerDebug("The text has been verified and its not matching with the given text : " + text);
				reportStep("The text has been verified and its not matching with the given text : " + text, "PASS");
				ATUReports.add("Verify Text", text, "Given text doesnot match",
						"The text has been verified and its not matching with the given text : " + text + "'",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			loggerError("The text cannot be verified with the given text : " + text);
			reportStep("The text cannot be verified with the given text : " + text, "FAIL");
			ATUReports.add("Verify Text", text, "Text cannot be verified",
					"The text cannot be verified with the given text : " + text + "", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return bReturn;
	}

	public boolean verifyTextByClassName(String classNameValue, String text) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.findElement(By.className(classNameValue)).getText()).equals(text)) {
				loggerDebug("The text has been verified and its matching with the given text : " + text);
				reportStep("The text has been verified and its matching with the given text : " + text, "PASS");
				/*ATUReports.add("Verify Text", text, "Text Verified Succesfully",
						"The data : '" + text + "' verified Succesfully", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				bReturn = true;
			} else {
				loggerDebug("The text has been verified and its not matching with the given text : " + text);
				reportStep("The text has been verified and its not matching with the given text : " + text, "PASS");
				ATUReports.add("Verify Text", text, "Given text doesnot match",
						"The text has been verified and its not matching with the given text : " + text + "'",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			loggerError("The text cannot be verified with the given text : " + text);
			reportStep("The text cannot be verified with the given text : " + text, "FAIL");
			ATUReports.add("Verify Text", text, "Text cannot be verified",
					"The text cannot be verified with the given text : " + text + "", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return bReturn;
	}

	public boolean verifyTextByTagName(String tagNameValue, String text) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.findElement(By.tagName(tagNameValue)).getText()).equals(text)) {
				loggerDebug("The text has been verified and its matching with the given text : " + text);
				reportStep("The text has been verified and its matching with the given text : " + text, "PASS");
				/*ATUReports.add("Verify Text", text, "Text Verified Succesfully",
						"The data : '" + text + "' verified Succesfully", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				bReturn = true;
			} else {
				loggerDebug("The text has been verified and its not matching with the given text : " + text);
				reportStep("The text has been verified and its not matching with the given text : " + text, "PASS");
				ATUReports.add("Verify Text", text, "Given text doesnot match",
						"The text has been verified and its not matching with the given text : " + text + "'",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			loggerError("The text cannot be verified with the given text : " + text);
			reportStep("The text cannot be verified with the given text : " + text, "FAIL");
			ATUReports.add("Verify Text", text, "Text cannot be verified",
					"The text cannot be verified with the given text : " + text + "", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return bReturn;
	}

	public boolean verifyTextByXpath(String xpathValue, String text) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.findElement(By.xpath(xpathValue)).getText()).equals(text)) {
				loggerDebug("The text has been verified and its matching with the given text : " + text);
				reportStep("The text has been verified and its matching with the given text : " + text, "PASS");
				/*ATUReports.add("Verify Text", text, "Text Verified Succesfully",
						"The data : '" + text + "' verified Succesfully", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
				bReturn = true;
			} else {
				loggerDebug("The text has been verified and its not matching with the given text : " + text);
				reportStep("The text has been verified and its not matching with the given text : " + text, "PASS");
				ATUReports.add("Verify Text", text, "Given text doesnot match",
						"The text has been verified and its not matching with the given text : " + text + "'",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			loggerError("The text cannot be verified with the given text : " + text);
			reportStep("The text cannot be verified with the given text : " + text, "FAIL");
			ATUReports.add("Verify Text", text, "Text cannot be verified",
					"The text cannot be verified with the given text : " + text + "", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return bReturn;
	}

	public boolean verifyTextByLinkText(String linkTextValue, String text) {
		// TODO Auto-generated method stub
		boolean bReturn = false;
		try {
			if ((driver.findElement(By.linkText(linkTextValue)).getText()).equals(text)) {
				loggerDebug("The text has been verified and its matching with the given text : " + text);
				reportStep("The text has been verified and its matching with the given text : " + text, "PASS");
				ATUReports.add("Verify Text", text, "Text Verified Succesfully",
						"The data : '" + text + "' verified Succesfully", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				bReturn = true;
			} else {
				loggerDebug("The text has been verified and its not matching with the given text : " + text);
				reportStep("The text has been verified and its not matching with the given text : " + text, "PASS");
				ATUReports.add("Verify Text", text, "Given text doesnot match",
						"The text has been verified and its not matching with the given text : " + text + "'",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			loggerError("The text cannot be verified with the given text : " + text);
			reportStep("The text cannot be verified with the given text : " + text, "FAIL");
			ATUReports.add("Verify Text", text, "Text cannot be verified",
					"The text cannot be verified with the given text : " + text + "", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

		return bReturn;
	}

	public String getTextById(String idValue) {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.findElement(By.id(idValue)).getText();
			loggerDebug("The retrieved text is : " + sReturn);
			reportStep("The retrieved text is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("Text cannot be retrieved from given id value");
			reportStep("Text cannot be retrieved from given id value", "FAIL");
		}
		return sReturn;
	}

	public String getTextByName(String nameValue) {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.findElement(By.name(nameValue)).getText();
			loggerDebug("The retrieved text is : " + sReturn);
			reportStep("The retrieved text is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("Text cannot be retrieved from given name value");
			reportStep("Text cannot be retrieved from given name value", "FAIL");
		}
		return sReturn;
	}

	public String getTextByClassName(String classNameValue) {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.findElement(By.className(classNameValue)).getText();
			loggerDebug("The retrieved text is : " + sReturn);
			reportStep("The retrieved text is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("Text cannot be retrieved from given class name value");
			reportStep("Text cannot be retrieved from given class name value", "FAIL");
		}
		return sReturn;
	}

	public String getTextByTagName(String tagNameValue) {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.findElement(By.tagName(tagNameValue)).getText();
			loggerDebug("The retrieved text is : " + sReturn);
			reportStep("The retrieved text is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("Text cannot be retrieved from given tag name value");
			reportStep("Text cannot be retrieved from given tag name value", "FAIL");
		}
		return sReturn;
	}

	public String getTextByXpath(String xpathValue) {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.findElement(By.xpath(xpathValue)).getText();

			loggerDebug("The retrieved text is : " + sReturn);
			reportStep("The retrieved text is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("Text cannot be retrieved from given xpath value");
			reportStep("Text cannot be retrieved from given xpath value", "FAIL");
		}
		return sReturn;
	}

	public String getTextByLinkText(String linkTextValue) {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.findElement(By.linkText(linkTextValue)).getText();
			loggerDebug("The retrieved text is : " + sReturn);
			reportStep("The retrieved text is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("Text cannot be retrieved from given id value");
			reportStep("Text cannot be retrieved from given id value", "FAIL");
		}
		return sReturn;
	}

	@SuppressWarnings("deprecation")
	public void closeBrowser() {
		// TODO Auto-generated method stub
		try {
			driver.close();
			ATUReports.add("Close current browser", "Current active browser closed succesfully",
					"Browser closed Succesfully", false);
			loggerDebug("Current browser closed");
			reportStep("Current browser closed", "PASS", false);
		} catch (Exception e) {
			ATUReports.add("Close current browser", "Unable to close current browser", e.toString(), false);
			loggerError("Unable to close the current browser");
			reportStep("Unable to close the current browser", "FAIL", false);
		}
	}

	public void closeAllBrowser() {
		// TODO Auto-generated method stub
		try {
			driver.quit();
			loggerDebug("All the active browsers has been closed");
			reportStep("All the active browsers has been closed", "PASS", false);
		} catch (Exception e) {
			loggerError("Unable to close all the browsers");
			reportStep("Unable to close all the browsers", "FAIL", false);
		}
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		sReturn = "";
		try {
			sReturn = driver.getTitle();
			loggerDebug("The title of the page is : " + sReturn);
			reportStep("The title of the page is : " + sReturn, "PASS");
		} catch (Exception e) {
			loggerError("The title cannot be retrieved");
			reportStep("The title cannot be retrieved", "FAIL");
		}
		return sReturn;
	}

	public void acceptAlert() {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().alert().accept();
			loggerDebug("Alert has been Accepted");
			reportStep("Alert has been Accepted", "PASS", false);
		} catch (Exception e) {
			loggerError("The alert cannot be accepted");
			reportStep("The alert cannot be accepted", "FAIL", false);
		}
	}

	public void dismissAlert() {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().alert().dismiss();
			loggerDebug("Alert has been Dismissed");
			reportStep("Alert has been Dismissed", "PASS", false);
		} catch (Exception e) {
			loggerError("The alert cannot be dismissed");
			reportStep("The alert cannot be dismissed", "FAIL", false);
		}
	}

	public void getAlertText() {
		// TODO Auto-generated method stub
		try {
			alertText = driver.switchTo().alert().getText();
			loggerDebug("The retrieved alert text is : " + alertText);
			reportStep("The retrieved alert text is : " + alertText, "PASS", false);
		} catch (Exception e) {
			loggerError("The alert text cannot be retrieved");
			reportStep("The alert text cannot be retrieved", "FAIL", false);
		}
	}

	public void enterTextInAlert(String data) {
		try {
			driver.switchTo().alert().sendKeys(data);
			loggerDebug("The data : " + data + " has been entered successfully into alert");
			reportStep("The data : " + data + " has been entered successfully into alert", "PASS");
		} catch (Exception e) {
			loggerError("The data : " + data + " cannot be entered into alert");
			reportStep("The data : " + data + " cannot be entered into alert", "FAIL");
		}
	}

	public void switchToParentWindow() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Parent window handle inside switch to parent window : " + parWindow);
			driver.switchTo().window(parWindow);
			ATUReports.add("Switch to parent window", "Switched to Parent Window Succesfully", "Switched Succesfully",
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			loggerDebug("Switched to parent window");
			reportStep("Switched to parent window", "PASS");
		} catch (Exception e) {
			ATUReports.add("Switch to parent window", "Unable to switch to parent window", e.toString(), LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			loggerError("Unable to switch to parent window");
			reportStep("Unable to switch to parent window", "FAIL");
		}
	}

	public void switchToLastWindow() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Parent Window Handle : " + parWindow);
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
			}
			ATUReports.add("Switch to last window", "Switched to Last Window Succesfully", "Switched Succesfully",
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			loggerDebug("Switched to last window");
			reportStep("Switched to last window", "PASS");
		} catch (Exception e) {
			ATUReports.add("Switch to last window", "Unable to switch to last window", e.toString(), LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			loggerError("Unable to switch to last window");
			reportStep("Unable to switch to last window", "FAIL");
		}
	}

	public void switchToNthWindow(int winNumb) {
		// TODO Auto-generated method stub
		try {
			int n = 1;
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
				if (winNumb == n) {
					break;
				}
				n++;
			}
			loggerDebug("Switched to : " + winNumb + "'th window");
			reportStep("Switched to : " + winNumb + "'th window", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to : " + winNumb + "'th window");
			reportStep("Unable to switch to : " + winNumb + "'th window", "FAIL");
		}
	}

	public void selectVisibleTextById(String idValue, String visibleText) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.id(idValue));
			Select dd = new Select(dropdown);
			dd.selectByVisibleText(visibleText);
			loggerDebug("The option : " + visibleText + " has been selected successfully");
			reportStep("The option : " + visibleText + " has been selected successfully", "PASS");
/*			ATUReports.add("Select VisibleText", visibleText, "Visible Text selected successfully",
					"The option : " + visibleText + " has been selected successfully", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
*/		} catch (Exception e) {

			loggerError("The option : " + visibleText + " cannot be selected");
			ATUReports.add("Select VisibleText", "Unable to select visible text",
					"The option : '" + visibleText + "' cannot be selected with the given Id value : '" + idValue + "'",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			reportStep("The option : " + visibleText + " cannot be selected", "FAIL");
		}
	}

	public void selectVisibleTextByXpath(String xpathValue, String visibleText) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.xpath(xpathValue));
			Select dd = new Select(dropdown);
			dd.selectByVisibleText(visibleText);
			loggerDebug("The option : " + visibleText + " has been selected successfully");
			reportStep("The option : " + visibleText + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + visibleText + " cannot be selected");
			reportStep("The option : " + visibleText + " cannot be selected", "FAIL");
		}
	}

	public void selectVisibleTextByName(String nameValue, String visibleText) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.name(nameValue));
			Select dd = new Select(dropdown);
			dd.selectByVisibleText(visibleText);
			loggerDebug("The option : " + visibleText + " has been selected successfully");
			reportStep("The option : " + visibleText + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + visibleText + " cannot be selected");
			reportStep("The option : " + visibleText + " cannot be selected", "FAIL");
		}
	}

	public void selectVisibleTextByClassName(String classNameValue, String visibleText) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.className(classNameValue));
			Select dd = new Select(dropdown);
			dd.selectByVisibleText(visibleText);
			loggerDebug("The option : " + visibleText + " has been selected successfully");
			reportStep("The option : " + visibleText + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + visibleText + " cannot be selected");
			reportStep("The option : " + visibleText + " cannot be selected", "FAIL");
		}
	}

	public void selectVisibleTextByTagName(String tagNameValue, String visibleText) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.tagName(tagNameValue));
			Select dd = new Select(dropdown);
			dd.selectByVisibleText(visibleText);
			loggerDebug("The option : " + visibleText + " has been selected successfully");
			reportStep("The option : " + visibleText + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + visibleText + " cannot be selected");
			reportStep("The option : " + visibleText + " cannot be selected", "FAIL");
		}
	}

	public void selectIndexById(String idValue, int indexValue) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.id(idValue));
			Select dd = new Select(dropdown);
			dd.selectByIndex(indexValue);
			loggerDebug("The option : " + indexValue + " has been selected successfully");
			reportStep("The option : " + indexValue + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + indexValue + " cannot be selected");
			reportStep("The option : " + indexValue + " cannot be selected", "FAIL");
		}
	}

	public void selectIndexByXpath(String xpathValue, int indexValue) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.xpath(xpathValue));
			Select dd = new Select(dropdown);
			dd.selectByIndex(indexValue);
			loggerDebug("The option : " + indexValue + " has been selected successfully");
			reportStep("The option : " + indexValue + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + indexValue + " cannot be selected");
			reportStep("The option : " + indexValue + " cannot be selected", "FAIL");
		}
	}

	public void selectIndexByName(String nameValue, int indexValue) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.name(nameValue));
			Select dd = new Select(dropdown);
			dd.selectByIndex(indexValue);
			loggerDebug("The option : " + indexValue + " has been selected successfully");
			reportStep("The option : " + indexValue + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + indexValue + " cannot be selected");
			reportStep("The option : " + indexValue + " cannot be selected", "FAIL");
		}
	}

	public void selectIndexByClassName(String classNameValue, int indexValue) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.className(classNameValue));
			Select dd = new Select(dropdown);
			dd.selectByIndex(indexValue);
			loggerDebug("The option : " + indexValue + " has been selected successfully");
			reportStep("The option : " + indexValue + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + indexValue + " cannot be selected");
			reportStep("The option : " + indexValue + " cannot be selected", "FAIL");
		}
	}

	public void selectIndexByTagName(String tagNameValue, int indexValue) {
		// TODO Auto-generated method stub
		try {
			WebElement dropdown = driver.findElement(By.tagName(tagNameValue));
			Select dd = new Select(dropdown);
			dd.selectByIndex(indexValue);
			loggerDebug("The option : " + indexValue + " has been selected successfully");
			reportStep("The option : " + indexValue + " has been selected successfully", "PASS");
		} catch (Exception e) {
			loggerError("The option : " + indexValue + " cannot be selected");
			reportStep("The option : " + indexValue + " cannot be selected", "FAIL");
		}
	}

	public void switchToParentFrame() {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().parentFrame();
			loggerDebug("Switched to parent frame");
			reportStep("Switched to parent frame", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to parent frame");
			reportStep("Unable to switch to parent frame", "FAIL");
		}
	}

	public void switchToDefaultFrame() {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().defaultContent();
			loggerDebug("Switched to default frame");
			reportStep("Switched to default frame", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to default frame");
			reportStep("Unable to switch to default frame", "FAIL");
		}
	}

	public void switchFrameByIndex(int frameIndex) {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().frame(frameIndex);
			loggerDebug("Switched to : " + frameIndex + "'th frame");
			reportStep("Switched to : " + frameIndex + "'th frame", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to : " + frameIndex + "'th frame");
			reportStep("Unable to switch to : " + frameIndex + "'th frame", "FAIL");
		}
	}

	public void switchFrameById(String idValue) {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().frame(idValue);
			loggerDebug("Successfully Switched frame by id");
			reportStep("Successfully Switched frame by id", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to frame by id");
			reportStep("Unable to switch to frame by id", "FAIL");
		}
	}

	public void switchFrameByName(String nameValue) {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().frame(nameValue);
			loggerDebug("Successfully Switched frame by name");
			reportStep("Successfully Switched frame by name", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to frame by name");
			reportStep("Unable to switch to frame by name", "FAIL");
		}
	}

	public void switchFrameByWebElement(WebElement webElement) {
		// TODO Auto-generated method stub
		try {
			driver.switchTo().frame(webElement);
			loggerDebug("Successfully Switched frame by web element");
			reportStep("Successfully Switched frame by web element", "PASS");
		} catch (Exception e) {
			loggerError("Unable to switch to frame by web element");
			reportStep("Unable to switch to frame by web element", "FAIL");
		}
	}

	public void click(String locator, String value) {
		// TODO Auto-generated method stub
		try {
			if (locator.equalsIgnoreCase("id")) {
				driver.findElement(By.id(value)).click();
			} else if (locator.equalsIgnoreCase("xpath")) {
				driver.findElement(By.xpath(value)).click();
			} else if (locator.equalsIgnoreCase("name")) {
				driver.findElement(By.name(value)).click();
			} else if (locator.equalsIgnoreCase("classname")) {
				driver.findElement(By.className(value)).click();
			} else if (locator.equalsIgnoreCase("linktext")) {
				driver.findElement(By.linkText(value)).click();
			}
			loggerInfo("The element has been clicked by locator: '" + locator + "' successfully");
			reportStep("The element has been clicked by locator: '" + locator + "' successfully", "PASS");
/*			ATUReports.add("Click Element", "Clicked Succesfully",
					"Element with locator : '" + locator + "' and Value : '" + value + "' clicked succesfully",
					LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
*/
		} catch (Exception e) {
			loggerError("Unable to click the element by locator: '" + locator + "', value :'" + value + "'");
			ATUReports.add("Click Element", "Unable to click",
					"Element with locator : '" + locator + "' and Value : '" + value + "' cannot be clicked",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			reportStep("Unable to click the element by locator: '" + locator + "', value : '" + value + "'", "FAIL");

		}

	}

	public void enter(String locator, String value, String data) {
		// TODO Auto-generated method stub
		try {
			if (locator.equalsIgnoreCase("id")) {
				driver.findElement(By.id(value)).clear();
				driver.findElement(By.id(value)).sendKeys(data);
			} else if (locator.equalsIgnoreCase("xpath")) {
				driver.findElement(By.xpath(value)).clear();
				driver.findElement(By.xpath(value)).sendKeys(data);
			} else if (locator.equalsIgnoreCase("name")) {
				driver.findElement(By.name(value)).clear();
				driver.findElement(By.name(value)).sendKeys(data);
			} else if (locator.equalsIgnoreCase("classname")) {
				driver.findElement(By.className(value)).clear();
				driver.findElement(By.className(value)).sendKeys(data);
			} else if (locator.equalsIgnoreCase("linktext")) {
				driver.findElement(By.linkText(value)).click();
				driver.findElement(By.linkText(value)).sendKeys(data);
			}
			loggerInfo("The data : '" + data + "' has been entered by locator: '" + locator + "' successfully");
			reportStep("The data : '" + data + "' has been entered by locator: '" + locator + "' successfully", "PASS");
			/*ATUReports.add("Enter Text", data, "Text Entered Succesfully",
					"The data : '" + data + "' entered Succesfully", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));*/
		} catch (Exception e) {
			ATUReports.add(
					"Enter Text", data, e.toString(), "Unable to Enter data : '" + data + "' in the locator: '"
							+ locator + "' with value: '" + value + "' ",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			loggerError("Unable to enter data : '" + data + "' in the field by locator: '" + locator + "', value :'"
					+ value + "'");
			reportStep("Unable to enter data : '" + data + "' the element by locator: '" + locator + "', value : '"
					+ value + "'", "FAIL");
		}

	}

	public void moveToElement(String locator, String value) {
		action = new Actions(driver);
		try {
			if (locator.equalsIgnoreCase("xpath")) {

				action.moveToElement(driver.findElement(By.xpath(value))).build().perform();

			}

		} catch (Exception e) {

		}
	}

	public int countItems(String locator , String value ) throws InterruptedException {

		action = new Actions(driver);
				
		if(locator.equalsIgnoreCase("xpath")) {
		do {
			initCount = finalCount;
			action.sendKeys(Keys.END).sendKeys(Keys.PAGE_UP).build().perform();
			Thread.sleep(5000);
			finalCount = driver.findElements(By.xpath(value)).size();
		} while (finalCount > initCount);
		}

		return finalCount;
	}

}