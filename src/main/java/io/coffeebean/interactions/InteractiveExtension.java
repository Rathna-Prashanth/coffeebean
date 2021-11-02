package io.coffeebean.interactions;

import io.coffeebean.logging.profiler.EventLogs;
import io.coffeebean.testsuite.SuiteHandler;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;
import java.util.function.Consumer;

public final class InteractiveExtension extends DriverExtension implements Interactive {

    public InteractiveExtension(SuiteHandler mSuite) {
        super(mSuite);
    }

    @Override
    public Interactive selectDropDownByVisible(String locator, String dropDownValue) {
        if (!mSuite.isFailure) {
            try {
                Select select = new Select(waitForElement(locator));
                select.selectByVisibleText(dropDownValue);
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping selectDropDownByVisible : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public Interactive selectDropDownByIndex(String locator, int dropDownValue) {
        if (!mSuite.isFailure) {
            try {
                Select select = new Select(waitForElement(locator));
                select.selectByIndex(dropDownValue);
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping selectDropDownByIndex : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public Interactive selectDropDownByValue(String locator, String dropDownValue) {
        if (!mSuite.isFailure) {
            try {
                Select select = new Select(waitForElement(locator));
                select.selectByValue(dropDownValue);
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping selectDropDownByValue : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public Interactive mouseOver(String locator) {
        if (!mSuite.isFailure) {
            try {
                Actions act = new Actions(this.mDriver);
                act.moveToElement(waitForElement(locator)).build().perform();
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception : " + e);
                mSuite.isFailure = true;
                mSuite.mReport.reportStepExpection(e);
                return this;
            }
        } else {
            EventLogs.log("Skipping MouseOver : " + locator.split(":")[1]);
            return this;
        }
    }

    @Override
    public Interactive doubleClick(String locator) {
        return this;
    }

    @Override
    public Interactive dragAndDrop(String source, String destination) {
        return this;
    }

    @Override
    public Interactive keysDown(Keys key) {
        return this;
    }

    @Override
    public Interactive keysUp(Keys key) {
        return this;
    }

    @Override
    public Interactive customAction(String locator, Consumer<Action> consumer) {
        return this;
    }

    @Override
    public Interactive switchToFrame(int frame) {
        if (!mSuite.isFailure) {
            try {
                mDriver.switchTo().frame(frame);
                EventLogs.log("Navigated to frame with id " + frame);
                return this;
            } catch (NoSuchFrameException e) {
                EventLogs.log("Unable to locate frame with id " + frame + e.getStackTrace());
                return this;
            } catch (Exception e) {
                EventLogs.log("Unable to navigate to frame with id " + frame + e.getStackTrace());
                return this;
            }
        } else {
            EventLogs.log("Skipping switchToFrame");
            return this;
        }
    }

    @Override
    public Interactive switchToFrame(String frame) {
        if (!mSuite.isFailure) {
            try {
                mDriver.switchTo().frame(frame);
                EventLogs.log("Navigated to frame with id " + frame);
                return this;
            } catch (NoSuchFrameException e) {
                EventLogs.log("Unable to locate frame with id " + frame + e.getStackTrace());
                return this;
            } catch (Exception e) {
                EventLogs.log("Unable to navigate to frame with id " + frame + e.getStackTrace());
                return this;
            }
        } else {
            EventLogs.log("Skipping switchToFrame");
            return this;
        }
    }

    @Override
    public Interactive switchToWindow(String title) {
        if (!mSuite.isFailure) {
            try {
                Set<String> allWindows = mDriver.getWindowHandles();
                for (String str : allWindows) {
                    if (str.equals(title)) {
                        mDriver.switchTo().window(str);
                        break;
                    }
                }
                return this;
            } catch (Exception e) {
                EventLogs.log("Exception While switching the windows");
                return this;
            }
        } else {
            EventLogs.log("Skipping switchToFrame");
            return this;
        }
    }

    @Override
    public Interactive switchToDefault() {
        if (!mSuite.isFailure) {
            try {
                mDriver.switchTo().defaultContent();
                EventLogs.log("Navigated back to webpage from frame");
                return this;
            } catch (Exception e) {
                EventLogs.log("unable to navigate back to main webpage from frame" + e.getStackTrace());
                return this;
            }
        } else {
            EventLogs.log("Skipping switchToFrame");
            return this;
        }
    }

    @Override
    public Interactive checkRadioButton(String locator) {
        return this;
    }

    @Override
    public Interactive verifyButtonCheck(String locator) {
        return this;
    }

    @Override
    public Interactive SelectCheckboxes(String... locator) {
        return this;
    }

    @Override
    public Interactive SelectCheckbox(String locator) {
        return this;
    }

    @Override
    public Interactive assertElementPresent(String locator) {
        return this;
    }

    @Override
    public Interactive assertButtonCheck(String locator) {
        return this;
    }

    @Override
    public Interactive assertTitle(String title) {
        return this;
    }

    @Override
    public Interactive assertElementCustom(String locator, Consumer<WebElement> consumer) {
        return this;
    }
}
