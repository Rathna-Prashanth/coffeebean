package io.coffeebean.bdd.report;

import com.aventstack.extentreports.ExtentReports;

import java.util.List;

public interface CoffeeBeanReport {

    void reportCreateFeature(String title);

    void reportFeatureLog(String info);

    void reportFeatureLogPass(String info);

    void reportFeatureLogFatal(String Info);

    void reportFeatureLogList(List<Object> items);

    void reportFeatureAssignCategory(String category);

    void reportCreateScenario(String Detail);

    void reportScenarioLogJSON(String Json);

    void reportScenarioLogLabelColor(String info);

    void reportScenarioCategory(String[] category);

    void reportScenarioPass(String Details, String s);

    void reportScenarioPass(String Details);

    void reportScenarioFail(String Details);

    void reportScenarioFail(String Details, String s);

    void reportScenarioExpection(Exception e);

    void createStep(String keyword, String stepName);

    void reportStepLogJSON(String Json);

    void reportStepLogLabelColor(String info);

    void reportStepCategory(String[] category);

    void reportStepPass(String Details, String s);

    void reportStepPass(String Details);

    void reportStepFail(String Details);

    void reportStepFail(String Details, String s);

    void reportStepSkip();

    void reportStepExpection(Exception e);

    ExtentReports reportEngine(String suiteName);

    void reportCooldown();

    void changeReporConfig();
}