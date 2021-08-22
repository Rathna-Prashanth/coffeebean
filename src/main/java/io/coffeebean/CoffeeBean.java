package io.coffeebean;

import io.coffeebean.logging.LoggingHandler;
import io.coffeebean.testsuite.SuiteHandler;
import io.coffeebean.testsuite.TestSuite;

public interface CoffeeBean {

    public static TestSuite initialize() {
        LoggingHandler.initialize();
        return new SuiteHandler().getTestSuite();
    }

}
