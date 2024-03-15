package com.yk.baseclass;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.yk.utilities.WebUtilities;

public class BaseClass {

	protected WebUtilities util = WebUtilities.getObject();

	public String takeScreenShot(String testCaseName) {
		TakesScreenshot ts = (TakesScreenshot) util.getDriver();
		File file = ts.getScreenshotAs(OutputType.FILE);
		String screenShotsFolder = System.getProperty("user.dir" + "/screenshots/" + testCaseName + ".png");

		try {
			FileUtils.copyFile(file, new File(screenShotsFolder));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenShotsFolder;
	}

}
