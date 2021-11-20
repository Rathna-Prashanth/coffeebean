package io.coffeebean.interactions;

import io.coffeebean.testsuite.ITestSuite;

public interface Interactive extends DropDown, Action, Frame, Window,MouseAction,Assert,View {
    /**
     * Click the element provided
     *
     * @param locator Click the element provided.
     *                Example: ID:loginbtn,
     *                XPATH://td/a
     * @return A self reference
     */
    public Interactive click(String locator);

    /**
     * Enter value on element provided
     *
     * @param locator Enter value on element provided.
     *                Example: ID:loginbtn,
     *                XPATH://td/a
     * @param value   Value to enter on field
     * @return A self reference
     */
    public Interactive sendKeys(String locator, String value);



    /**
     * Stepname for the scenario
     *
     * @param stepName Stepname for the scenario.
     *                 Example: Given:User open the application,
     *                 And:User view login page
     * @return A self reference
     */
    public Interactive createStep(String stepName);

    /**
     * End current scenario and listen for next scenario
     *
     * @return A self reference
     */
    public ITestSuite end();
}
