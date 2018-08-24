package pages.base;

import java.io.File;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.ITestResult;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.assertthat.selenium_shutterbug.core.ElementSnapshot;
import com.assertthat.selenium_shutterbug.core.PageSnapshot;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.remote.MobileCapabilityType;
import utilities.CommandLine;
import utilities.DateTime;
import utilities.FolderFile;


@Listeners(CustomTestListener.class)
public class PageTest {
	public static WebDriver driver;
	private static ExtentReports extent;
	public static ExtentTest test;

	// private static String reportFolder="";
	final String reportPath = "./Extent.html";

	public WebDriver getDriver() {
		return driver;
	}

	private void setDriver(String server) throws MalformedURLException {
		DesiredCapabilities capabilities = DesiredCapabilities.android();

		String url = "";
		System.out.println("*************************** Start test for server: " + server + "************");
		switch (server) {
		case "local":
			url = "http://127.0.0.1:4723/wd/hub";
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "my phone");
			capabilities.setCapability(MobileCapabilityType.VERSION, "7.1");
			capabilities.setCapability("appPackage", "com.android.chrome");
			capabilities.setCapability("appActivity", "com.google.android.apps.chrome.ChromeTabbedActivity");
//			capabilities.setCapability("chromedriverExecutable", "C:\\selenium_drivers\\chromedriver.exe");
			capabilities.setCapability("unicodeKeyboard", true);

			break;

		case "sauceLaps":
			String USERNAME = "huyphan13189";
			String ACCESS_KEY = "d74e7457-e217-49cc-a1bd-1edf53426147";
			url = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";

			capabilities.setCapability("appiumVersion", "1.8.1");
			capabilities.setCapability("deviceName", "Samsung Galaxy Tab A 10 GoogleAPI Emulator");
			capabilities.setCapability("deviceOrientation", "landscape");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("platformVersion", "7.1");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("unicodeKeyboard", true);
			break;

		default:
			break;
		}
		
		driver = new RemoteWebDriver(new URL(url), capabilities);

		// driver = new AndroidDriver<>(new URL(URL), capabilities);

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@BeforeSuite(alwaysRun = true)
	protected void beforeSuite() {
		String reportPath = "test-reports" + "/images";
		
		extent = ExtentManager.getReporter("test-reports" + "/ExtentReport.html");
	}
	
	public void createReportFolder(String reportPath ) {
		FolderFile.deleteAFolder(reportPath);
		FolderFile.createMutilFolder(reportPath);
		
	}

	@BeforeClass(alwaysRun = true)
	protected synchronized void beforeClass() {
	}

	@Parameters({ "server" })
	@BeforeMethod(alwaysRun = true)
	protected synchronized void beforeMethod(String server, Method method) {
		Test testClass = method.getAnnotation(Test.class);
		String testGroups = "";
		for (int i = 0; i < testClass.groups().length; i++) {
			if (i == 0)
				testGroups = testGroups + "" + testClass.groups()[i];
			else
				testGroups = testGroups + ", " + testClass.groups()[i];
		}

		System.out.println("*********************** Start :" + getClass().getSimpleName() + "/" + method.getName()
				+ " ***********************");
		System.out.println("Test Group: " + testGroups);
		try {
			setDriver(server);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// driver.navigate().to(appURL);
		test = extent.startTest(getClass().getSimpleName() + "/" + method.getName())
				.assignCategory(getClass().getSimpleName());
		test.log(LogStatus.INFO, "Test Group: " + testGroups);
	}

	@AfterMethod(alwaysRun = true)
	protected synchronized void afterMethodd(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				if (result.getThrowable().getStackTrace() != null) {
					test.log(LogStatus.FAIL, result.getThrowable() + screenShoot());
				}
			} catch (Exception e) {
			}
		}

