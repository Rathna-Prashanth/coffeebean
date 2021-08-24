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
import io.coffeebean.logging.profiler.EventLogs;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class CoffeeBeanReportHandler implements CoffeeBeanReport {
    private static String ReportPath, WorkingDirectory, ReportDirectory;
    private static boolean isWindowsHost = false;
    // public static ExtentHtmlReporter reporter;
    public static ExtentReports extent;
    private ExtentTest feature;
    private ExtentTest scenario, step;
    private String testDetail;
    private static CoffeeBeanReportHandler reportRuntimeListener;

    public CoffeeBeanReportHandler getReportInstance() {
//        reportEngine();
        if (reportRuntimeListener == null) {
            reportRuntimeListener = new CoffeeBeanReportHandler();
            return reportRuntimeListener;
        }
        return reportRuntimeListener;
    }

    @Override
    public ExtentReports reportEngine(String suiteName) {

        ReportPath = Paths.get("").toAbsolutePath() + "/test-output/Coffeebean-Dashboard.html";
        WorkingDirectory = System.getProperty("user.dir");
        ReportDirectory = Paths.get("").toAbsolutePath() + "/test-output/";
        extent = new ExtentReports();
        // reporter = new ExtentHtmlReporter(ReportPath);
        ExtentSparkReporter spark = new ExtentSparkReporter(ReportDirectory);
        ExtentSparkReporter sparkFail = new ExtentSparkReporter(ReportDirectory + "/Coffeebean-Fail.html").filter()
                .statusFilter().as(new Status[]{Status.FAIL}).apply();
        ExtentSparkReporter sparkDashboard = new ExtentSparkReporter(ReportDirectory + "/Coffeebean-Dashboard.html")
                .viewConfigurer().viewOrder().as(new ViewName[]{ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY,
                        ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG})
                .apply();
        extent = new ExtentReports();
        extent.attachReporter(spark, sparkFail, sparkDashboard);

        extent.setSystemInfo("Coffeebean Version", EventLogs.model.getVersion());
        extent.setSystemInfo("Test Suite", suiteName);
        return extent;
    }

    @Override
    public void changeReporConfig() {
        if (isWindowsHost) {
            try {
                File report = new File(ReportPath);
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
                System.out.println(ReportPath);
                BufferedReader in = new BufferedReader(new FileReader(ReportPath));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
            } catch (IOException e) {
            }

            String content = contentBuilder.toString();
//			System.out.println("HTML: " + content);
            content = content.replace("ExtentReports", "CoffeeBean Reports");
            content = content.replace(
                    "https://cdn.rawgit.com/extent-framework/extent-github-cdn/d74480e/commons/img/logo.png",
                    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAABGCAIAAAD+THXTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAG4mlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDIgNzkuMTYwOTI0LCAyMDE3LzA3LzEzLTAxOjA2OjM5ICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtbG5zOnN0RXZ0PSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VFdmVudCMiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo4Y2I2Y2YyYy0yZDdhLTRhNDgtYmFhZS04MTQxNTU5MDg0NGQiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDpkM2Q0NmM3YS0wM2IyLWQ4NGQtOGNiYy1kMjc5MWFlNzViOGQiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6ZmRkNjBjNzQtZGFmYi1jZTQyLThkMzctNmRiZTQ5MTllMjI1IiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChNYWNpbnRvc2gpIiB4bXA6Q3JlYXRlRGF0ZT0iMjAyMC0wOS0wM1QxNjowMTozOSswNTozMCIgeG1wOk1vZGlmeURhdGU9IjIwMjAtMDktMDNUMTY6Mjk6MTcrMDU6MzAiIHhtcDpNZXRhZGF0YURhdGU9IjIwMjAtMDktMDNUMTY6Mjk6MTcrMDU6MzAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDoyOTgxMjM1My05MTRiLTRiZjEtOTYyMC1iNmVkZjAwYTFjZmEiIHN0UmVmOmRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDo1ZTA3ZWQ1Ni0wZjBjLTQxNDItYWI0ZC05MTg3MzkzOTE4OTciLz4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NzdhZTQ3NTAtNTRmNy1jODRhLTgzNGEtMTYzOTFjZjgzNTI2IiBzdEV2dDp3aGVuPSIyMDIwLTA5LTAzVDE2OjE1OjM0KzA1OjMwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ0MgKFdpbmRvd3MpIiBzdEV2dDpjaGFuZ2VkPSIvIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDpmZGQ2MGM3NC1kYWZiLWNlNDItOGQzNy02ZGJlNDkxOWUyMjUiIHN0RXZ0OndoZW49IjIwMjAtMDktMDNUMTY6Mjk6MTcrMDU6MzAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBt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");
            content = content.replace("base64-img", "Screenshot");
            // System.out.println(content);
            FileOutputStream fs = null;
            try {
                fs = new FileOutputStream(ReportDirectory + "/CoffeeBeanReport.html");
                OutputStreamWriter out = new OutputStreamWriter(fs);
                out.write(content);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reportCooldown() {
        extent.flush();
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
    public void reportFeatureLogFatal(String Info) {
        feature.log(Status.WARNING, Info);
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
    public void reportCreateScenario(String Scenario) {
        scenario = feature.createNode(Scenario.class, Scenario);
        scenario.assignAuthor("CoffeeBean Report");
    }

    @Override
    public void reportScenarioLogJSON(String Json) {
        scenario.info(MarkupHelper.createCodeBlock(Json, CodeLanguage.JSON));
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
    public void reportScenarioPass(String Details, String s) {
        scenario.pass(Details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
    }

    @Override
    public void reportScenarioPass(String Details) {
        scenario.pass(Details);
    }

    @Override
    public void reportScenarioFail(String Details) {
        scenario.fail(Details);
    }

    @Override
    public void reportScenarioFail(String Details, String s) {
        scenario.fail(Details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
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
    public void reportStepLogJSON(String Json) {
        step.info(MarkupHelper.createCodeBlock(Json, CodeLanguage.JSON));
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
    public void reportStepPass(String Details, String s) {
        step.pass(Details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
    }

    @Override
    public void reportStepPass(String Details) {
        step.pass(Details);
    }

    @Override
    public void reportStepFail(String Details) {
        step.fail(Details);
    }

    @Override
    public void reportStepFail(String Details, String s) {
        step.fail(Details, MediaEntityBuilder.createScreenCaptureFromBase64String(s).build());
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
