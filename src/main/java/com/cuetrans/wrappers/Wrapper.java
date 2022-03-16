package com.cuetrans.wrappers;

import org.openqa.selenium.WebElement;

public interface Wrapper {

	/**
	 * 
	 * method to launch the required browser and url will get browser name and
	 * invoke that particular browser will maximize the browser and set the wait as
	 * 30 secs
	 * 
	 * @param browser
	 * @param url
	 * @author nj112237
	 * @throws Exception
	 */
	public void invokeApp(String browser);

	/**
	 * this method will set the implicitly wait as given secs
	 * 
	 * @param secs
	 * @author nj112237
	 */
	public void implicitlyWait(int secs);

	/**
	 * this method will take screenshot
	 * 
	 * @author nj112237
	 * @return
	 */
	public long takeSnap();

	/**
	 * method will enter given text by finding the element by id value
	 * 
	 * @param idValue
	 * @param data
	 * @author nj112237
	 */
	public void enterById(String idValue, String data);

	/**
	 * method will enter given text by finding the element by name value
	 * 
	 * @param nameValue
	 * @param data
	 * @author nj112237
	 */
	public void enterByName(String nameValue, String data);

	/**
	 * method will enter given text by finding the element by class name value
	 * 
	 * @param classNameValue
	 * @param data
	 * @author nj112237
	 */
	public void enterByClassName(String classNameValue, String data);

	/**
	 * method will enter given text by finding the element by xpath value
	 * 
	 * @param xpathValue
	 * @param data
	 * @author nj112237
	 */
	public void enterByXpath(String xpathValue, String data);

	/**
	 * method will enter given text by finding the element by tag name value
	 * 
	 * @param tagNamevalue
	 * @param data
	 * @author nj112237
	 */
	public void enterByTagName(String tagNamevalue, String data);

	/**
	 * method will click the element by id value
	 * 
	 * @param idValue
	 * @author nj112237
	 */
	public void clickById(String idValue);

	/**
	 * method will click the element by xpath value
	 * 
	 * @param xpathValue
	 * @author nj112237
	 */
	public void clickByXpath(String xpathValue);

	/**
	 * method will click the element by tag name value
	 * 
	 * @param tagNameValue
	 * @author nj112237
	 */
	public void clickByTagName(String tagNameValue);

	/**
	 * method will click the element by name value
	 * 
	 * @param nameValue
	 * @author nj112237
	 */
	public void clickByName(String nameValue);

	/**
	 * method will click the element by class name value
	 * 
	 * @param classNameValue
	 * @author nj112237
	 */
	public void clickByClassName(String classNameValue);

	/**
	 * method will click the element by linktext value
	 * 
	 * @param linkTextValue
	 * @author nj112237
	 */
	public void clickByLinkText(String linkTextValue);

	/**
	 * method will click the element by id value
	 * 
	 * @param idValue
	 * @author nj112237
	 */
	public void clickByIdNoSnap(String idValue);

	/**
	 * method will click the element by xpath value without taking screen shot
	 * 
	 * @param xpathValue
	 * @author nj112237
	 */
	public void clickByXpathNoSnap(String xpathValue);

	/**
	 * method will click the element by tag name value without taking screen shot
	 * 
	 * @param tagNameValue
	 * @author nj112237
	 */
	public void clickByTagNameNoSnap(String tagNameValue);

	/**
	 * method will click the element by name value without taking screen shot
	 * 
	 * @param nameValue
	 * @author nj112237
	 */
	public void clickByNameNoSnap(String nameValue);

	/**
	 * method will click the element by class name value without taking screen shot
	 * 
	 * @param classNameValue
	 * @author nj112237
	 */
	public void clickByClassNameNoSnap(String classNameValue);

	/**
	 * method will click the element by linktext value without taking screen shot
	 * 
	 * @param linkTextValue
	 * @author nj112237
	 */
	public void clickByLinkTextNoSnap(String linkTextValue);

	/**
	 * this method will get the title and verify it with given title
	 * 
	 * @param titleName
	 * @author nj112237
	 * @return
	 */
	public boolean verifyTitle(String titleName);

	/**
	 * this method will verify text with given text by id
	 * 
	 * @param idValue
	 * @author nj112237
	 * 
	 */
	public boolean verifyTextById(String idValue, String text);

	/**
	 * this method will verify text with given text by name
	 * 
	 * @param nameValue
	 * @author nj112237
	 * 
	 */
	public boolean verifyTextByName(String nameValue, String text);

	/**
	 * this method will verify text with given text by className
	 * 
	 * @param classNameValue
	 * @author nj112237
	 * 
	 */
	public boolean verifyTextByClassName(String classNameValue, String text);

	/**
	 * this method will verify text with given text by tag name
	 * 
	 * @param tagNameValue
	 * @author nj112237
	 * 
	 */
	public boolean verifyTextByTagName(String tagNameValue, String text);

	/**
	 * this method will verify text with given text by xpath
	 * 
	 * @param xpathValue
	 * @author nj112237
	 * 
	 */
	public boolean verifyTextByXpath(String xpathValue, String text);

	/**
	 * this method will verify text with given text by link text
	 * 
	 * @param linkTextValue
	 * @author nj112237
	 * 
	 */
	public boolean verifyTextByLinkText(String linkTextValue, String text);

	/**
	 * this method will get text from web element by id
	 * 
	 * @param idValue
	 * @author nj112237
	 * @return
	 */
	public String getTextById(String idValue);

	/**
	 * this method will get text from web element by name
	 * 
	 * @param nameValue
	 * @author nj112237
	 */
	public String getTextByName(String nameValue);

