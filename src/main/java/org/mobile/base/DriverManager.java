package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.mobile.config.DeviceConfig;
import org.mobile.utils.ConfigReader;
import org.mobile.utils.DevicesConfigReader;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.mobile.base.ThreadLocalManager.driverTL;
import static org.mobile.config.LogConfig.*;

public class DriverManager {

    public enum OS_TYPES {
        iOS, android
    }

    private static final ConcurrentHashMap<Long, DeviceConfig> deviceMap = new ConcurrentHashMap<>();

    public static AppiumDriver getDriver() {
        if (driverTL.get() == null) {
            logInfo("Creating new driver instance...");
            setupDriver();
        }
        return driverTL.get();
    }

    private static void setupDriver() {
        DesiredCapabilities capabilities = getCapabilities();
        initializeDriver(capabilities);
    }

    public static DeviceConfig getDeviceConfig() {
        DeviceConfig device = ThreadLocalManager.getIsParallelEnabled()
                ? DeviceManager.getCurrentDevice()
                : DevicesConfigReader.getDeviceConfigs().stream().findFirst().orElse(null);
        logDebug("Device config: " + device);
        return device;
    }

    @Deprecated
    private static DesiredCapabilities getCapabilities(DeviceConfig deviceConfig) {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", ConfigReader.get("platform"));
        //capabilities.setCapability("appium:deviceName", deviceConfig.getDeviceName());
        capabilities.setCapability("appium:udid", deviceConfig.getDeviceUDID());
        capabilities.setCapability("appium:automationName", deviceConfig.getPlatform() == OS_TYPES.iOS ? "XCUITest" : "UIAutomator2");
        capabilities.setCapability("appium:app", ConfigReader.get("appPath") +
                (deviceConfig.getPlatform() == OS_TYPES.iOS ? ConfigReader.get("iosAppName") : ConfigReader.get("androidAppName")));

        if (deviceConfig.getPlatform() == OS_TYPES.iOS) {
            capabilities.setCapability("appium:bundleId", ConfigReader.get("bundleId"));
        } else {
            capabilities.setCapability("appium:appPackage", ConfigReader.get("appPackage"));
            Optional.ofNullable(ConfigReader.get("appActivity"))
                    .filter(appActivity -> !appActivity.isEmpty())
                    .ifPresent(appActivity -> capabilities.setCapability("appium:appActivity", appActivity));
        }
        capabilities.asMap().forEach((key, value) -> getLogger().info("Capability: {} = {}", key, value));
        return capabilities;
    }

    private static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:app", ConfigReader.get("appPath") + ConfigReader.get("androidAppName"));
        capabilities.setCapability("appium:appPackage", ConfigReader.get("appPackage"));
        Optional.ofNullable(ConfigReader.get("appActivity"))
                .filter(appActivity -> !appActivity.isEmpty())
                .ifPresent(appActivity -> capabilities.setCapability("appium:appActivity", appActivity));
        capabilities.asMap().forEach((key, value) ->
                getLogger().info("Capability: {} = {}", key, value));
        return capabilities;
    }

    private static void initializeDriver(DesiredCapabilities capabilities) {
        String serverUrl = "http://localhost:4444/wd/hub"; // Selenium Grid

        try {
            ThreadLocalManager.osPlatformTL.set(OS_TYPES.android);
            AppiumDriver driverInstance = new AndroidDriver(new URL(serverUrl), capabilities);
            driverTL.set(driverInstance);

            logInfo("[Thread-%s] Driver initialized via Grid".formatted(Thread.currentThread().getName()));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL: " + serverUrl, e);
        }
    }

    public static void quitDriver() {
        if (ThreadLocalManager.driverTL.get() != null) {
            ThreadLocalManager.driverTL.get().quit();
            ThreadLocalManager.driverTL.remove();
            logInfo("Driver quit successfully and removed from ThreadLocal.");
        }
    }

    public static OS_TYPES parsePlatform(String platform) {
        if (platform == null || platform.isEmpty()) {
            throw new IllegalArgumentException("Platform type is not provided");
        }
        return switch (platform.toLowerCase()) {
            case "android" -> OS_TYPES.android;
            case "ios" -> OS_TYPES.iOS;
            default -> throw new IllegalArgumentException("Invalid platform type: " + platform);
        };
    }
}
