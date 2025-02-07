package org.mobile.base;

import io.appium.java_client.AppiumDriver;
import org.mobile.utils.LocatorMap;


import static org.mobile.base.ThreadLocalManager.getOSPlatform;


abstract public class BasePage {
    protected static final AppiumDriver driver = DriverManager.getDriver();
    protected static final DriverManager.OS_TYPES platform = getOSPlatform();
    protected final LocatorMap locators = new LocatorMap();

    public LocatorMap getLocators() {
        return locators;
    }
}
