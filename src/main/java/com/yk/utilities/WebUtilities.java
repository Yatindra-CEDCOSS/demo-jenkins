package com.yk.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebUtilities {

	private WebDriver driver;
	private static WebUtilities util;

	/**
	 * default constructor
	 */
	private WebUtilities() {

	}

	/**
	 * returns the object of WebUtilities
	 * 
	 * @return
	 */
	public static WebUtilities getObject() {
		if (util == null) {
			util = new WebUtilities();
		}

		return util;
	}

	/**
	 * returns driver
	 * 
	 * @return
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * opens browser
	 * 
	 * @param browserName
	 * @param url
	 */
	public void openBrowser(String browserName, String url) {
		if (browserName == null || browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}

		getDriver().get(url);
	}

	/**
	 * clicks on element with exception handling
	 * 
	 * @param element
	 */
	public void clickOnElement(WebElement element) {
		try {
			element.click();
		} catch (ElementNotInteractableException | NoSuchElementException | StaleElementReferenceException e) {
			clickOnElementUsingJavaScript(element);
		}
	}

	/**
	 * click on element using JavaScript code.
	 * 
	 * @param element
	 */
	public void clickOnElementUsingJavaScript(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		moveToElementUsingJavaScript(element);
		jsExecutor.executeScript("argument[0].click();", element);
	}

	/**
	 * move to element using JavaScript code.
	 * 
	 * @param element
	 */
	public void moveToElementUsingJavaScript(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * enter value in text field.
	 * 
	 * @param element
	 * @param value
	 */
	public void enterValue(WebElement element, String value) {
		try {
			element.sendKeys(value);
		} catch (ElementNotInteractableException | NoSuchElementException | StaleElementReferenceException e) {
			enterValueUsingJavaScript(element, value);
		}
	}

	/**
	 * enter value in text field using JavaScript code.
	 * 
	 * @param element
	 * @param value
	 */
	public void enterValueUsingJavaScript(WebElement element, String value) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
		moveToElementUsingJavaScript(element);
		jsExecutor.executeScript("arguments[0].value = arguments[1];", element, value);
	}

	/**
	 * switch to window on the basis of the url.
	 * 
	 * @param url
	 */
	public void switchToWindow(String url) {
		Set<String> windows = getDriver().getWindowHandles();

		for (String wind : windows) {
//			switch to each window.
			getDriver().switchTo().window(wind);

			if (wind.contains(url)) {
				break;
			}
		}
	}

	/**
	 * switch to window on the basis of the title.
	 * 
	 * @param title
	 */
	public void switchToWindowUsingTitle(String title) {
		Set<String> windows = getDriver().getWindowHandles();

		for (String win : windows) {
//			switch to each window.
			getDriver().switchTo().window(win);
			String getTitleValue = getDriver().getTitle();

			if (getTitleValue.equalsIgnoreCase(title)) {
				break;
			}
		}
	}

	/**
	 * read properties file.
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 */
	public String readProperties(String fileName, String key) {
		Properties prop = new Properties();
		String path = System.getProperty("user.dir/proptiesFiles/") + fileName + "properties";

		try (FileInputStream fis = new FileInputStream(path)) {
			prop.load(fis);
		} catch (IOException e) {
			e.getMessage();
		}

		String value = prop.getProperty(key.trim());

		return value.trim();
	}

	/**
	 * update values of property file.
	 * 
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public void updatePropertiesFile(String fileName, String key, String value) {
		Properties prop = new Properties();
		String path = System.getProperty("user.dir/proptiesFiles/") + fileName + "properties";

		try (FileInputStream fis = new FileInputStream(path); FileOutputStream fos = new FileOutputStream(path)) {
			prop.load(fis);
			prop.setProperty(key.trim(), value);
			prop.store(fos, "updated properties");

		} catch (IOException e) {
			e.getMessage();
		}
	}

}
