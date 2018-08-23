package page.test;

import org.testng.annotations.BeforeMethod;
import page.object.GuruLoginPO;
import pages.base.PageTest;

public class GuruLoginTest extends PageTest{
	private GuruLoginPO guruLoginPO;
	
	public void initialPageObject() {
		guruLoginPO = new GuruLoginPO();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		initialPageObject();
	}

	public GuruLoginTest() {
		initialPageObject();
	}
	
	public void login() {
		guruLoginPO.waitPage(15);
		guruLoginPO.login("mngr150118", "yvajUgU");
	}

}
