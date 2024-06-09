package com.company.automation.reporting;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import static com.company.automation.config.ConfigurationManager.configuration;

/**
 * @author Zeeshan
 */
public class ExtentManager {

	public static final ExtentReports extentReports = new ExtentReports();
	public synchronized static ExtentReports createExtentReports() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(configuration().baseReportPath());
		reporter.config().setReportName("Automation Extent Report");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Platform", System.getProperty("os.name"));
		extentReports.setSystemInfo("Version", System.getProperty("os.version"));
		extentReports.setSystemInfo("Browser", configuration().browser());
		extentReports.setSystemInfo("Base URL", configuration().baseUrl());
		return extentReports;
	}





}
