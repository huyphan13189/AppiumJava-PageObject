package pages.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class PageObject {
	public WebDriver driver = PageTest.driver;
	public ExtentTest test = PageTest.test;

	public void sleep(int timeSecond) {
		try {
			Thread.sleep(timeSecond * 1000);
		} catch (Exception e) {
		}

	}

	// Explicit wait time
	private WebDriverWait initWait(int time) {
		return new WebDriverWait(driver, time);
	}

	public boolean waitElementVisible(WebElement we, int time) {
		try {
			WebDriverWait wa = initWait(time);
			wa.until(ExpectedConditions.visibilityOf(we));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean waitElementInvisible(WebElement we, int time) {
		try {
			WebDriverWait wa = initWait(time);
			wa.until(ExpectedConditions.invisibilityOf(we));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void waitListElementVisible(List<WebElement> we, int time) {
		WebDriverWait wa = initWait(time);
		wa.until(ExpectedConditions.visibilityOfAllElements(we));
	}

	public void waitElementClickable(WebElement we, int time) {
		WebDriverWait wa = initWait(time);
		wa.until(ExpectedConditions.elementToBeClickable(we));
	}

	public void waitElementPresence(String xpath, int time) {
		WebDriverWait wa = initWait(time);
		wa.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	}

	public boolean waitExist(String xpath, int timeSecond) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = false;
		while (timeSecond > 0) {
			if (driver.findElements(By.xpath(xpath)).size() > 0) {
				exists = true;
				break;
			}
			sleep(1);
			timeSecond--;
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return exists;
	}

	public boolean waitNotExist(String xpath, int timeSecond) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean notExists = false;
		while (timeSecond > 0) {
			if (driver.findElements(By.xpath(xpath)).size() == 0) {
				notExists = true;
				break;
			}
			sleep(1);
			timeSecond--;
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return notExists;
	}

	// Element action
	public WebElement findWebElement(By by) {
		WebElement wb = null;
		try {
			return driver.findElement(by);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return wb;
	}

	public void clickUtilClickable(WebElement we, int time) {
		waitElementClickable(we, time);
		int timeout = 0;
		while (timeout <= time) {
			try {
				we.click();
				break;
			} catch (Exception e) {
				sleep(1);
				timeout += 1;
			}
		}
	}
	

	public void clickUtilInvisible(WebElement we, int time) {
		int i = 1;
		while (elementEndabled(we)) {
			clickUtilClickable(we, time);
			sleep(1);
			i++;
			if (i > time)
				break;
		}
	}

	public boolean elementEndabled(WebElement we) {
		try {
			return we.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean elementExisted(WebElement we) {
		if (we != null) {
			return true;
		} else {
			return false;
		}
	}

	public void waitVisibleThenClick(int time, WebElement we) {
		waitElementVisible(we, time);
		we.click();
	}

	public void clickUntilNotExist(String xpath, int timeSeconds) {
		while(timeSeconds>0) {
			sleep(1);
			if(!xpathExist(xpath))
				break;
			WebElement wel = driver.findElement(By.xpath(xpath));
			wel.click();
			timeSeconds--;
		}
	}
	
	public boolean xpathExist(String xpath) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exist = driver.findElements(By.xpath(xpath)).size()>0;
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		return exist;
	}

	// Input text

	public void sendKeyUntil(WebElement we, String text) {
		int i = 1;
		while (i <= 5) {
			we.clear();
			we.sendKeys(text);
			sleep(1);
			if (we.getAttribute("value").equals(text))
				break;
			i++;
		}
	}

	public void inputText(WebElement we, String text) {
		if (!text.equals("")) {
			if (text.equals("empty"))
				emptyText(we);
			else {
				we.clear();
				we.sendKeys(text);
			}
		}
	}

	public void inputTextUntil(WebElement we, String text) {
		if (!text.equals("")) {
			if (text.equals("empty"))
				emptyText(we);
			else {
				sendKeyUntil(we, text);
			}
		}
	}

	public void emptyText(WebElement we) {
		for (int i = 1; i <= 5; i++) {
			we.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
			sleep(1);
			if (we.getAttribute("value").equals(""))
				return;
		}

	}

	// Focus a element
	public void focus(WebElement wel) {
		if ("input".equals(wel.getTagName())) {
			wel.sendKeys("");
		} else {
			new Actions(driver).moveToElement(wel).perform();

		}
	}

	// Drop down list support
	public void dropDownListSelectText(WebElement select, String text) {
		Select sl = new Select(select);
		sl.selectByVisibleText(text);
	}

	public void dropDownListSelectIndex(WebElement select, int index) {
		Select sl = new Select(select);
		sl.selectByIndex(index);
	}

	public void dropDownListSelectValue(WebElement select, String value) {
		Select sl = new Select(select);
		sl.selectByValue(value);
	}

	public String dropDownListDivWithName(WebElement openList, List<WebElement> list, String name) {
		openList.click();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().equals(name)) {
				list.get(i).click();
				break;
			}
		}
		return openList.getText();
	}

	public String randomSelectControl(WebElement selectControl, List<WebElement> selectOption) {
		selectControl.click();
		Random rn = new Random();
		sleep(1);
		WebElement optionSelected = selectOption.get(rn.nextInt(selectOption.size()));
		optionSelected.click();
		return selectControl.getText();
	}

	public String randomSelectControl(WebElement selectControl, List<WebElement> selectOption, String option) {
		selectControl.click();
		Random rn = new Random();
		WebElement optionSelected = null;
		sleep(1);
		if (option.equals("select")) {
			optionSelected = selectOption.get(0);
		} else {
			optionSelected = selectOption.get(rn.nextInt(selectOption.size() - 1) + 1);
		}
		optionSelected.click();
		return selectControl.getText();
	}

	// Check box

	public void checkboxInput(WebElement checkbox, boolean checked) {
		int i = 1;
		if (checked) {
			while (i <= 3) {
				if (checkbox.isSelected())
					break;
				else {
					checkbox.click();
					sleep(1);
					i++;
				}

			}
		} else {
			i = 1;
			while (i <= 3) {
				if (!checkbox.isSelected())
					break;
				else {
					checkbox.click();
					sleep(1);
					i++;
				}

			}
		}
	}

	// Handle pop up
	public void switchBrowserPopup(String mainWindow) {
		Set<String> setWindow = driver.getWindowHandles();
		for (String window : setWindow) {
			if (!window.equalsIgnoreCase(mainWindow)) {
				driver.switchTo().window(window);
			}
		}
	}

	public void acceptAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {

		}
	}

	public void dissmisAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {

		}
	}

	public boolean waitSwitchFrame(String frameName, int time) {
		boolean rs = false;
		try {
			WebDriverWait wa = initWait(time);
			driver.switchTo().defaultContent();
			wa.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
			rs = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}

	// Get text functions
	public List<String> getListTextLW(List<WebElement> lw) {
		List<String> result = new ArrayList<>();
		for (WebElement wel : lw) {
			result.add(wel.getText().trim());
		}
		return result;
	}

	// Verify functions
	public boolean verifyElementVisible(WebElement wel, String dec) {
		if (waitElementVisible(wel, 5)) {
			test.log(LogStatus.PASS, "Verify '" + dec + "' visible is passed");
			return true;
		} else {
			test.log(LogStatus.FAIL, "Verify '" + dec + "' visible is failed");
			return false;
		}
	}

}
