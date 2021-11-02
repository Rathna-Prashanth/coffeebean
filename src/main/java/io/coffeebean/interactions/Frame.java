package io.coffeebean.interactions;

public interface Frame{

    Interactive switchToFrame(int frame);
    Interactive switchToFrame(String frame);
    Interactive switchtoDefault();
}
