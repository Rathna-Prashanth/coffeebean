package io.coffeebean.bdd.report.internals;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import io.coffeebean.CoffeeBeanOptions;
import io.coffeebean.bdd.report.CoffeeBeanReport;
import io.coffeebean.browser.WebBrowser;
import io.coffeebean.logging.profiler.EventLogs;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class CoffeeBeanReportHandler implements CoffeeBeanReport {
    private static String reportPath, workingDirectory, reportDirectory;
    private static boolean isWindowsHost = false;
//    public static ExtentHtmlReporter reporter;
    public static ExtentReports extent;
    private ExtentTest feature;
    private ExtentTest scenario, step;
    private String testDetail;
    private static CoffeeBeanReportHandler reportRuntimeListener;

    public CoffeeBeanReportHandler getReportInstance() {
        if (reportRuntimeListener == null) {
            reportRuntimeListener = new CoffeeBeanReportHandler();
            return reportRuntimeListener;
        }
        return reportRuntimeListener;
    }

    @Override
    public ExtentReports reportEngine(String suiteName) {
        reportPath = Paths.get("").toAbsolutePath() + "/test-output/Coffeebean/index.html";
        workingDirectory = System.getProperty("user.dir");
        reportDirectory = Paths.get("").toAbsolutePath() + "/test-output/Coffeebean/";
        extent = new ExtentReports();
        // reporter = new ExtentHtmlReporter(ReportPath);
        ExtentSparkReporter spark = new ExtentSparkReporter(reportDirectory + "index.html");
        ExtentSparkReporter sparkFail = new ExtentSparkReporter(reportDirectory + "Coffeebean-Fail.html").filter()
                .statusFilter().as(new Status[]{Status.FAIL}).apply();
        ExtentSparkReporter sparkDashboard = new ExtentSparkReporter(reportDirectory + "Coffeebean-Dashboard.html")
                .viewConfigurer().viewOrder().as(new ViewName[]{ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY,
                        ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG})
                .apply();
        extent = new ExtentReports();
        extent.attachReporter(spark/*, sparkFail, sparkDashboard*/);

        extent.setSystemInfo("Coffeebean Version", EventLogs.model.getVersion());
        extent.setSystemInfo("Test Suite", suiteName);
        return extent;
    }

    @Override
    public void changeReporConfig() {
        if (isWindowsHost) {
            try {
                File report = new File(reportPath);
                Scanner myReader = new Scanner(report);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } else {
            StringBuilder contentBuilder = new StringBuilder();
            try {
                System.out.println(reportPath);
                BufferedReader in = new BufferedReader(new FileReader(reportPath));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            String content = contentBuilder.toString();
//			System.out.println("HTML: " + content);
            content = content.replace("ExtentReports", "CoffeeBean Reports");
            content = content.replace(
                    "https://cdn.jsdelivr.net/gh/extent-framework/extent-github-cdn@b00a2d0486596e73dd7326beacf352c639623a0e/commons/img/logo.png",
                    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAQAAACROWYpAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAAAmJLR0QA/4ePzL8AAAAJcEhZcwAAAEgAAABIAEbJaz4AAAAHdElNRQflCR0QJBtClUaYAAAEEUlEQVQ4y22UXU9UZxSFn/fjzIcwQEWEWgQEi7aMxIBtosXatJqmNv2waXrX+973BzT9Ef0ZTWxLjQmtUUQ0RdFUwEyx6oARGEGHAWYG5rzv7oVTHGbc5+7ss87ae+21t6ImfuQHGlQp6vf4HumXQZ+U/TSi2VQZNa3H9DV9z2R3+yyqGtqEphSUml2vH/BHpZd9NBHDoBC2yDGvbpqR4Ho888zbSuAR7qJ1MREe9MfdkPTRRj0BapsiRkBUAimQkbW2jQrmDuZosFtt7pg744/TRQKDAgTKvOssqrSaM2kzq1JqTlVCE5HNLvehPyuDtGDLfAKAI8cjNaVn9KKKuDdoZd5cKpfdzhyNkUJv+Kn/jCSJ7UIFEIrMq0k9bmcxrjcckCSt6pFyFqCFxyQihd7wK/8Fh4nvUFBYZUr/YcZsNux2p/xxDtCAEeNPKthLhoTd7A7P+W94ixhU8Hqe85c+H4wrE55wn8ggLQQAFFSGQaBJR9vNd4yxjscj5cfjWFG/m29jByJJ870a5Tmu/IWnxLpNsZdcwh3zZ0myq4IVoMC0Ph9ckVj4sf+aJHUVWUXUriOBO+jOyCCJHQWD54m+ZEdVqXTGf14FBSHUCRU2+xNygpYat+XVXX01WA77/FmOVEHBkdMu5g75IbrKMrwMT0bdsvdLr7lTMkBDVVYoqnntm2VA+qiHChsKEDKvZlQhPOTfoQVdA87qKe16/FHaMDXLlVcPdNrHpI+eHeN7ESELalJLv/RSX9OvZ0Wl9IprlkM0v4J3Xc2qv7UM8jq2hrfIfX1XF6VDutlV82vHkr5j/tU+SdMrkovmhk1JTN5mPxapUEOADTWjJvWylg5iFeAX832mbujLNusOyuArRhiS1mM2FRQ1DVViebLc1L/ZmdIed1L6qavp96keN+N2eVVseeFfrnxWTaif7bjEwg/8R+zbIZYAa+qWGbGz9SWNZZPodrLEUzWhzwdXUKVT/kv6iFdZNs+0vqAngtwyb2JVRlqJA0KetBo3FyO3fbQ05M/JuzRVGafIP3rYXIourPpW7mH1lIsTZZOnalpdteNmMexw7/vTcoTGMlTK8AIp/Ysdjj7Mhc0sAVaP+gbJ8VDfMrfNY4ltnfbvyQD7iVft0DpTetgOx1OrW+08BsDqy2ZLdqsnekmCrSHplySd23fzf3jIsprUF/SfsYerW52ky6/Vrjpa6Skddr3SLV20kiBAlbUHwbFOWl03I2YisrAWdvNguxzbvrGyUNzrO/0QndRXXOoXp2aDRTWjx8x1PRvNrfqj3KnoxawRdWHoRSLEiWDQgCckT4ZZdcNc1L8Go8GDSB7ZZHGHX0w9jRTyLOkFtYaljigGz5p6ZCbMiLlorgWzkeyau8xPNdvzH9iJ0oXwl18xAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDIxLTA5LTI5VDE2OjM2OjI3KzAwOjAw4iQQ6AAAACV0RVh0ZGF0ZTptb2RpZnkAMjAyMS0wOS0yOVQxNjozNjoyNyswMDowMJN5qFQAAABUdEVYdHN2ZzpiYXNlLXVyaQBmaWxlOi8vL2hvbWUvZGIvc3ZnX2luZm8vc3ZnL2E1LzRkL2E1NGQ4ZDdiODZlODg5ZWFmNTI0MmUxODA2MmZhYzU4LnN2Zz3wXgcAAAAASUVORK5CYII=");
            content = content.replace("base64-img", "Screenshot");
//            System.out.println(content);
            FileOutputStream fs = null;
            try {
                fs = new FileOutputStream(reportDirectory + "index.html");
                OutputStreamWriter out = new OutputStreamWriter(fs);
                out.write(content);
                out.close();
            } catch (Exception e) {
                EventLogs.log(e.toString());
            }
        }
    }

    public void reportCooldown() {
        extent.flush();
        changeReporConfig();
    }

    @Override
    public void reportCreateFeature(String title) {
        feature = extent.createTest(Feature.class, title);
        feature.assignDevice(CoffeeBeanOptions.deviceName);
    }

    @Override
    public void reportFeatureLog(String info) {
        feature.log(Status.INFO, info);
    }

    @Override
    public void reportFeatureLogPass(String info) {
        feature.log(Status.PASS, info);
    }

    @Override
    public void reportFeatureLogFatal(String info) {
        feature.log(Status.WARNING, info);
    }

    @Override
    public void reportFeatureLogList(List<Object> items) {
        feature.info(MarkupHelper.createOrderedList(items));
    }

    @Override
    public void reportFeatureAssignCategory(String category) {
        feature.assignCategory(category);
    }

    @Override
    public void reportCreateScenario(String scenario, WebBrowser browser) {
        this.scenario = feature.createNode(Scenario.class, scenario);
        this.scenario.assignAuthor("CoffeeBean Report");
        this.scenario.assignDevice(browser.toString());
    }

    @Override
    public void reportScenarioLogJSON(String json) {
        scenario.info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }

    @Override
    public void reportScenarioLogLabelColor(String info) {
        scenario.info(MarkupHelper.createLabel(info, ExtentColor.RED));
    }

    @Override
    public void reportScenarioCategory(String[] category) {
        for (String cat : category) {
            scenario.assignCategory(cat);
        }
    }

    @Override
    public void reportScenarioPass(String details, String s) {
        scenario.pass(details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
    }

    @Override
    public void reportScenarioPass(String details) {
        scenario.pass(details);
    }

    @Override
    public void reportScenarioFail(String details) {
        scenario.fail(details);
    }

    @Override
    public void reportScenarioFail(String details, String s) {
        scenario.fail(details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
    }

    @Override
    public void reportScenarioExpection(Exception e) {
        scenario.fail(e);
    }

    @Override
    public void createStep(String keyword, String stepName) {
        try {
            switch (keyword) {
                case "Given":
                    step = scenario.createNode(new GherkinKeyword("Given"), stepName);
                    break;
                case "And":
                    step = scenario.createNode(new GherkinKeyword("And"), stepName);
                    break;
                case "When":
                    step = scenario.createNode(new GherkinKeyword("When"), stepName);
                    break;
                case "Then":
                    step = scenario.createNode(new GherkinKeyword("Then"), stepName);
                    break;
                default:
                    step = scenario.createNode(new GherkinKeyword("And"), stepName);
            }
        } catch (Exception e) {
            EventLogs.log(e.toString());
        }
    }

    @Override
    public void reportStepLogJSON(String json) {
        step.info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }

    @Override
    public void reportStepLogLabelColor(String info) {
        step.info(MarkupHelper.createLabel(info, ExtentColor.RED));
    }

    @Override
    public void reportStepCategory(String[] category) {
        for (String cat : category) {
            step.assignCategory(cat);
        }
    }

    @Override
    public void reportStepPass(String details, String s) {
        step.pass(details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
    }

    @Override
    public void reportStepPass(String details) {
        step.pass(details);
    }

    @Override
    public void reportStepFail(String details) {
        step.fail(details);
    }

    @Override
    public void reportStepFail(String details, String s) {
        step.fail(details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
    }

    @Override
    public void reportStepSkip() {
        step.skip("Previous step failed");
    }

    @Override
    public void reportStepExpection(Exception e) {
        step.fail(e);
    }
}
