package io.coffeebean.interactions;

import io.coffeebean.testsuite.TestSuite;

public interface DriverAction {
    public DriverAction actions = new DriverExtension();

    /**
     * Click the element provided
     * @param locator Click the element provided.
     * Example: ID:loginbtn,
     * XPATH://td/a
     * @return A self reference
     */
    public DriverAction click(String locator);

    /**
     * Enter value on element provided
     * @param locator Enter value on element provided.
     * Example: ID:loginbtn,
     * XPATH://td/a
     * @param value Value to enter on field
     * @return A self reference
     */
    public DriverAction sendKeys(String locator, String value);

    /**
     * @param locator Element to get text
     * @param expected Expected value to assert
     * @return A self reference
     */
    public DriverAction assertElementText(String locator,String expected);

    /**
     * Stepname for the scenario
     * @param stepName Stepname for the scenario.
     * Example: Given:User open the application,
     * And:User view login page
     * @return A self reference
     */
    public DriverAction createStep(String stepName);

    /**
     * End current scenario and listen for next scenario
     * @return A self reference
     */
    public TestSuite end();
}
