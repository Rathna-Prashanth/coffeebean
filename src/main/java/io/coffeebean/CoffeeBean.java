package io.coffeebean;

import io.coffeebean.logging.LoggingHandler;
import io.coffeebean.testsuite.SuiteHandler;
import io.coffeebean.testsuite.ITestSuite;

public interface CoffeeBean {

    ITestSuite testSuite = new SuiteHandler();

    /**
     * Initialize coffeebean engine.
     * Use extended APIs to create testSuite, feature, scenario and step
     * Look for Detail level documentation on rathnaprashanth.com/coffeebean
     *
     * @return TestSuite
     */
    public static ITestSuite initialize() {
        LoggingHandler.initialize();
        return testSuite;
    }

    /**
     * @param url URL of webpage to open
     * @return TestSuite
     */
    public static ITestSuite setURL(String url) {
        CoffeeBeanOptions.URL = url;
        return testSuite;
    }
}
