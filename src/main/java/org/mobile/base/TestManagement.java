package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mobile.config.ExtentReportManager;
import org.mobile.utils.TestResultLogger;

import static org.mobile.config.LogConfig.logInfo;

@ExtendWith(TestResultLogger.class)
public class TestManagement {

    protected AppiumDriver driver;

    @Before(order = 0)
    public void projectSetUp() {
        logInfo("Tests Starting...");
        AppiumServerManager.startServer();
    }

    @Before(order = 1)
    public void setUpTestCase(Scenario scenario) {
        driver = DriverManager.getDriver();
        ExtentReportManager.startTest(scenario.getName());
    }

    @After(order = 1)
    public void tearDownTestCase(Scenario scenario) {
        ExtentReportManager.endTest();
        DriverManager.quitDriver();
    }

    @After(order = 0)
    public void projectTearDown() {
        logInfo("Tests completed!");
        AppiumServerManager.stopServer();
    }
}
