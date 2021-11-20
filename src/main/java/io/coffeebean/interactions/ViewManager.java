package io.coffeebean.interactions;

import io.coffeebean.logging.profiler.EventLogs;
import io.coffeebean.testsuite.SuiteHandler;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ViewManager {
    private SuiteHandler mSuite;
    private WebDriver mDriver;

    public ViewManager(SuiteHandler mSuite) {
        this.mSuite = mSuite;
        this.mDriver = mSuite.mDriver;
    }

    public Interactive moveToView(WebElement element) {
        if (!mSuite.isFailure) {
            ((JavascriptExecutor) mDriver).executeScript("arguments[0].scrollIntoView(true);", element);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                EventLogs.log("unable to scroll to element" + e.getStackTrace());
                this.mSuite.isFailure = true;
            }
        } else {
            EventLogs.log("Skipping switchToFrame");
            return new InteractiveExtension(mSuite);
        }
        return new InteractiveExtension(mSuite);
    }
}
