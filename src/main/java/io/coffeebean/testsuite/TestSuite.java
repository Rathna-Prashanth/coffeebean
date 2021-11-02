package io.coffeebean.testsuite;

import io.coffeebean.browser.WebBrowser;
import io.coffeebean.interactions.Interactive;

public interface TestSuite {

    /**
     * Name of your test suite
     *
     * @param suiteName Name of your test suite
     * @return A self reference
     */
    public TestSuite createTestSuite(String suiteName);

    /**
     * Feature name
     *
     * @param featureName Feature name
     * @return A self reference
     */
    public TestSuite createFeature(String featureName);

    /**
     * Scenario Name
     *
     * @param scenarioName Scenario Name
     * @return A self reference
     */
    public TestSuite createScenario(WebBrowser browser, String scenarioName);

    /**
     * Gherkin Step Name
     *
     * @param stepName Step Name example: Given:User open the application
     * @return A self reference
     */
    public Interactive createStep(String stepName);

    /**
     * End of scenario and listen to next scenario
     *
     * @return A self reference
     */
    public TestSuite end();

    /**
     * End complete test suite. Test suite report will be generated under test-output folder
     */
    public void endTestSuite();
}
