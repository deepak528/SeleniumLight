package api;


import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class SeleniumHelper {
	private WebDriver driver;
	
	public SeleniumHelper (WebDriver driver) {
		this.driver = driver;
	}
	
	public void navigate (String URL) {
		navigate (URL, 3);
	}
	
	public void navigate (String URL, int wait_in_seconds) {
		// navigate to URL
		driver.get(URL);
		
		// wait for x seconds
		sleep(wait_in_seconds);
	}

	public WebElement findElement(String Element_Identifier) {
		List<WebElement> elements;
		
		// Find by ID
		elements = driver.findElements(By.id(Element_Identifier));
		
		if (elements.size() == 1) {
			Log ("Found element: id=" + Element_Identifier);
			return elements.get(0);
		} else if (elements.size() > 1) {
			throw new NoSuchFieldError();
		}

		// Find by ID
		elements = driver.findElements(By.name(Element_Identifier));
		
		if (elements.size() == 1) {
			Log ("Found element: name=" + Element_Identifier);
			
			return elements.get(0);
		}
		
		// Find by Class - if no space in name
		if (Element_Identifier.indexOf(" ") == -1) {
			elements = driver.findElements(By.className(Element_Identifier));
			
			if (elements.size() == 1) {
				Log ("Found element: class=" + Element_Identifier);
				
				return elements.get(0);
			}	
		}
		
		// Find by Link Text
		elements = driver.findElements(By.partialLinkText(Element_Identifier));
		
		if (elements.size() == 1) {
			Log ("Found element: partial link text=" + Element_Identifier);
			return elements.get(0);
		}
	
		// Find by Span-Inner Text
		String xpath = "//span[text()='" + Element_Identifier + "']";
		elements = driver.findElements(By.xpath(xpath));
		
		if (elements.size() == 1) {
			Log ("Found element: span/innerText=" + Element_Identifier);
			return elements.get(0);
		}		
		
		// Find by Div-Inner Text
		xpath = "//div[text()='" + Element_Identifier + "']";
		elements = driver.findElements(By.xpath(xpath));
		
		if (elements.size() == 1) {
			Log ("Found element: div/innerText=" + Element_Identifier);
			return elements.get(0);
		}
		
		
		// no match found
		return null;
	}	
	
	/**
	Find a textbox/drop down on a page using case-insensitive search.
	**/
	public WebElement findInputElement(String Element_Identifier) {
		return findElementByTag("input", Element_Identifier);
	}
	
	/**
	Find a button on a page using case-insensitive search.
	**/
	public WebElement findButton(String Element_Identifier) {
		return findElementByTag("input,button", Element_Identifier);
	}
	
	
	public WebElement findElementByTag(String tagName, String Element_Identifier) {
		String[] tagNames = tagName.split(",");
		
		// loop through all tags
		for (int i=0; i<tagNames.length; i++) {
			
			// find elements using first tag
			List<WebElement> inputs = driver.findElements(By.tagName(tagNames[i]));
			
			// normalize search string
			Element_Identifier = Element_Identifier.toLowerCase().trim();
			
			Log ("Finding element: '" + Element_Identifier + "'");
			
			Log ("Elements found: " + inputs.size());
			
			// loop through all elements
			for (WebElement element : inputs) {
				
				if (element.isDisplayed()) {
				
					// get attributes
					String pName = (element.getAttribute("name") + "").toLowerCase().trim();
					String pID = (element.getAttribute("id") + "").toLowerCase().trim();
					String pClass = (element.getAttribute("class") + "").toLowerCase().trim();
					String pAriaLabel = (element.getAttribute("aria-label") + "").toLowerCase().trim();
					
					Log("Checking element: '" + pName + "'");
					
					// examine all elements
					if (Element_Identifier.equals(pID) ||
						Element_Identifier.equals(pName) ||
						Element_Identifier.equals(pClass) ||
						Element_Identifier.equals(pAriaLabel)) {
						
						Log ("It's a match");
						
						return element;
					}
				}
			}
			
		}
		
		return null;
	}
	
	public void setText(String Element_Identifier, String text ) {
		//WebElement element = findElement(Element_Identifier);
		
		WebElement element = findInputElement (Element_Identifier);
		
		element.sendKeys(text);
		
		Log ("Setting text on input box: " + text);
	}
	
	public String getText(String Element_Identifier) {
		WebElement element = findElement(Element_Identifier);
		
		Log ("Returning text for '" + Element_Identifier + "': " + element.getText());
		
		return element.getText();		
	}
	
	public void clickButton(String Element_Identifier) {
		WebElement element = findButton(Element_Identifier);
		
		element.click();
		
		Log ("Clicking button");
		
	}
	
	public void VerifyText(String Element_Identifier, String ExpectedText) {
		List<WebElement> elements;
		WebElement element;
		
		String xpath = "//*[text()='" + Element_Identifier + "']";
		elements = driver.findElements(By.xpath(xpath));
		
		//elements.get(0).
		
		//WebElement element = findElement(Element_Identifier);
		
		element = elements.get(0).findElement(By.xpath("./following-sibling::span"));
		
		//log (element.getText());
		
		Assert.assertEquals(element.getText(), ExpectedText);
	}
	
	public void sleep(int seconds) {
		
	   try {
				Thread.sleep(seconds * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
	}
	
	public void Log(String message) {
		System.out.println (message);
	}
	
	public boolean IsElementPresent(String Element_Identifier) {
		WebElement element = this.findElement(Element_Identifier);
		
		return (element != null);
	}
	
	public void FindAllElements() {
		List<WebElement> inputs = driver.findElements(By.xpath("//input"));	
	
		for (WebElement element : inputs) {
			if (element.getAttribute("id").toLowerCase() == "test") {
				
				// do nothing
			}
		}
	}
	
	/*
	public void OptionalBlock (String Element_Identifier, Method myFunction) {
		WebElement element = this.findElement(Element_Identifier);
		
		if (element != null) {

	        try {
	        	myFunction.invoke(new Object[1], "");
	        } catch(Exception e) {
	        	// do nothing
	        }
		}
	}
	*/
}
