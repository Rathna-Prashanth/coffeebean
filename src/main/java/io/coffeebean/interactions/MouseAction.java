package io.coffeebean.interactions;

public interface MouseAction {
    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive checkRadioButton(String locator);

    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive verifyButtonCheck(String locator);

    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive SelectCheckboxes(String... locator);

    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive SelectCheckbox(String locator);
}
