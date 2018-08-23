package page.object;

import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.base.PageObject;

public class GuruBankPO extends PageObject {
	public GuruBankPO() {
		PageFactory.initElements(driver, this);
	}

	// Define elements ****************************************************
	@FindBy(xpath = "//*[@class='heading3']//*[contains(text(),'Manger Id')]")
	public WebElement mangerId;

	public WebElement menuName(String name) {
		String xpath = "//ul[@class='menusubnav']//li//a[text()='" + name + "']";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement inputName(String name) {
		String xpath = "//td[text()='" + name + "']/following-sibling::td//input";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement inputContainName(String name) {
		String xpath = "//td[contains(text(),'" + name + "')]/following-sibling::td//input";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement textareaName(String name) {
		String xpath = "//td[text()='" + name + "']/following-sibling::td//textarea";
		return driver.findElement(By.xpath(xpath));
	}

	public WebElement radioValue(String name) {
		String xpath = "//input[@type='radio' and @value='" + name + "']";
		return driver.findElement(By.xpath(xpath));
	}

	public String btnName_xp(String name) {
		return "//td//input[@value='" + name + "']";
	}

	public WebElement btnName(String name) {
		return driver.findElement(By.xpath(btnName_xp(name)));
	}

	public WebElement tdInfo(String name) {
		String xpath = "//td[text()='" + name + "']/following-sibling::td";
		return driver.findElement(By.xpath(xpath));
	}

	// Date Time
	@FindBy(xpath = "//android.widget.Button[@resource-id='android:id/button3']")
	public WebElement btnSet;

	// Define methods *****************************************************
	public void waitPage(int time) {
		waitElementVisible(mangerId, time);
	}

	public HashMap<String, String> setupCustomerData(HashMap<String, String> excelData) {
		if (excelData.get("Email").equals("random")) {
			excelData.put("Email", "huy" + System.currentTimeMillis() + "@gmail.com");
		}
		return excelData;
	}

	public void customerInput(HashMap<String, String> excelData) {
		if (!excelData.get("CustomerName").equals("")) {
			inputName("Customer Name").sendKeys(excelData.get("CustomerName"));
		}

		if (!excelData.get("Gender").equals("")) {
			checkboxInput(radioValue(excelData.get("Gender")), true);
		}

		if (!excelData.get("DateofBirth").equals("")) {
//			inputName("Date of Birth").click();
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].value='"+excelData.get("DateofBirth")+"';", inputName("Date of Birth"));
			inputName("Date of Birth").click();
			inputName("Password").click();
			
		}

		if (!excelData.get("Address").equals("")) {
			textareaName("Address").sendKeys(excelData.get("Address"));
		}

		if (!excelData.get("City").equals("")) {
			inputName("City").sendKeys(excelData.get("City"));
		}

		if (!excelData.get("State").equals("")) {
			inputName("State").sendKeys(excelData.get("State"));
		}

		if (!excelData.get("PIN").equals("")) {
			inputName("PIN").sendKeys(excelData.get("PIN"));
		}

		if (!excelData.get("MobileNumber").equals("")) {
			inputName("Mobile Number").sendKeys(excelData.get("MobileNumber"));
		}

		if (!excelData.get("Email").equals("")) {
			inputName("E-mail").sendKeys(excelData.get("Email"));
		}

		if (!excelData.get("Password").equals("")) {
			inputName("Password").sendKeys(excelData.get("Password"));
		}

	}

	public HashMap<String, String> setupAcountData(HashMap<String, String> excelData) {
		excelData.put("AccountType", "Savings");
		excelData.put("InitialDeposit", "500");
		return excelData;
	}

	public void acountInput(HashMap<String, String> excelData) {
		if (!excelData.get("CustomerID").equals("")) {
			inputName("Customer id").sendKeys(excelData.get("CustomerID"));
		}

		if (!excelData.get("AccountType").equals("")) {

		}

		if (!excelData.get("InitialDeposit").equals("")) {
			inputContainName("Initial deposit").sendKeys(excelData.get("InitialDeposit"));
		}
	}

	public HashMap<String, String> setupDepositData(HashMap<String, String> customerData, HashMap<String, String> depositData) {
		customerData.put("DepositDescription", depositData.get("Description"));
		customerData.put("DepositAmount", depositData.get("Amount"));
		customerData.put("DepositDescription", depositData.get("DescriptionDepo"));
		return customerData;
	}

	public void depositInput(HashMap<String, String> excelData) {
		if (!excelData.get("AccountID").equals("")) {
			inputName("Account No").sendKeys(excelData.get("AccountID"));
		}

		if (!excelData.get("DepositAmount").equals("")) {
			inputName("Amount").sendKeys(excelData.get("DepositAmount"));
		}

		if (!excelData.get("DepositDescription").equals("")) {
			sendKeyUntil(inputName("Description"), excelData.get("DepositDescription"));
		}
	}
}
