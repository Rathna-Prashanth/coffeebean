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
    Interactive selectCheckboxes(String... locator);

    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive selectCheckbox(String locator);
}
