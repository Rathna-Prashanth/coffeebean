package io.coffeebean.testsuite;

import io.coffeebean.bdd.report.CoffeeBeanReport;
import io.coffeebean.bdd.report.internals.CoffeeBeanReportHandler;
import io.coffeebean.browser.WebBrowser;
import io.coffeebean.interactions.DriverAction;
import io.coffeebean.interactions.DriverExtension;
import io.coffeebean.logging.profiler.EventLogs;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SuiteHandler implements TestSuite {

    public CoffeeBeanReport mReport = new CoffeeBeanReportHandler();
    public WebDriver mDriver;
    public Boolean isFailure = false;

    @Override
    public TestSuite createTestSuite(String suiteName) {
        EventLogs.log("Suite : " + suiteName);
        mReport.reportEngine(suiteName);
        return this;
    }

    @Override
    public TestSuite createFeature(String featureName) {
        EventLogs.log("Feature : " + featureName);
        mReport.reportCreateFeature(featureName);
        return this;
    }

    @Override
    public TestSuite createScenario(WebBrowser browser, String scenarioName) {
        EventLogs.log("Scenario : " + scenarioName);
        mReport.reportCreateScenario(scenarioName, browser);
        switch (browser) {
            case ChromeBrowser:
                EventLogs.log("Chrome Browser : " + scenarioName);
                WebDriverManager.chromedriver().setup();
                mDriver = new ChromeDriver();
                break;
            case FirefoxBrowser:
                EventLogs.log("Firefox Browser : " + scenarioName);
                WebDriverManager.firefoxdriver().setup();
                mDriver = new FirefoxDriver();
                break;
            case EdgeBrowser:
                EventLogs.log("Edge Browser : " + scenarioName);
                WebDriverManager.edgedriver().setup();
                mDriver = new EdgeDriver();
        }
        return this;
    }

    @Override
    public DriverAction createStep(String stepName) {
        if (!isFailure) {
            EventLogs.log("Step : " + stepName);
            mReport.createStep(stepName.split(":")[0], stepName.split(":")[1]);
            return new DriverExtension(this);
        } else {
            mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            mReport.reportStepSkip();
            EventLogs.log("Skiiping Step : " + stepName.split(":")[1]);
            return new DriverExtension(this);
        }
    }

    @Override
    public TestSuite end() {
        this.mDriver.quit();
        new DriverExtension(this).setIsFailure(false);
        return this;
    }

    @Override
    public void endTestSuite() {
        mReport.reportCooldown();
    }
}
