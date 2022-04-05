package io.coffeebean.interactions;

public interface DropDown {
    /**
     * @param locator Element to interact
     * @param dropDownValue Value of dropdown
     * @return A self reference
     */
    Interactive selectDropDownByVisible(String locator, String dropDownValue);

    /**
     * @param locator Element to interact
     * @param dropDownValue Value of dropdown
     * @return A self reference
     */
    Interactive selectDropDownByIndex(String locator, int dropDownValue);

    /**
     * @param locator Element to interact
     * @param dropDownValue Value of dropdown
     * @return A self reference
     */
    Interactive selectDropDownByValue(String locator, String dropDownValue);
}