		extent.endTest(test);
		extent.flush();
		driver.quit();
	}

	@AfterClass(alwaysRun = true)
	protected void afterClass() {
		// driver.quit();
	}

	@AfterSuite(alwaysRun = true)
	protected void afterSuite() {
		extent.close();
		// zipReportFolder();
	}

	public synchronized void logError(ITestResult result, String details) {
		result.setStatus(ITestResult.FAILURE);
		test.log(LogStatus.FAIL, details);
	}

	public void finishTest() {
	}
	// Methods for test report ***********************************************

	public void zipReportFolder() {
		String workingDir = System.getProperty("user.dir");
		String zipFilePath = workingDir + "/test-reports.zip";
		String zipFolderPath = workingDir + "/test-reports/*";
		String cmdStr = "cd 7z1604-extra && 7za a -r " + zipFilePath + " " + zipFolderPath;
		CommandLine.runACommandLine(cmdStr);
	}

	private String imageSavePath() {
		String imgSP = "";
		File path = new File("").getAbsoluteFile();
		imgSP = path.toString() + "/test-reports" + "/images/";
		return imgSP;
	}

	private String imageName() {
		String imgName = DateTime.getCurrentTime("MM-dd-yyyy_HHmmssSSS");

		return imgName;
	}

	public String screenShoot() {
		String imgPath = "";
		String imgName = imageName() + ".png";
		try {
			File scrnsht = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// File path = new File("").getAbsoluteFile();
			// String pathfile = path.toString() + "\\test-reports" + "\\images\\" +
			// imgName;
			String pathfile = imageSavePath() + imgName;
			FileUtils.copyFile(scrnsht, new File(pathfile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgPath = test.addScreenCapture("./images/" + imgName);
		return imgPath;
	}

	public String shuttlePage() {
		String imgPath = "";
		String imgName = imageName();
		try {
			// File path = new File("").getAbsoluteFile();
			String pathfile = imageSavePath();
			PageSnapshot pss = Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS);
			pss.withName(imgName);
			pss.save(pathfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgPath = test.addScreenCapture("./images/" + imgName + ".png");
		return imgPath;
	}

	public String shuttlePageElement(WebElement wel) {
		String imgPath = "";
		String imgName = imageName();
		try {
			String pathfile = imageSavePath();
			PageSnapshot pss = Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS);
			pss.withName(imgName);
			pss.highlight(wel);
			pss.save(pathfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgPath = test.addScreenCapture("./images/" + imgName + ".png");
		return imgPath;
	}

	public String shuttleElement(WebElement wel) {
		String imgPath = "";
		String imgName = imageName();
		try {
			String pathfile = imageSavePath();
			ElementSnapshot pss = Shutterbug.shootElement(driver, wel);
			pss.withName(imgName);
			pss.save(pathfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgPath = test.addScreenCapture("./images/" + imgName + ".png");
		return imgPath;
	}

	// Verify text display
	public void elementTextEqual(String dec, WebElement wel, String text) {
		if (wel.getText().trim().toUpperCase().equals(text.trim().toUpperCase())) {
			test.log(LogStatus.PASS, dec + " was verify passed. Expected: " + text + " - Actual: " + wel.getText());
		} else {
			test.log(LogStatus.FAIL, dec + " was verify failed. Expected: " + text + " - Actual: " + wel.getText());
		}
	}

	public void verifyTextEqual(String dec, String expected, String actual) {
		if (expected.trim().toUpperCase().equals(actual.trim().toUpperCase()))
			test.log(LogStatus.PASS, dec + " was verify passed. Expected: " + expected + " - Actual: " + actual);
		else
			test.log(LogStatus.FAIL, dec + " was verify failed. Expected: " + expected + " - Actual: " + actual);
	}

	public void elementTextContain(String dec, WebElement wel, String text) {
		if (wel.getText().contains(text)) {
			test.log(LogStatus.PASS, dec + " was verify passed. Expected: " + text + " - Actual: " + wel.getText());
		} else {
			test.log(LogStatus.FAIL, dec + " was verify failed. Expected: " + text + " - Actual: " + wel.getText());
		}
	}

	public void elementTextMatches(String dec, WebElement wel, String regex) {
		if (wel.getText().matches(regex)) {
			test.log(LogStatus.PASS, dec + " was verify passed. Expected: " + regex + " - Actual: " + wel.getText());
		} else {
			test.log(LogStatus.FAIL, dec + " was verify failed. Expected: " + regex + " - Actual: " + wel.getText());
		}
	}

	public boolean verifyElementAttribute(WebElement wel, String attribute, String dec, String expected) {
		boolean result = false;
		String attr = wel.getAttribute(attribute);
		if (attr.equals(expected)) {
			test.log(LogStatus.PASS, "Verify attribute '" + attribute + "' for " + dec + " is passed. Expected: "
					+ expected + " - Actual: " + attr);
			result = true;
		} else {
			test.log(LogStatus.FAIL, "Verify attribute '" + attribute + "' for " + dec + " is faled. Expected: "
					+ expected + " - Actual: " + attr);
		}
		return result;
	}

	public void verifyErrMessages(String description, WebElement wel, String errMes) {
		if (errMes.equals(""))
			return;
		PageObject pageObject = new PageObject();
		if (pageObject.elementExisted(wel)) {
			elementTextEqual(description, wel, errMes);
		} else {
			test.log(LogStatus.FAIL, description + " does not show error message");
		}
	}

	public void verifyErrMessages(String description, String xpath, String errMes) {
		if (errMes.equals("")) {
			verifyErrMessageNotExist(description, xpath);
		} else {
			PageObject pageObject = new PageObject();
			if (pageObject.waitExist(xpath, 3)) {
				WebElement wel = driver.findElement(By.xpath(xpath));
				elementTextEqual(description, wel, errMes);
			} else {
				test.log(LogStatus.FAIL, description + " does not show error message");
			}
		}
	}

	public void verifyErrMessageNotExist(String des, String xpath) {
		PageObject pageObject = new PageObject();
		if (!pageObject.waitNotExist(xpath, 1)) {
			WebElement wel = driver.findElement(By.xpath(xpath));
			if (wel.getText().length() > 1)
				test.log(LogStatus.FAIL, "Message error for " + des + " still show: " + wel.getText());
		}
	}

	public void verifyElementNotExist(String des, String xpath, int timeWaitSecond) {
		PageObject pageObject = new PageObject();
		if (!pageObject.waitNotExist(xpath, timeWaitSecond)) {
			test.log(LogStatus.FAIL, des + " still show: ");
		}
	}

	public void verifyElementExist(String des, String xpath, int timeWaitSecond) {
		PageObject pageObject = new PageObject();
		if (!pageObject.waitExist(xpath, timeWaitSecond)) {
			test.log(LogStatus.FAIL, des + " still show: ");
		}
	}

	public void verifyCheckBoxInput(String dec, WebElement wel, boolean checked) {
		if (checked) {
			if (wel.isSelected())
				test.log(LogStatus.PASS, "Verify check box '" + dec + "' is passed. Expected: " + checked
						+ " - Actual: " + wel.isSelected());
			else
				test.log(LogStatus.PASS, "Verify check box '" + dec + "' is failed. Expected: " + checked
						+ " - Actual: " + wel.isSelected());
		} else {
			if (!wel.isSelected())
				test.log(LogStatus.PASS, "Verify check box '" + dec + "' is passed. Expected: " + checked
						+ " - Actual: " + wel.isSelected());
			else
				test.log(LogStatus.PASS, "Verify check box '" + dec + "' is failed. Expected: " + checked
						+ " - Actual: " + wel.isSelected());
		}
	}

	// Check broken link
	public boolean verifyLinkWorking(String linkUrl, String dec) {
		boolean result = false;
		try {
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(30000);
			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200 || httpURLConnect.getResponseCode() == 302) {
				test.log(LogStatus.PASS, "Verify " + dec + " is passed");
				result = true;
			} else {
				test.log(LogStatus.FAIL, "Verify " + dec + " is failed. " + httpURLConnect.getResponseMessage() + " - "
						+ httpURLConnect.getResponseCode());
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Verify " + dec + " is failed. " + e.getMessage());
		}
		return result;
	}

	public void textMatches(String des, String actualValue, String expectedValue) {
		if (actualValue.matches(actualValue)) {
			test.log(LogStatus.PASS,
					des + " was verify passed. Expected: " + expectedValue + " - Actual: " + actualValue);
		} else {
			test.log(LogStatus.FAIL,
					des + " was verify failed. Expected: " + expectedValue + " - Actual: " + actualValue);
		}
	}
}
