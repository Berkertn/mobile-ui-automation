package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.mobile.utils.ConfigReader;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mobile.base.ThreadLocalManager.driver;
import static org.mobile.config.LogConfig.*;

public class DriverManager {

    public static AppiumDriver getDriver() {
        if (driver.get() == null) {
            logInfo("Creating new driver instance");
            driver.set(createDriver());
        }
        return driver.get();
    }

    private static AppiumDriver createDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        String platform = ConfigReader.get("platform");
        String deviceName = ConfigReader.get("deviceName");
        String automationName = ConfigReader.get("automationName");
        String appPath = ConfigReader.get("appPath");
        String serverUrl = ConfigReader.get("appiumServerUrl");
        logDebug("Creating driver instance for [%s] and device [%s], automationName [%s], appPath [%s], serverUrl [%s]".formatted(platform, deviceName, automationName, appPath, serverUrl));

        capabilities.setCapability("platformName", platform);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("automationName", automationName);
        capabilities.setCapability("app", appPath);

        try {
            return platform.equalsIgnoreCase("ios") ? new IOSDriver(new URL(serverUrl), capabilities) : new AndroidDriver(new URL(serverUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium Server URL", e);
        } finally {
            if (driver != null) {
                logInfo("\033[32m Driver initialaze successfuly \033[0m");
            }
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logInfo("Driver quit and removed successfully");
        }
    }
}

