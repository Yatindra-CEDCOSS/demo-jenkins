package demo;

import org.testng.annotations.Test;

import com.yk.baseclass.BaseClass;

public class DemoTest extends BaseClass{

	@Test
	public void openGoogle() {
		util.openBrowser("chrome", "https://www.google.com");
	}
}
