package org.mobile.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.mobile.config.ExtentReportManager;

import java.util.Optional;

import static org.mobile.config.LogConfig.*;

public class TestResultLogger implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = context.getDisplayName();
        logInfo("Test Passed: " + testName);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        logInfo("Test Failed: " + testName + " - Error: " + cause.getMessage());
        String screenshotPath = ScreenshotUtil.captureScreenshot(testName);
        logDebug("Screenshot Captured and s: " + screenshotPath);
        ExtentReportManager.getTest().fail("Test Failed: " + testName + " - Error: " + cause.getMessage(),
                MediaEntityBuilder.createScreenCaptureFromPath("../../" + screenshotPath).build());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        logInfo("Test Aborted: " + testName + " - Reason: " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        String testName = context.getDisplayName();
        logInfo("Test Disabled: " + testName + " - Reason: " + reason.orElse("Unknown"));
    }
}

