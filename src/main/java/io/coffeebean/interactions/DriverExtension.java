package io.coffeebean.interactions;

import io.coffeebean.bdd.report.internals.CoffeeBeanReportHandler;
import io.coffeebean.logging.profiler.EventLogs;
import io.coffeebean.testsuite.SuiteHandler;
import io.coffeebean.testsuite.TestSuite;

public class DriverExtension extends SuiteHandler implements DriverAction {

    @Override
    public DriverAction click(String locator) {
        return actions;
    }

    @Override
    public DriverAction sendKeys(String locator, String value) {
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
        return new SuiteHandler().getTestSuite();
    }
}
