import io.coffeebean.CoffeeBean;
import io.coffeebean.CoffeeBeanOptions;
import io.coffeebean.browser.WebBrowser;
import org.junit.Ignore;
import org.junit.Test;

public class CallBackTest {

    @Test
    @Ignore
    public void initCallBack() {
        CoffeeBeanOptions.URL = "https://www.google.co.in/";
        CoffeeBeanOptions.deviceName = "Windows Browser";
        CoffeeBean.initialize()
                .createTestSuite("CoffeeBean Unit Test")
                .createFeature("initCallBack")
                .createScenario(WebBrowser.ChromeBrowser, "Check Callback")
                .createStep("Given:User open google")
                .createStep("And:User enter search text")
                .sendKeys("XPATH://input", "Rathna Prashanth SDET")
                .createStep("Given:User click search")
                .click("XPATH:(//input[@value='Google Search'])")
                .createStep("And:User click SDET")
                .click("XPATH://h3[contains(text(),'SDET')]")
                .end()
                .createFeature("initCallBack")
                .createScenario(WebBrowser.FirefoxBrowser, "Check Callback")
                .createStep("Given:User open google")
                .createStep("And:User enter search text")
                .sendKeys("XPATH://input", "Rathna Prashanth")
                .createStep("Given:User click search")
                .click("XPATH:(//input[@value='Google Search'])[2]")
                .end().endTestSuite();
    }
}
