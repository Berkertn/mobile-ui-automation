package org.mobile.pages;

import org.mobile.base.BasePage;
import org.mobile.base.DriverManager.OS_TYPES;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    By usernameAndroidLocator = By.id("android_username");
    By usernameIOSLocator = By.id("ios_username");

    public HomePage() {
        locators.addLocator("username", OS_TYPES.android, usernameAndroidLocator);
        locators.addLocator("username", OS_TYPES.iOS, usernameIOSLocator);
        locators.addLocator("password", OS_TYPES.android, By.id("android_password"));
        locators.addLocator("password", OS_TYPES.iOS, By.id("ios_password"));
        locators.addLocator("loginButton", OS_TYPES.android, By.id("android_login"));
        locators.addLocator("loginButton", OS_TYPES.iOS, By.id("ios_login"));
    }
}
