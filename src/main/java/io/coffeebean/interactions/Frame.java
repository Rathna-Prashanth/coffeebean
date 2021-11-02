package io.coffeebean.interactions;

public interface Frame{

    /**
     * @param frame Frame to switch
     * @return A self reference
     */
    Interactive switchToFrame(int frame);

    /**
     * @param frame Frame to switch
     * @return A self reference
     */
    Interactive switchToFrame(String frame);

    /**
     * @return A self reference
     */
    Interactive switchToDefault();
}
