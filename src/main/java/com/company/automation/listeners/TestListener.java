package com.company.automation.listeners;


import com.aventstack.extentreports.MediaEntityBuilder;
import com.company.automation.reporting.ExtentManager;
import com.company.automation.reporting.ExtentReport;
import com.company.automation.data.BaseData;
import com.company.automation.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import static com.company.automation.config.ConfigurationManager.configuration;


/**
 * @author Zeeshan
 */

public class TestListener implements ITestListener {

    private String testCaseDescription ;

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        ITestNGMethod method = iTestResult.getMethod();
        BaseData baseData = (BaseData) iTestResult.getParameters()[0];
        String testCaseId = baseData.getTestCaseId();
        testCaseDescription = baseData.getTestCaseDescription();
        String testData = baseData.toString();

        ExtentReport.createTest(String.format("[%s] %s", testCaseId, testCaseDescription))
                .assignCategory(method.getRealClass().getSimpleName())
                .pass(String.format("<b>Test Data:</b> %s", testData));
        LogUtils.info(testCaseId + "  " + testCaseDescription+ " test is started.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
            LogUtils.pass(testCaseDescription+ " test is succeed.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ITestNGMethod method = iTestResult.getMethod();
        ExtentReport.getTest()
                .fail(
                        iTestResult.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(
                                        String.format(
                                                "%s%s.png",System.getProperty("user.dir")+"/"+
                                                configuration().baseScreenshotPath(),
                                                method.getMethodName()))
                                .build());
        LogUtils.fail(testCaseDescription+ " test is failed.");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
            LogUtils.skip(testCaseDescription + " test is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LogUtils.fail("Test failed but it is in defined success ratio " + testCaseDescription);
    }
}
