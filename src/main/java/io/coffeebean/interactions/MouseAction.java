package io.coffeebean.interactions;

public interface MouseAction {
    Interactive checkRadioButton(String locator);
    Interactive verifyButtonCheck(String locator);
    Interactive SelectCheckboxes(String... locator);
    Interactive SelectCheckbox(String locator);
}
