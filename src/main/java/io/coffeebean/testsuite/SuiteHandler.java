package io.coffeebean.testsuite;

import io.coffeebean.bdd.report.CoffeeBeanReport;
import io.coffeebean.bdd.report.internals.CoffeeBeanReportHandler;
import io.coffeebean.browser.WebBrowser;
import io.coffeebean.interactions.DriverAction;
import io.coffeebean.interactions.DriverExtension;
import io.coffeebean.logging.profiler.EventLogs;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SuiteHandler implements TestSuite {
    public static CoffeeBeanReport mreport = new CoffeeBeanReportHandler();

    public TestSuite getTestSuite() {
        return testSuite;
    }

    @Override
    public TestSuite createTestSuite(String suiteName) {
        EventLogs.log("Suite : " + suiteName);
        mreport.reportEngine(suiteName);
        return testSuite;
    }

    @Override
    public TestSuite createFeature(String featureName) {
        EventLogs.log("Feature : " + featureName);
        mreport.reportCreateFeature(featureName);
        return testSuite;
    }

    @Override
    public TestSuite createScenario(WebBrowser browser, String scenarioName) {
        EventLogs.log("Scenario : " + scenarioName);
        mreport.reportCreateScenario(scenarioName);
        switch (browser) {
            case ChromeBrowser:
                EventLogs.log("Chrome Browser : " + scenarioName);
                WebDriverManager.chromedriver().setup();
                DriverExtension.setmDriver(new ChromeDriver());
                break;
            case FirefoxBrowser:
                EventLogs.log("Firefox Browser : " + scenarioName);
                WebDriverManager.firefoxdriver().setup();
                DriverExtension.setmDriver(new FirefoxDriver());
                break;
            case EdgeBrowser:
                EventLogs.log("Edge Browser : " + scenarioName);
                WebDriverManager.edgedriver().setup();
                DriverExtension.setmDriver(new EdgeDriver());
        }
        return testSuite;
    }

    @Override
    public DriverAction createStep(String stepName) {
        if (!DriverExtension.getIsFailure()) {
            EventLogs.log("Step : " + stepName);
            mreport.createStep(stepName.split(":")[0], stepName.split(":")[1]);
            return new DriverExtension().actions;
        } else {
            mreport.createStep(stepName.split(":")[0],
                    stepName.split(":")[1]);
            mreport.reportStepSkip();
            EventLogs.log("Skiiping Step : " + stepName.split(":")[1]);
            return new DriverExtension().actions;
        }
    }

    @Override
    public TestSuite end() {
        DriverExtension.getmDriver().quit();
        DriverExtension.setIsFailure(false);
        return testSuite;
    }

    @Override
    public void endTestSuite() {
        mreport.reportCooldown();
    }
}
