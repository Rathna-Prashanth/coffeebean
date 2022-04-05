package io.coffeebean.bdd.report;

import com.aventstack.extentreports.ExtentReports;
import io.coffeebean.browser.WebBrowser;

import java.util.List;

public interface CoffeeBeanReport {

    void reportCreateFeature(String title);

    void reportFeatureLog(String info);

    void reportFeatureLogPass(String info);

    void reportFeatureLogFatal(String info);

    void reportFeatureLogList(List<Object> items);

    void reportFeatureAssignCategory(String category);

    void reportCreateScenario(String detail, WebBrowser browser);

    void reportScenarioLogJSON(String json);

    void reportScenarioLogLabelColor(String info);

    void reportScenarioCategory(String[] category);

    void reportScenarioPass(String details, String s);

    void reportScenarioPass(String details);

    void reportScenarioFail(String details);

    void reportScenarioFail(String details, String s);

    void reportScenarioExpection(Exception e);

    void createStep(String keyword, String stepName);

    void reportStepLogJSON(String json);

    void reportStepLogLabelColor(String info);

    void reportStepCategory(String[] category);

    void reportStepPass(String details, String s);

    void reportStepPass(String details);

    void reportStepFail(String details);

    void reportStepFail(String details, String s);

    void reportStepSkip();

    void reportStepExpection(Exception e);

    ExtentReports reportEngine(String suiteName);

    void reportCooldown();

    void changeReporConfig();
}