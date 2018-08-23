package page.object;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidElement;
import pages.base.PageObject;

public class GuruLoginPO extends PageObject {
	public GuruLoginPO() {
		PageFactory.initElements(driver, this);
	}

	// Define elements ****************************************************
	@FindBy(name = "uid")
	public WebElement userID;

	@FindBy(name = "password")
	public WebElement password;

	@FindBy(name = "btnLogin")
	public WebElement btnLogin;
	
	@FindBy(name = "btnReset")
	public WebElement btnReset;

	// Define methods *****************************************************
	public void waitPage(int time) {
		waitElementVisible(btnLogin, time);
	}
	
	public void login(String user, String pass) {
		userID.sendKeys(user);
		password.sendKeys(pass);
		btnLogin.click();
	}
		
}
