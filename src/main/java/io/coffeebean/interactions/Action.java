package io.coffeebean.interactions;

import org.openqa.selenium.Keys;

import java.util.function.Consumer;

public interface Action{
    Interactive mouseOver(String locator);
    Interactive doubleClick(String locator);
    Interactive dragAndDrop(String source,String destination);
    Interactive keysDown(Keys key);
    Interactive keysUp(Keys key);
    Interactive customAction(String locator, Consumer<Action> consumer);
}
