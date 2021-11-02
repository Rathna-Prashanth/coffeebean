package io.coffeebean.interactions;

import org.openqa.selenium.Keys;

import java.util.function.Consumer;

public interface Action{
    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive mouseOver(String locator);

    /**
     * @param locator Element to interact
     * @return A self reference
     */
    Interactive doubleClick(String locator);

    /**
     * @param source Source element
     * @param destination Destination element
     * @return A self reference
     */
    Interactive dragAndDrop(String source,String destination);

    /**
     * @param key Key to be pressed
     * @return A self reference
     */
    Interactive keysDown(Keys key);

    /**
     * @param key Key to be pressed
     * @return A self reference
     */
    Interactive keysUp(Keys key);

    /**
     * @param locator Element to interact
     * @param consumer Consumer
     * @return A self reference
     */
    Interactive customAction(String locator, Consumer<Action> consumer);
}
