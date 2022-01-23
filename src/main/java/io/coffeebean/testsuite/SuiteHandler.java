package io.coffeebean.testsuite;

import io.coffeebean.CoffeeBeanOptions;
import io.coffeebean.bdd.report.CoffeeBeanReport;
import io.coffeebean.bdd.report.internals.CoffeeBeanReportHandler;
import io.coffeebean.browser.WebBrowser;
import io.coffeebean.interactions.DriverExtension;
import io.coffeebean.interactions.Interactive;
import io.coffeebean.interactions.InteractiveExtension;
import io.coffeebean.logging.profiler.EventLogs;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SuiteHandler implements ITestSuite {

    public CoffeeBeanReport mReport = new CoffeeBeanReportHandler();
    public WebDriver mDriver;
    public Boolean isFailure = false;

    @Override
    public ITestSuite createTestSuite(String suiteName) {
        EventLogs.log("Suite : " + suiteName);
        mReport.reportEngine(suiteName);
        return this;
    }

    @Override
    public ITestSuite createFeature(String featureName) {
        EventLogs.log("Feature : " + featureName);
        mReport.reportCreateFeature(featureName);
        return this;
    }

    @Override
    public ITestSuite createScenario(WebBrowser browser, String scenarioName) {
        EventLogs.log("Scenario : " + scenarioName);
        mReport.reportCreateScenario(scenarioName, browser);
        switch (browser) {
            case ChromeBrowser:
                EventLogs.log("Chrome Browser : " + scenarioName);
                WebDriverManager.chromedriver().setup();
                mDriver = new ChromeDriver();
                mDriver.manage().window().maximize();
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
        this.mDriver.get(CoffeeBeanOptions.URL);
        return this;
    }

    @Override
    public Interactive createStep(String stepName) {
        if (!isFailure) {
            EventLogs.log("Step : " + stepName);
            mReport.createStep(stepName.split(":")[0], stepName.split(":")[1]);
            return new InteractiveExtension(this);
        } else {
            mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            mReport.reportStepSkip();
            EventLogs.log("Skiiping Step : " + stepName.split(":")[1]);
            return new InteractiveExtension(this);
        }
    }

    @Override
    public ITestSuite createStep(String stepName, Interactive stepAction) {
        if (!isFailure) {
            EventLogs.log("Step : " + stepName);
            mReport.createStep(stepName.split(":")[0], stepName.split(":")[1]);
            return this;
        } else {
            mReport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            mReport.reportStepSkip();
            EventLogs.log("Skiiping Step : " + stepName.split(":")[1]);
            return this;
        }
    }

    @Override
    public Interactive getInteractive() {
        return new InteractiveExtension(this);
    }

    @Override
    public ITestSuite end() {
        this.mDriver.quit();
        new DriverExtension(this).setIsFailure(false);
        return this;
    }

    @Override
    public void endTestSuite() {
        mReport.reportCooldown();
    }
}
