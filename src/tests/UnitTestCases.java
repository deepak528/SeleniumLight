package tests;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;

import api.SeleniumHelper;


public class UnitTestCases  {
	static WebDriver driver;
	static SeleniumHelper selenium;
	

    @BeforeClass
    public void setUp() {
    	String version = "10";
    	String browser = "chrome";
    	
		// set path to Chrome
		System.setProperty("webdriver.chrome.driver", "/users/smritigupta/Documents/selenium/webdrivers/chromedriver");
  	  
    	// code that will be invoked when this test is instantiated
        driver = new ChromeDriver();
        selenium = new SeleniumHelper(driver);

        // wait to find elements
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
        
        /*
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) {
            capabilities.setCapability(CapabilityType.VERSION, version);
        }
        capabilities.setCapability(CapabilityType.PLATFORM, "mac");
        //new Remotewe
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
                (Capabilities) capabilities);
                
            */
    }
    
    @AfterClass
    public void cleanUp() {
        //Close the browser
        driver.quit();
    }
    
    @Test
    public static void testMemberPortal() {
    	// Go to homepage
        selenium.navigate("http://google.com/");	
                
     // enter credentials
        selenium.setText("q", "selenium webdriver");
        //selenium.setText("password", "password");
        selenium.clickButton("Google Search");
        
        int results = driver.findElement(By.id("rso")).findElements(By.tagName("li")).size();
        
        System.out.println("Results on page: " + results);
        
        //selenium.VerifyText ("Covered Members", "3");
    }

  
}