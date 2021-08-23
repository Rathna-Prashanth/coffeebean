package io.coffeebean.interactions;

import com.aventstack.extentreports.util.Assert;
import io.coffeebean.CoffeeBeanOptions;
import static org.junit.Assert.*;
import io.coffeebean.logging.profiler.EventLogs;
import io.coffeebean.testsuite.SuiteHandler;
import io.coffeebean.testsuite.TestSuite;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.function.Function;

import java.util.concurrent.TimeUnit;

public class DriverExtension extends SuiteHandler implements DriverAction {

    private static WebDriver mDriver;

    public static void setmDriver(WebDriver mDriver) {
        DriverExtension.mDriver = mDriver;
        DriverExtension.mDriver.get(CoffeeBeanOptions.URL);
    }

    public static WebDriver getmDriver() {
        return mDriver;
    }

    private WebElement getWebElement(String locator) {
        switch (locator.split(":")[0]) {
            case "ID":
                return mDriver.findElement(By.id(locator.split(":")[1]));
            case "XPATH":
                return mDriver.findElement(By.xpath(locator.split(":")[1]));
            case "CLASSNAME":
                return mDriver.findElement(By.className(locator.split(":")[1]));
            case "CSS":
                return mDriver.findElement(By.cssSelector(locator.split(":")[1]));
            case "Link":
                return mDriver.findElement(By.linkText(locator.split(":")[1]));
            case "PLink":
                return mDriver.findElement(By.partialLinkText(locator.split(":")[1]));
            default:
                return mDriver.findElement(By.xpath(locator.split(":")[1]));
        }
    }

    private WebElement waitForElement(String locator) {
        Wait wait = new FluentWait(mDriver).withTimeout(60, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(TimeoutException.class);
        WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return (WebElement) getWebElement(locator);
            }
        });
        return element;
    }

    @Override
    public DriverAction click(String locator) {
        WebElement webElement = waitForElement(locator);
        ((JavascriptExecutor) mDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webElement.click();
        return actions;
    }

    @Override
    public DriverAction sendKeys(String locator, String value) {
        waitForElement(locator).sendKeys(value);
        return actions;
    }

    @Override
    public DriverAction assertElementText(String locator, String expected) {
        assertEquals(expected,waitForElement(locator).getText());
        return actions;
    }

    @Override
    public DriverAction createStep(String stepName) {
        EventLogs.log("Step : " + stepName);
        mreport.createStep(stepName.split(":")[0],
                stepName.split(":")[1]);
        return new DriverExtension().actions;
    }

    @Override
    public TestSuite end() {
        DriverExtension.getmDriver().quit();
        return new SuiteHandler().getTestSuite();
    }
}
