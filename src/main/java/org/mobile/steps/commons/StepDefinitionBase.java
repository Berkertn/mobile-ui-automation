package org.mobile.steps.commons;

import org.mobile.base.BasePage;
import org.mobile.base.PageManager;
import org.mobile.utils.appium.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.mobile.base.ThreadLocalManager.getCurrentPageTL;
import static org.mobile.base.ThreadLocalManager.getOSPlatform;

abstract public class StepDefinitionBase {
    private final ElementUtil elementUtil = new ElementUtil();

    public void iSetThePageAsFrom(String pageName, String path) {
        PageManager.setPageInstance(pageName, path);
    }

    public void iTapOnElement(String key) {
        BasePage page = getCurrentPageTL();
        By locator = page.getLocators().getLocator(key, getOSPlatform());
        WebElement element = elementUtil.getElement(locator);
        elementUtil.tapElement(element);
    }
}