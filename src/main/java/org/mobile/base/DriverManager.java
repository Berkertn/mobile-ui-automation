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
        DeviceConfig deviceConfig = getDeviceConfig();

        if (deviceConfig == null) {
            throw new RuntimeException("No available device found for test execution!");
        }

        logDebug("Using device: [%s], Port: [%d]".formatted(deviceConfig.getDeviceName(), deviceConfig.getPort()));

        DesiredCapabilities capabilities = getCapabilities(deviceConfig);
        logInfo("Setup driver: Initializing driver for device [%s] on port [%d]".formatted(deviceConfig.getDeviceName(), deviceConfig.getPort()));
        initializeDriver(deviceConfig, capabilities);
    }

    public static DeviceConfig getDeviceConfig() {
        DeviceConfig device = ThreadLocalManager.getIsParallelEnabled()
                ? DeviceManager.getCurrentDevice()
                : DevicesConfigReader.getDeviceConfigs().stream().findFirst().orElse(null);
        logDebug("Device config: " + device);
        return device;
    }

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

    private static void initializeDriver(DeviceConfig deviceConfig, DesiredCapabilities capabilities) {
        String serverUrl = ConfigReader.get("appiumServerUrl") + ":" + deviceConfig.getPort();
        logInfo("\n[Thread-%s]Driver will init for device [%s] on port [%d]\t".formatted(Thread.currentThread().getName(), deviceConfig.getDeviceName(), deviceConfig.getPort()));

        try {
            if (deviceConfig.getPlatform() == OS_TYPES.iOS) {
                ThreadLocalManager.osPlatformTL.set(OS_TYPES.iOS);
                driverTL.set(new IOSDriver(new URL(serverUrl), capabilities));
            } else {
                ThreadLocalManager.osPlatformTL.set(OS_TYPES.android);
                AndroidDriver driver = new AndroidDriver(new URL(serverUrl), capabilities);
                driverTL.set(driver);
            }

            deviceMap.put(Thread.currentThread().getId(), deviceConfig);
            logInfo("[Thread-%s]Created driver: [%s]\n".formatted(Thread.currentThread().getName(), driverTL.get()));
            logInfo("Driver initialized successfully for device [%s] on port [%d]".formatted(deviceConfig.getDeviceName(), deviceConfig.getPort()));

        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium Server URL: " + serverUrl, e);
        }
    }

    public static void quitDriver() {
        Long threadId = Thread.currentThread().getId();
        DeviceConfig deviceConfig = deviceMap.get(threadId);

        logDebug("Current device map: " + deviceMap);

        if (deviceConfig == null) {
            throw new RuntimeException("No active device found for thread ID [%d]. The test may not have initialized correctly.".formatted(threadId));
        }

        if (driverTL.get() == null) {
            logDebug("No active driver found for thread ID [%d]. The driver may have never been initialized or was already quit.".formatted(threadId));
            return;
        }

        logInfo("Releasing device [%s] on port [%d]".formatted(deviceConfig.getDeviceName(), deviceConfig.getPort()));
        deviceMap.remove(threadId);

        driverTL.get().quit();
        driverTL.remove();
        logInfo("Driver quit and removed successfully");
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
