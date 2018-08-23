package page.test;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import page.object.GuruBankPO;
import pages.base.PageTest;
import utilities.DateTime;
import utilities.Excel;

public class GuruBankTest extends PageTest {
	private GuruLoginTest guruLoginTest;
	private GuruBankPO guruBankPO;

	public void initialPageObject() {
		guruLoginTest = new GuruLoginTest();
		guruBankPO = new GuruBankPO();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		initialPageObject();
		driver.navigate().to("http://demo.guru99.com/v4/");
	}

	public GuruBankTest() {
		initialPageObject();
	}

	@Test(groups = { "GuruNewCustomer01" })
	public void GuruNewCustomer() {
		HashMap<String, String> customerExcel = Excel
				.readXSLXFileID("test-data/GuruBank.xlsx", "NewCustomer", "NewCustomer01").get(0);
		test.log(LogStatus.INFO, "**************** Test Case: " + customerExcel.get("Description")
				+ " **********************************");
		guruLoginTest.login();
		guruBankPO.waitPage(15);

		test.log(LogStatus.INFO, "****** Step 1: Create a new customer");
		customerExcel = guruBankPO.setupCustomerData(customerExcel);
		createCustomer(customerExcel);
		customerExcel.put("CustomerID", getCustomerID());
		verifyCustomerInfo(customerExcel);

		test.log(LogStatus.INFO, "****** Step 2: Create a new account");
		customerExcel = guruBankPO.setupAcountData(customerExcel);
		createAccount(customerExcel);
		customerExcel.put("AccountID", getAccountID());
		customerExcel.put("CurrentBalance", getCurrentAmount());
		verifyAccountInfo(customerExcel);

		test.log(LogStatus.INFO, "****** Step 3: Depoist ofthe account");
		List<HashMap<String, String>> depositsExcel = Excel
				.readXSLXFileID("test-data/GuruBank.xlsx", "Deposit", "Deposit01");
		
		for(HashMap<String, String> depositData : depositsExcel) {
			customerExcel = guruBankPO.setupDepositData(customerExcel,depositData);
			deposit(customerExcel);
			int currentBalance = (Integer.parseInt(customerExcel.get("CurrentBalance")) + Integer.parseInt(customerExcel.get("DepositAmount")));
			customerExcel.put("CurrentBalance", currentBalance+"");
			verifyDepositInfo(customerExcel);
		}
		
	}

	public void createCustomer(HashMap<String, String> excelData) {
		guruBankPO.menuName("New Customer").click();
		guruBankPO.customerInput(excelData);
		test.log(LogStatus.INFO, "Create new customer" + screenShoot());
		guruBankPO.btnName("Submit").click();

	}

	public String getCustomerID() {
		guruBankPO.waitElementVisible(guruBankPO.tdInfo("Customer ID"), 15);
		return guruBankPO.tdInfo("Customer ID").getText();
	}

	public void verifyCustomerInfo(HashMap<String, String> excelData) {
		test.log(LogStatus.INFO, "Verify customer info" + screenShoot());
		elementTextEqual("Customer Name", guruBankPO.tdInfo("Customer Name"), excelData.get("CustomerName"));
		String gender = "";
		if (excelData.get("Gender").equals("m")) {
			gender = "male";
		} else if (excelData.get("Gender").equals("f")) {
			gender = "female";
		}
		elementTextEqual("Gender", guruBankPO.tdInfo("Gender"), gender);
		elementTextEqual("Address", guruBankPO.tdInfo("Address"), excelData.get("Address"));
		elementTextEqual("City", guruBankPO.tdInfo("City"), excelData.get("City"));
		elementTextEqual("State", guruBankPO.tdInfo("State"), excelData.get("State"));
		elementTextEqual("Pin", guruBankPO.tdInfo("Pin"), excelData.get("PIN"));
		elementTextEqual("Mobile No.", guruBankPO.tdInfo("Mobile No."), excelData.get("MobileNumber"));
		elementTextEqual("Email", guruBankPO.tdInfo("Email"), excelData.get("Email"));

	}

	public void createAccount(HashMap<String, String> excelData) {
		guruBankPO.menuName("New Account").click();
		guruBankPO.acountInput(excelData);
		test.log(LogStatus.INFO, "Create new account" + screenShoot());
		guruBankPO.btnName("submit").click();

	}

	public String getAccountID() {
		guruBankPO.waitElementVisible(guruBankPO.tdInfo("Account ID"), 15);
		return guruBankPO.tdInfo("Account ID").getText();
	}

	public String getCurrentAmount() {
		return guruBankPO.tdInfo("Current Amount").getText();
	}

	public void verifyAccountInfo(HashMap<String, String> excelData) {
		test.log(LogStatus.INFO, "Verify account info" + screenShoot());
		elementTextEqual("Customer ID", guruBankPO.tdInfo("Customer ID"), excelData.get("CustomerID"));
		elementTextEqual("Customer Name", guruBankPO.tdInfo("Customer Name"), excelData.get("CustomerName"));
		elementTextEqual("Email", guruBankPO.tdInfo("Email"), excelData.get("Email"));
		elementTextEqual("Account Type", guruBankPO.tdInfo("Account Type"), excelData.get("AccountType"));
		elementTextEqual("Date of Opening", guruBankPO.tdInfo("Date of Opening"),
				DateTime.getCustomTime("yyyy-MM-dd", 0));
		elementTextEqual("Current Amount", guruBankPO.tdInfo("Current Amount"), excelData.get("InitialDeposit"));
	}

	public void deposit(HashMap<String, String> excelData) {
		guruBankPO.menuName("Deposit").click();
		guruBankPO.depositInput(excelData);
		test.log(LogStatus.INFO, "Case: " + excelData.get("DepositDescription") + screenShoot());
		guruBankPO.btnName("Submit").click();
	}

	public void verifyDepositInfo(HashMap<String, String> excelData) {
		guruBankPO.waitElementVisible(guruBankPO.tdInfo("Account No"), 15);
		test.log(LogStatus.INFO, "Verify deposit info" + screenShoot());
		elementTextEqual("Account No", guruBankPO.tdInfo("Account No"), excelData.get("AccountID"));
		elementTextEqual("Amount Credited", guruBankPO.tdInfo("Amount Credited"), excelData.get("DepositAmount"));
		elementTextEqual("Type of Transaction", guruBankPO.tdInfo("Type of Transaction"), "Deposit");
		elementTextEqual("Description", guruBankPO.tdInfo("Description"), excelData.get("DepositDescription"));
		elementTextEqual("Current Balance", guruBankPO.tdInfo("Current Balance"),excelData.get("CurrentBalance"));
	}
}
