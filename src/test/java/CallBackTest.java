import io.coffeebean.CoffeeBean;
import io.coffeebean.testsuite.TestSuite;
import org.junit.Test;

public class CallBackTest {

    @Test
    public void initCallBack() {
       /* CoffeeBean.initialize().createTestSuite("CoffeeBean Unit Test")
                .createFeature("initCallBack")
                .createScenario("Check Callback")
                .createStep("User clicks a button")
                .click("id:loginbtn")
                .createStep("User enter value")
                .sendKeys("", "xpath://td/a")
                .end()
                .createFeature("initCallBack 2")
                .createScenario("Check Callback 2 ")
                .createStep("User clicks a button")
                .click("id:loginbtn")
                .createStep("User clicks a button")
                .sendKeys("", "xpath://td/a")
                .end(); */

        CoffeeBean.initialize().createTestSuite("CoffeeBean Unit Test")
                .createFeature("initCallBack")
                .createScenario("Check Callback")
                .createStep("Given:User clicks a button")
                .click("id:loginbtn")
                .createStep("Given:User enter value")
                .sendKeys("", "xpath://td/a")
                .end()
                .createFeature("initCallBack 2")
                .createScenario("Check Callback 2 ")
                .createStep("Given:User clicks a button")
                .click("id:loginbtn")
                .createStep("Given:User enter value")
                .sendKeys("", "xpath://td/a")
                .end().endTestSuite();

    }
}
