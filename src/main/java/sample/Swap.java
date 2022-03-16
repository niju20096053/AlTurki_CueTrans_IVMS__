package sample;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;

public class Swap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		int a = 5;
		int b = 10;
		
		a = a + b ; 
		b = a - b ;
		a = a - b ;
		
		System.out.println("A : " + a  +"    ...   B = "  + b);
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		ChromeOptions options = new ChromeOptions();
		
		System.out.println((options.getBrowserName()));
		System.out.println(options.getVersion().toString());
		options.addArguments("disable-infobars");
		driver.quit();
		
		
	}

}
