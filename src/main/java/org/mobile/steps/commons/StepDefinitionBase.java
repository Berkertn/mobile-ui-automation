package org.mobile.steps.commons;

import org.mobile.base.BasePage;
import org.mobile.base.PageManager;
import org.openqa.selenium.By;

import static org.mobile.base.ThreadLocalManager.getCurrentPage;
import static org.mobile.base.ThreadLocalManager.getOSPlatform;

public class StepDefinitionBase {

    public static void iSetThePageAsFrom(String pageName, String path) {
        PageManager.setPageInstance(pageName, path);
    }

    public static void iTapOnElement(String key) {
        BasePage page = getCurrentPage();
        By locator = page.getLocators().getLocator(key, getOSPlatform());

    }
}
