package org.mobile.utils.appium;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
            Assert.fail("Element [%s] could not found in [%s] seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
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
            Assert.fail("Element [%s] could not found in [%s] seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
        }
        return webElementList;
    }

    public void tapElement(WebElement element) {
        AppiumDriver driver = getDriver();
        new Actions(driver)
                .moveToElement(element)
                .click()
                .perform();
        logDebug(String.format("Element [%s] has tapped", element));
    }

    //TODO sometimes it need to tap to field first
    public void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
        logDebug("Typed text:[%s] into element:[%s]".formatted(text, element));
    }

    public void clearAndSendKeys(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        logDebug("Cleared and typed text:[%s] into element:[%s]".formatted(text, element));
    }

    public void waitForElementToBeVisible(By elementBy, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
        logDebug("Element became visible: " + elementBy);
    }

    public void waitForElementToDisappear(By elementBy, int timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementBy));
        logDebug("Element disappeared: " + elementBy);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        logDebug("Scrolled to element: " + element);
    }

    public boolean isDisplayed(WebElement element) {
        try {
            boolean result = element.isDisplayed();
            logDebug("Element displayed status: " + result);
            return result;
        } catch (NoSuchElementException e) {
            logDebug("Element is not displayed.");
            return false;
        }
    }

    public boolean isEnabled(WebElement element) {
        return element.isEnabled();
    }

    public boolean isSelected(WebElement element) {
        return element.isSelected();
    }

    //TODO this method can be improved like in some elements getText will not work check the elements and try
    public String getText(WebElement element) {
        return element.getText();
    }
}