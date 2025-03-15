package org.mobile.utils.appium;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
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
            Assertions.fail("Element [%s] could not found in [%s] seconds,\nError: %s".formatted(elementBy, timeout, e.getMessage()));
        }
        return webElement;
    }

    /// main differences between getElement and this, this one is not fail the tests
    public WebElement findElement(By elementBy) {
        int timeout = 5;
        WebElement webElement = null;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
            webElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(elementBy)
            );
            logDebug(String.format("Element [%s] has been found in %s seconds", elementBy, timeout));
        } catch (Exception ignored) {
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

    //TODO this method can be improved like in some elements getText will not work check the elements and try
    public String getText(WebElement element) {
        return element.getText();
    }

    public String getAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
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

    public void sendEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
        logDebug("Sent ENTER key to element: " + element);
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

    public By getParent(By elementBy) {
        String xpath = elementBy.toString().replace("By.xpath: ", "");
        return AppiumBy.xpath(xpath + "/parent::*");
    }

    public By getChild(By elementBy, int childIndex) {
        String xpath = elementBy.toString().replace("By.xpath: ", "");
        return AppiumBy.xpath(xpath + "/child::" + childIndex);
    }

    public By getFollowingSibling(By elementBy) {
        String xpath = elementBy.toString().replace("By.xpath: ", "");
        return AppiumBy.xpath(xpath + "/following-sibling::*");
    }

    public By getPrecedingSibling(By elementBy) {
        String xpath = elementBy.toString().replace("By.xpath: ", "");
        return AppiumBy.xpath(xpath + "/preceding-sibling::*");
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

    public void assertElementNotExists(By locator) {
        List<WebElement> elements = getElements(locator, 5);
        Assertions.assertTrue(elements.isEmpty(), "Element should NOT exist, but it does: " + locator);
        logDebug("Assertion Passed: Element does NOT exist -> " + locator);
    }
}