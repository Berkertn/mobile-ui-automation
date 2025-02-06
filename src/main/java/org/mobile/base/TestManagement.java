package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mobile.utils.TestResultLogger;

import static org.mobile.config.LogConfig.logInfo;

@ExtendWith(TestResultLogger.class)
public class TestManagement {

    protected AppiumDriver driver;

    @BeforeAll
    static void projectSetUp() {
        logInfo("Tests Starting...");
        AppiumServerManager.startServer();
    }

    @BeforeEach
    public void setUpTestCase(TestInfo testInfo) {
        driver = DriverManager.getDriver();
    }

    @AfterEach
    public void tearDownTestCase(TestInfo testInfo) {
        DriverManager.quitDriver();
    }

    @AfterAll
    static void projectTearDown() {
        logInfo("Tests completed!");
        AppiumServerManager.stopServer();
    }
}
