package org.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.mobile.base.BasePage;
import org.mobile.base.DriverManager;
import org.openqa.selenium.By;

public class AppPage extends BasePage {

    By actionLocatorAnd = AppiumBy.accessibilityId("Action Bar");
    By activityLocatorAnd = AppiumBy.accessibilityId("Activity");
    By alarmLocatorAnd = AppiumBy.accessibilityId("Alarm");
    By alertLocatorAnd = AppiumBy.accessibilityId("Alert Dialogs");
    By deviceLocatorAnd = AppiumBy.accessibilityId("Device Admin");
    By fragmentLocatorAnd = AppiumBy.accessibilityId("Fragment");
    By launcherLocatorAnd = AppiumBy.accessibilityId("Launcher Shortcuts");
    By loaderLocatorAnd = AppiumBy.accessibilityId("Loader");
    By menuLocatorAnd = AppiumBy.accessibilityId("Menu");
    By notificationLocatorAnd = AppiumBy.accessibilityId("Notification");
    By searchLocatorAnd = AppiumBy.accessibilityId("Search");
    By serviceLocatorAnd = AppiumBy.accessibilityId("Service");
    By textLocatorAnd = AppiumBy.accessibilityId("Text-To-Speech");
    By voiceLocatorAnd = AppiumBy.accessibilityId("Voice Recognition");

    public AppPage() {
        locators.addLocator("actionButton", DriverManager.OS_TYPES.android, actionLocatorAnd);
        locators.addLocator("activityButton", DriverManager.OS_TYPES.android, activityLocatorAnd);
        locators.addLocator("alarmButton", DriverManager.OS_TYPES.android, alarmLocatorAnd);
        locators.addLocator("alertButton", DriverManager.OS_TYPES.android, alertLocatorAnd);
        locators.addLocator("deviceButton", DriverManager.OS_TYPES.android, deviceLocatorAnd);
        locators.addLocator("fragmentButton", DriverManager.OS_TYPES.android, fragmentLocatorAnd);
        locators.addLocator("launcherButton", DriverManager.OS_TYPES.android, launcherLocatorAnd);
        locators.addLocator("loaderButton", DriverManager.OS_TYPES.android, loaderLocatorAnd);
        locators.addLocator("menuButton", DriverManager.OS_TYPES.android, menuLocatorAnd);
        locators.addLocator("notificationButton", DriverManager.OS_TYPES.android, notificationLocatorAnd);
        locators.addLocator("searchButton", DriverManager.OS_TYPES.android, searchLocatorAnd);
        locators.addLocator("serviceButton", DriverManager.OS_TYPES.android, serviceLocatorAnd);
        locators.addLocator("textButton", DriverManager.OS_TYPES.android, textLocatorAnd);
        locators.addLocator("voiceButton", DriverManager.OS_TYPES.android, voiceLocatorAnd);
    }
}