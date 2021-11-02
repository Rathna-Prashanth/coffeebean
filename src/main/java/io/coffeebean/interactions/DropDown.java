package io.coffeebean.interactions;

public interface DropDown {
    Interactive selectDropDownByVisible(String locator, String dropDownValue);

    Interactive selectDropDownByIndex(String locator, int dropDownValue);

    Interactive selectDropDownByValue(String locator, String dropDownValue);
}
