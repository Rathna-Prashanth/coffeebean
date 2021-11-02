
![Logo](https://rathnaprashanthcom.files.wordpress.com/2021/08/coffeebean-site.jpg?w=150&h=150)


# CoffeeBean

Easy to use end to end testing solution for web application and websites with BDD approach

## Usage



```        CoffeeBeanOptions.URL = "https://www.google.co.in/";
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
                .end().endTestSuite();
```


## License

[Apache-2.0 License](http://www.apache.org/licenses/LICENSE-2.0)

## Documentation

[CofeeBean Java Documentation](https://rathna-prashanth.github.io/coffeebean/)

[CofeeBean Documentation](https://rathnaprashanth.com/coffeebean/)

## Author

[@Rathna Prashanth](https://github.com/Rathna-Prashanth)


