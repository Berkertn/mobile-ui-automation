package org.mobile.utils;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.mobile.base.DriverManager.getDriver;
import static org.mobile.config.AppiumConfig.APPIUM_IMPLICITLY_WAIT_SECONDS;
import static org.mobile.config.LogConfig.logDebug;
import static org.mobile.config.LogConfig.logError;

public class ElementUtil {

    public WebElement getElement(By elementBy) {
        return getElement(elementBy, APPIUM_IMPLICITLY_WAIT_SECONDS);
    }

    public WebElement getElement(By elementBy, int timeout) {
        WebElement webElement = null;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
            webElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(elementBy)
            );
            logDebug(String.format("Element [%s] has been found in %s seconds", elementBy, timeout));
        } catch (Exception e) {
            logError("Element [%s] could not found in [%s]seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
            Assertions.fail("Element [%s] could not found in [%s] seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
        }
        return webElement;
    }

    public List<WebElement> getElements(By elementBy, int timeout) {
        List<WebElement> webElementList = null;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
            webElementList = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(elementBy)
            );
            webElementList = getDriver().findElements(elementBy);
            logDebug(String.format("Elements [%s] has been found in [%s] seconds size of the [%s]", elementBy, timeout, webElementList.size()));
        } catch (Exception e) {
            logError("Element [%s] could not found in [%s]seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
            Assertions.fail("Element [%s] could not found in [%s] seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
        }
        return webElementList;
    }

    public void clickElement(WebElement element) {
        element.click();
    }
}
