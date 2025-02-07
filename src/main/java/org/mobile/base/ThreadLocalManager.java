package org.mobile.base;

import io.appium.java_client.AppiumDriver;

public class ThreadLocalManager {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<BasePage> currentPage = new ThreadLocal<>();
    protected static ThreadLocal<DriverManager.OS_TYPES> osPlatform = new ThreadLocal<>();

    public static DriverManager.OS_TYPES getOSPlatform() {
        return osPlatform.get();
    }

    public static BasePage getCurrentPage() {
        if (currentPage.get() == null) {
            throw new AssertionError("Current page is not set yet");
        }
        return currentPage.get();
    }
}
