package io.coffeebean.interactions;

import io.coffeebean.CoffeeBeanOptions;
import io.coffeebean.logging.profiler.EventLogs;
import io.coffeebean.testsuite.SuiteHandler;
import io.coffeebean.testsuite.TestSuite;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class DriverExtension extends SuiteHandler implements DriverAction {

    private WebDriver mDriver;
    private Boolean isFailure = false;
    private SuiteHandler mSuite;

    public DriverExtension(SuiteHandler mSuite) {
        this.mSuite = mSuite;
        this.mDriver = mSuite.mDriver;
        this.mDriver.get(CoffeeBeanOptions.URL);
    }

    public Boolean getIsFailure() {
        return isFailure;
    }

    public Boolean getIsFailure(SuiteHandler suite) {
        suite.isFailure = this.isFailure;
        return this.isFailure;
    }

    public void setIsFailure(Boolean isFailure) {
        this.isFailure = isFailure;
    }

    public void setmDriver(WebDriver mDriver) {
        this.mDriver = mDriver;
        this.mDriver.get(CoffeeBeanOptions.URL);
    }

    public WebDriver getmDriver() {
        return this.mDriver;
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
        if (!mSuite.isFailure) {
            try {
                WebElement webElement = waitForElement(locator);
                ((JavascriptExecutor) mDriver).executeScript("arguments[0].scrollIntoView(true);", webElement);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                webElement.click();
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping Click : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public DriverAction sendKeys(String locator, String value) {
        if (!mSuite.isFailure) {
            try {
                waitForElement(locator).sendKeys(value);
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping Sendkeys : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public DriverAction assertElementText(String locator, String expected) {
        if (!mSuite.isFailure) {
            try {
                assertEquals(expected, waitForElement(locator).getText());
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping Assert : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public DriverAction createStep(String stepName) {
        if (!mSuite.isFailure) {
            EventLogs.log("Step : " + stepName);
            mSuite.mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            return new DriverExtension(mSuite);
        } else {
            mSuite.mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            mSuite.mReport.reportStepSkip();
            EventLogs.log("Skiiping Step : " + stepName.split(":")[1]);
            return new DriverExtension(mSuite);
        }
    }

    @Override
    public TestSuite end() {
        mSuite.isFailure = false;
        getmDriver().quit();
        return mSuite;
    }
}
