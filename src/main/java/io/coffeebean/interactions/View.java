package io.coffeebean.interactions;

public interface View {
    /**
     * @param locator Element to move to
     * @return Self reference
     */
    Interactive moveToView(String locator);
}
