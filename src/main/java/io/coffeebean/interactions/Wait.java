package io.coffeebean.interactions;

public interface Wait {

    /**
     * @param wait Wait unit in seconds
     * @param pollingTime Polling Time in seconds
     * @return Self reference
     */
    Interactive setCustomWait(int wait,int pollingTime);

    /**
     * @return Self reference
     */
    Interactive setGlobalWait();
}
