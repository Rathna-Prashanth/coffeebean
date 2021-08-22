package io.coffeebean.testsuite;

import io.coffeebean.interactions.DriverAction;

public interface TestSuite {
    public TestSuite testSuite = new SuiteHandler();

    public TestSuite createTestSuite(String suiteName);

    public TestSuite createFeature(String featureName);

    public TestSuite createScenario(String scenarioName);

    public DriverAction createStep(String stepName);

    public TestSuite end();

    public void endTestSuite();
}
