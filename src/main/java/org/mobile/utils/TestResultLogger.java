package org.mobile.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.mobile.config.ExtentReportManager;

import static org.mobile.config.LogConfig.logDebug;
import static org.mobile.config.LogConfig.logInfo;

public class TestResultLogger extends TestWatcher {

    @Override
    protected void succeeded(Description description) {
        String testName = description.getDisplayName();
        logInfo("Test Passed: " + testName);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        String testName = description.getDisplayName();
        logInfo("Test Failed: " + testName + " - Error: " + e.getMessage());
        String screenshotPath = ScreenshotUtil.captureScreenshot(testName);
        logDebug("Screenshot Captured and saved: " + screenshotPath);
        ExtentReportManager.getTest().fail("Test Failed: " + testName + " - Error: " + e.getMessage(),
                MediaEntityBuilder.createScreenCaptureFromPath("../../" + screenshotPath).build());
    }

    @Override
    protected void starting(Description description) {
        String testName = description.getDisplayName();
        logInfo("Test Starting: " + testName);
    }
}
