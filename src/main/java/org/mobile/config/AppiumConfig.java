package org.mobile.config;

import org.mobile.utils.ConfigReader;

public class AppiumConfig {
    public static final int APPIUM_IMPLICITLY_WAIT_SECONDS =
            (ConfigReader.get("appium_implicitly_wait_seconds") != null && !ConfigReader.get("appium_implicitly_wait_seconds").isEmpty())
                    ? Integer.parseInt(ConfigReader.get("appium_implicitly_wait_seconds"))
                    : 10;
}
