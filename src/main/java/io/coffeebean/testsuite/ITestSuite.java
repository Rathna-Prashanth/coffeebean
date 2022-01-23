package io.coffeebean.testsuite;

import io.coffeebean.browser.WebBrowser;
import io.coffeebean.interactions.Interactive;

public interface ITestSuite {

    /**
     * Name of your test suite
     *
     * @param suiteName Name of your test suite
     * @return A self reference
     */
    public ITestSuite createTestSuite(String suiteName);

    /**
     * Feature name
     *
     * @param featureName Feature name
     * @return A self reference
     */
    public ITestSuite createFeature(String featureName);

    /**
     * Scenario Name
     *
     * @param scenarioName Scenario Name
     * @return A self reference
     */
    public ITestSuite createScenario(WebBrowser browser, String scenarioName);

    /**
     * Gherkin Step Name
     *
     * @param stepName Step Name example: Given:User open the application
     * @return A self reference
     */
    public Interactive createStep(String stepName);

    /**
     * Gherkin Step Name
     *
     * @param stepName   Step Name example: Given:User open the application
     * @param stepAction Step Action
     * @return A self reference
     */
    public ITestSuite createStep(String stepName, Interactive stepAction);

    /**
     * @return Interactive actions with current driver
     */
    public Interactive getInteractive();

    /**
     * End of scenario and listen to next scenario
     *
     * @return A self reference
     */
    public ITestSuite end();

    /**
     * End complete test suite. Test suite report will be generated under test-output folder
     */
    public void endTestSuite();
}
