package io.coffeebean.interactions;

public interface Wait {

    Interactive setCustomWait(int wait,int pollingTime);

    Interactive setGlobalWait();
}
