package io.coffeebean.interactions;

import io.coffeebean.CoffeeBeanOptions;
import io.coffeebean.logging.profiler.EventLogs;
import io.coffeebean.testsuite.ITestSuite;
import io.coffeebean.testsuite.SuiteHandler;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class DriverExtension extends SuiteHandler implements io.coffeebean.interactions.Wait {

    protected WebDriver mDriver;
    protected Boolean isFailure = false;
    protected SuiteHandler mSuite;
    private int globalExplicitWait = 60;
    private int globalPolling = 5;

    public DriverExtension(SuiteHandler mSuite) {
        this.mSuite = mSuite;
        this.mDriver = mSuite.mDriver;
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

    protected void setmDriver(WebDriver mDriver) {
        this.mDriver = mDriver;
        this.mDriver.get(CoffeeBeanOptions.url);
    }

    protected WebDriver getmDriver() {
        return this.mDriver;
    }

    protected WebElement getWebElement(String locator) {
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

    protected WebElement waitForElement(String locator) {
        Wait wait = new FluentWait(mDriver).withTimeout(globalExplicitWait, TimeUnit.SECONDS)
                .pollingEvery(globalPolling, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(TimeoutException.class);
        WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return (WebElement) getWebElement(locator);
            }
        });
        return element;
    }

    public Interactive click(String locator) {
        if (!mSuite.isFailure) {
            try {
                WebElement webElement = waitForElement(locator);
                webElement.click();
                return (Interactive) this;
            } catch (Exception e) {
                EventLogs.errLog("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return (Interactive) this;
            }
        } else {
            EventLogs.errLog("Skipping Click : " + locator.split(":")[1]);
            return (Interactive) this;
        }
    }

    public Interactive sendKeys(String locator, String value) {
        if (!mSuite.isFailure) {
            try {
                waitForElement(locator).sendKeys(value);
                return (Interactive) this;
            } catch (Exception e) {
                EventLogs.errLog("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return (Interactive) this;
            }
        } else {
            EventLogs.errLog("Skipping Sendkeys : " + locator.split(":")[1]);
            return (Interactive) this;
        }
    }

    public Interactive assertElementText(String locator, String expected) {
        if (!mSuite.isFailure) {
            try {
                assertEquals(expected, waitForElement(locator).getText());
                return (Interactive) this;
            } catch (Exception e) {
                EventLogs.errLog("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return (Interactive) this;
            }
        } else {
            EventLogs.errLog("Skipping Assert : " + locator.split(":")[1]);
            return (Interactive) this;
        }
    }

    public Interactive createStep(String stepName) {
        if (!mSuite.isFailure) {
            EventLogs.log("Step : " + stepName);
            mSuite.mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            return new InteractiveExtension(mSuite);
        } else {
            mSuite.mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            mSuite.mReport.reportStepSkip();
            EventLogs.errLog("Skiiping Step : " + stepName.split(":")[1]);
            return new InteractiveExtension(mSuite);
        }
    }

    public ITestSuite end() {
        mSuite.isFailure = false;
        getmDriver().quit();
        return mSuite;
    }

    @Override
    public Interactive setCustomWait(int wait, int pollingTime) {
        this.globalExplicitWait = wait;
        this.globalPolling = pollingTime;
        return new InteractiveExtension(mSuite);
    }

    @Override
    public Interactive setGlobalWait() {
        globalExplicitWait = 60;
        globalPolling = 5;
        return new InteractiveExtension(mSuite);
    }
}