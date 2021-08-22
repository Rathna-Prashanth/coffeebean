package io.coffeebean.interactions;

import io.coffeebean.testsuite.TestSuite;

public interface DriverAction {
    public DriverAction actions = new DriverExtension();

    public DriverAction click(String locator);

    public DriverAction sendKeys(String locator, String value);

    public DriverAction createStep(String stepName);

    public TestSuite end();
}
