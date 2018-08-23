package pages.base;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;


public class CustomTestListener implements ITestListener{
	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if (PageTest.test.getRunStatus().toString().equals("fail")) {
			result.setStatus(ITestResult.FAILURE);
			ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
            tc.getPassedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
            tc.getFailedTests().addResult(result, Reporter.getCurrentTestResult().getMethod());
		}
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}
}
