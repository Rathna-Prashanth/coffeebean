package io.coffeebean.interactions;

import org.openqa.selenium.WebElement;

import java.util.function.Consumer;

public interface Assert {

    /**
     * @param locator  Element to get text
     * @param expected Expected value to assert
     * @return A self reference
     */
    public Interactive assertElementText(String locator, String expected);

    /**
     * @param locator Element to be checked
     * @return A self reference
     */
    public Interactive assertElementPresent(String locator);

    /**
     * @param locator Element to be checked
     * @return A self reference
     */
    public Interactive assertButtonCheck(String locator);

    /**
     * @param title Title to assert
     * @return A self reference
     */
    public Interactive assertTitle(String title);

    /**
     * @param locator Element to be checked
     * @param consumer
     * @return A self reference
     */
    public Interactive assertElementCustom(String locator, Consumer<WebElement> consumer);

}
