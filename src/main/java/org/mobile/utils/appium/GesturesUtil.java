package org.mobile.utils.appium;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static org.mobile.base.DriverManager.getDriver;
import static org.mobile.config.LogConfig.logDebug;
import static org.mobile.config.LogConfig.logError;

public class GesturesUtil {
    private final ElementUtil elementUtil = new ElementUtil();

    //TODO lets add down or up as well
    //TODO now it's only for xpath but we need to update for the others types as well

    /// This MEthod can only use if the locator is created with ui-automator for android
    public void scrollToElement(By elementBy) {
        AppiumDriver driver = getDriver();
        //TODO we can set this value from properties file
        int maxScrolls = 4;
        int scrollCount = 0;

        while (scrollCount < maxScrolls) {

            Optional<WebElement> elementOpt = Optional.ofNullable(elementUtil.findElement(elementBy));
            if (elementOpt.isPresent()) {
                logDebug("Element [%s] found on the screen.".formatted(elementBy));
                return;
            } else {
                logDebug("Element [%s] not found on the screen. Scrolling again, attempt: [%s]".formatted(elementBy, scrollCount));
            }

            if (driver instanceof AndroidDriver) {
                driver.executeScript("mobile: scroll", ImmutableMap.of(
                        "strategy", "-android uiautomator",
                        "selector", elementBy.toString().replace("AppiumBy.androidUIAutomator: ", "").trim(),
                        "direction", "down"
                ));
                logDebug("Scrolled down to find the element [%s]".formatted(elementBy));
            } else if (driver instanceof IOSDriver) {
                //TODO we need to update this for ios we can do the swipe and check the element after every gesture
                logDebug("Scrolled down to find the element [%s]".formatted(elementBy));
            }
            scrollCount++;

            elementOpt = Optional.ofNullable(elementUtil.findElement(elementBy));
            if (elementOpt.isPresent()) {
                logDebug("Element [%s] found on the screen after [%s] scroll attempts.".formatted(elementBy, scrollCount + 1));
                return;
            }

            if (isScrollEndReached()) {
                logError("Element [%s] could not be found or accessed by scrolling.".formatted(elementBy));
                Assertions.fail("Element [%s] could not be found or accessed by scrolling.".formatted(elementBy));
            }
        }
    }

    public boolean isScrollEndReached() {
        AppiumDriver driver = getDriver();
        String lastPageSource = driver.getPageSource();

        try {
            Thread.sleep(1000);
            boolean isEnd = lastPageSource.equals(driver.getPageSource());
            if (isEnd) {
                logDebug("No further scrolling possible. End of the page reached.");
            }
            return isEnd;
        } catch (Exception e) {
            logError("An error occurred while checking scroll end: %s".formatted(e.getMessage()));
            return true;
        }
    }
}
