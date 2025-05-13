package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.junit.Rule;
import org.mobile.config.ExtentReportManager;
import org.mobile.config.TestExecutionConfig;
import org.mobile.utils.ConfigReader;
import org.mobile.utils.DevicesConfigReader;
import org.mobile.utils.TestResultLogger;

import static org.mobile.base.DriverManager.parsePlatform;
import static org.mobile.config.LogConfig.getLogger;
import static org.mobile.config.LogConfig.logInfo;

public class TestHooks {

    protected AppiumDriver driver;

    @Rule
    public TestResultLogger testResultLogger = new TestResultLogger();

    @BeforeAll
    public static void projectSetUp() {
        getLogger().info("Configs setting:");
        TestExecutionConfig.initialize();
        ThreadLocalManager.osPlatformTL.set(parsePlatform(ConfigReader.get("platform")));

        logInfo("Tests Starting...\nTests will run through Selenium Grid.");

        if (ThreadLocalManager.getIsParallelEnabled()) {
            logInfo("Tests will run parallel. Starting all Appium servers...");
            DevicesConfigReader.getDeviceConfigs().forEach(AppiumServerManager::startServer);
        } else {
            logInfo("Tests will run single-thread. Starting one Appium server...");
            DevicesConfigReader.getDeviceConfigs().stream().findFirst().ifPresent(AppiumServerManager::startServer);
        }
        System.out.println("Tests will run through Selenium Grid.");
    }

    @Before(order = 1)
    public void setUpTestCase(Scenario scenario) {
        logInfo("Starting test: " + scenario.getName());
        logInfo("Test running on thread: " + Thread.currentThread().getName());
        driver = DriverManager.getDriver();
        ExtentReportManager.startTest(scenario.getName());
        getLogger().info("\033[31mDriver Options:\n{}\n\n\033[0m",driver.getCapabilities());
    }

    @After(order = 1)
    public void tearDownTestCase(Scenario scenario) {
        logInfo("Ending test: [%s] --> [%s]".formatted(scenario.getName(), scenario.getStatus()));
        ExtentReportManager.endTest();
        DriverManager.quitDriver();
    }

    @AfterAll(order = 0)
    public static void projectTearDown() {
        logInfo("\033[31mAll tests completed.\033[0m");
    }
}