	/**
	 * this method will get text from web element by class name
	 * 
	 * @param classNameValue
	 * @author nj112237
	 */
	public String getTextByClassName(String classNameValue);

	/**
	 * this method will get text from web element by tag name
	 * 
	 * @param tagNameValue
	 * @author nj112237
	 */
	public String getTextByTagName(String tagNameValue);

	/**
	 * this method will get text from web element by xpath
	 * 
	 * @param xpathValue
	 * @author nj112237
	 * @return
	 */
	public String getTextByXpath(String xpathValue);

	/**
	 * this method will get text from web element by linkText
	 * 
	 * @param linkTextValue
	 * @author nj112237
	 */
	public String getTextByLinkText(String linkTextValue);

	/**
	 * this method will close the current browser
	 * 
	 * @author nj112237
	 */
	public void closeBrowser();

	/**
	 * this method will close all the browsers
	 * 
	 * @author nj112237
	 */
	public void closeAllBrowser();

	/**
	 * this method will get title of the current window
	 * 
	 * @author nj112237
	 */
	public String getTitle();

	/**
	 * this method will accept the alert
	 * 
	 * @author nj112237
	 */
	public void acceptAlert();

	/**
	 * this method will dismiss the alert
	 * 
	 * @author nj112237
	 */
	public void dismissAlert();

	/**
	 * this method will get the alert text
	 * 
	 * @author nj112237
	 * @return
	 */
	public void getAlertText();

	/**
	 * this method will send data to text field in alert
	 * 
	 * @param data
	 * @author nj112237
	 */
	public void enterTextInAlert(String data);

	/**
	 * this method will switch to parent window
	 * 
	 * @param parWindow
	 * @author nj112237
	 */
	public void switchToParentWindow();

	/**
	 * this method will switch to last window
	 * 
	 * @author nj112237
	 */
	public void switchToLastWindow();

	/**
	 * this method will switch to desired(n'th) window
	 * 
	 * @param winNumber
	 * @author nj112237
	 */
	public void switchToNthWindow(int winNumb);

	/**
	 * this method will find element by id and select by visible text
	 * 
	 * @param idValue
	 * @param visibleText
	 * @author nj112237
	 */
	public void selectVisibleTextById(String idValue, String visibleText);

	/**
	 * this method will find the element by xpath and select by visible text
	 * 
	 * @param xpathValue
	 * @param visibleText
	 * @author nj112237
	 */
	public void selectVisibleTextByXpath(String xpathValue, String visibleText);

	/**
	 * this method will find the element by name and select by visible text
	 * 
	 * @param nameValue
	 * @param visibleText
	 * @author nj112237
	 */
	public void selectVisibleTextByName(String nameValue, String visibleText);

	/**
	 * this method will find the element by class name and select by visible text
	 * 
	 * @param classNameValue
	 * @param visibleText
	 * @author nj112237
	 */
	public void selectVisibleTextByClassName(String classNameValue, String visibleText);

	/**
	 * this method will find the element by tag name and select by visible text
	 * 
	 * @param tagNameValue
	 * @param visibleText
	 * @author nj112237
	 */
	public void selectVisibleTextByTagName(String tagNameValue, String visibleText);

	/**
	 * this method will find element by id and select by index value
	 * 
	 * @param idValue
	 * @param indexValue
	 * @author nj112237
	 */
	public void selectIndexById(String idValue, int indexValue);

	/**
	 * this method will find the element by xpath and select by index value
	 * 
	 * @param xpathValue
	 * @param indexValue
	 * @author nj112237
	 */
	public void selectIndexByXpath(String xpathValue, int indexValue);

	/**
	 * this method will find the element by name and select by index value
	 * 
	 * @param nameValue
	 * @param indexValue
	 * @author nj112237
	 */
	public void selectIndexByName(String nameValue, int indexValue);

	/**
	 * this method will find the element by class name and select by index value
	 * 
	 * @param classNameValue
	 * @param indexValue
	 * @author nj112237
	 */
	public void selectIndexByClassName(String classNameValue, int indexValue);

	/**
	 * this method will find the element by tag name and select by index value
	 * 
	 * @param tagNameValue
	 * @param indexValue
	 * @author nj112237
	 */
	public void selectIndexByTagName(String tagNameValue, int indexValue);

	/**
	 * this method will switch to parent frame
	 * 
	 * @author nj112237
	 */
	public void switchToParentFrame();

	/**
	 * this method will switch to the default frame in he web page
	 * 
	 * @author nj112237
	 */
	public void switchToDefaultFrame();

	/**
	 * this method will switch to a particular frame by index
	 * 
	 * @param frameIndex
	 * @author nj112237
	 */
	public void switchFrameByIndex(int frameIndex);

	/**
	 * this method will switch to particular frame by id value
	 * 
	 * @param idValue
	 * @author nj112237
	 */
	public void switchFrameById(String idValue);

	/**
	 * this method will switch to particular frame by name value
	 * 
	 * @param nameValue
	 * @author nj112237
	 */
	public void switchFrameByName(String nameValue);

	/**
	 * this method will switch to particular frame by web element
	 */
	public void switchFrameByWebElement(WebElement webElement);

	/**
	 * this method will get the locator type and click the element by getting the
	 * locatoor value from property file
	 * 
	 * @param
	 * @author nj112237
	 */
	public void click(String locator, String value);

	/**
	 * this method will get the locator type and enter text in the element by
	 * getting the locatoor value from property file
	 * 
	 * @param locator
	 * @param value
	 * @param data
	 * @author nj112237
	 */
	public void enter(String locator, String value, String data);

}
