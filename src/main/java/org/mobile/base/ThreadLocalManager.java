package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import lombok.Getter;
import lombok.Setter;

public class ThreadLocalManager {
    @Getter @Setter
    public static Boolean isParallelEnabled = false;
    public static ThreadLocal<AppiumDriver> driverTL = new ThreadLocal<>();
    protected static ThreadLocal<BasePage> currentPageTL = new ThreadLocal<>();
    protected static ThreadLocal<DriverManager.OS_TYPES> osPlatformTL = new ThreadLocal<>();

    public static DriverManager.OS_TYPES getOSPlatform() {
        return osPlatformTL.get();
    }

    public static BasePage getCurrentPageTL() {
        if (currentPageTL.get() == null) {
            throw new AssertionError("Current page is not set yet");
        }
        return currentPageTL.get();
    }
}
