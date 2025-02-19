package org.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.mobile.base.BasePage;
import org.mobile.base.DriverManager.OS_TYPES;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    By appButtonLocatorAnd = AppiumBy.accessibilityId("App");
    By appButtonLocatorIOS = AppiumBy.id("ios_appButton");

    By accessibilityButtonLocatorAnd = AppiumBy.xpath("//android.widget.TextView[@content-desc='Accessibility']");

    By textButtonLocatorAnd = AppiumBy.accessibilityId("Text");

    By notDisplayedLocatorAnd = AppiumBy.accessibilityId("notDisplayedLocatorAnd");


    public HomePage() {
        locators.addLocator("appButton", OS_TYPES.android, appButtonLocatorAnd);
        locators.addLocator("appButton", OS_TYPES.iOS, appButtonLocatorIOS);
        locators.addLocator("accessButton", OS_TYPES.android, accessibilityButtonLocatorAnd);
        locators.addLocator("textButton", OS_TYPES.android, textButtonLocatorAnd);
        locators.addLocator("displayCheck", OS_TYPES.android, notDisplayedLocatorAnd);
    }
}
