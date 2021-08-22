package io.coffeebean.testsuite;

import io.coffeebean.bdd.report.CoffeeBeanReport;
import io.coffeebean.bdd.report.internals.CoffeeBeanReportHandler;
import io.coffeebean.interactions.DriverAction;
import io.coffeebean.interactions.DriverExtension;
import io.coffeebean.logging.profiler.EventLogs;

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
    public TestSuite createScenario(String scenarioName) {
        EventLogs.log("Scenario : " + scenarioName);
        mreport.reportCreateScenario(scenarioName);
        return testSuite;
    }

    @Override
    public DriverAction createStep(String stepName) {
        EventLogs.log("Step : " + stepName);
        mreport.createStep(stepName.split(":")[0],stepName.split(":")[1]);
        return new DriverExtension().actions;
    }

    @Override
    public TestSuite end() {
        return testSuite;
    }

    @Override
    public void endTestSuite() {
        mreport.reportCooldown();
    }
}
