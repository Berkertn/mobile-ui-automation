package org.mobile.base;

import io.appium.java_client.AppiumDriver;

public class ThreadLocalManager {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
}
