import io.coffeebean.CoffeeBean;
import io.coffeebean.CoffeeBeanOptions;
import io.coffeebean.browser.WebBrowser;
import io.coffeebean.interactions.Interactive;
import io.coffeebean.testsuite.ITestSuite;
import org.junit.Ignore;
import org.junit.Test;

public class CallBackFixTest {
    @Test
    @Ignore
    public void initCallBack() {
        CoffeeBeanOptions.url = "https://www.google.co.in/";
        CoffeeBeanOptions.deviceName = "Windows Browser";
        ITestSuite eye = CoffeeBean.initialize();
        Interactive actions = eye.getInteractive();
        eye.createTestSuite("CoffeeBean Unit Test")
                .createFeature("initCallBack")
                .createScenario(WebBrowser.ChromeBrowser, "Check Callback")
                .createStep("When:I search Rathna Prashanth",
                        eye.getInteractive().sendKeys("XPATH://input", "Rathna Prashanth")
                                .click("XPATH:(//input[@value='Google Search'])[2]"))
                .createStep("Then:I should see title as SDET",
                        eye.getInteractive()
                                .assertTitle("SDET")
                ).end().endTestSuite();
    }
}
