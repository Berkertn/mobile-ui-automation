package org.mobile.utils;


import org.mobile.base.DriverManager;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class LocatorMap {
    private final Map<String, Map<String, By>> locatorMap = new HashMap<>();

    /**
     * @param key      element's key name (e.g.: buttonLocator)
     * @param platform Platform name (android" or "iOS"
     * @param locator  By nesnesi.
     */
    public void addLocator(String key, DriverManager.OS_TYPES platform, By locator) {
        locatorMap.computeIfAbsent(key, k -> new HashMap<>()).put(String.valueOf(platform), locator);
    }

    /**
     * Based on the platform and key, returns the By (locator)
     *
     * @param key      element's key name (e.g.: buttonLocator)
     * @param platform
     * @return By
     */
    public By getLocator(String key, DriverManager.OS_TYPES platform) {
        Map<String, By> platformLocators = locatorMap.get(key);
        if (platformLocators == null || !platformLocators.containsKey(platform.toString())) {
            throw new RuntimeException("Locator not found for: " + key + " on platform: " + platform);
        }
        return platformLocators.get(platform.toString());
    }
}
