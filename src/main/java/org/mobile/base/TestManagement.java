package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mobile.config.ExtentReportManager;
import org.mobile.config.TestExecutionConfig;
import org.mobile.utils.ConfigReader;
import org.mobile.utils.DevicesConfigReader;
import org.mobile.utils.TestResultLogger;

import static org.mobile.base.DeviceManager.releaseDevice;
import static org.mobile.base.DriverManager.getDeviceConfig;
import static org.mobile.base.DriverManager.parsePlatform;
import static org.mobile.config.LogConfig.logInfo;

@ExtendWith(TestResultLogger.class)
public class TestManagement {

    protected AppiumDriver driver;

    @BeforeAll
    public static void projectSetUp() {
        logInfo("Configs setting:");
        TestExecutionConfig.initialize();
        ThreadLocalManager.osPlatformTL.set(parsePlatform(ConfigReader.get("platform")));
        logInfo("Tests Starting...");
        if (ThreadLocalManager.getIsParallelEnabled()) {
            logInfo("Tests will run parallel. Starting all Appium servers...");
            DevicesConfigReader.getDeviceConfigs().forEach(AppiumServerManager::startServer);
        } else {
            logInfo("Tests will run single-thread. Starting the Appium server...");
            DevicesConfigReader.getDeviceConfigs().stream().findFirst().ifPresent(AppiumServerManager::startServer);
        }
    }

    @Before(order = 1)
    public void setUpTestCase(Scenario scenario) {
        logInfo("Starting test: " + scenario.getName());
        logInfo("Test running on thread: " + Thread.currentThread().getName());
        driver = DriverManager.getDriver();
        ExtentReportManager.startTest(scenario.getName());
        logInfo("Test running on thread: [%s] and port: [%d] ".formatted(Thread.currentThread().getName(), getDeviceConfig().getPort()));
    }

    @After(order = 1)
    public void tearDownTestCase(Scenario scenario) {
        logInfo("Ending test: [%s] --> [%s]".formatted(scenario.getName(), scenario.getStatus()));
        ExtentReportManager.endTest();
        releaseDevice();
        DriverManager.quitDriver();
    }

    @AfterAll(order = 0)
    public static void projectTearDown() {
        logInfo("All tests completed. Stopping all Appium servers...");
        DevicesConfigReader.getDeviceConfigs().forEach(AppiumServerManager::stopServer);
        logInfo("\033[31mAll Appium servers have been stopped.\033[0m");
    }
}
