package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static org.mobile.config.LogConfig.logDebug;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.mobile.steps", "org.mobile.base", "org.mobile.utils", "org.mobile.pages"},
        monochrome = true,
        tags = "@wip",
        plugin = {
                "pretty",
                "timeline:build/reports/timeline",
                "json:build/reports/cucumber-files/extent-report.json",
                "html:build/reports/cucumber-reports/cucumber.html",
                "junit:build/reports/cucumber-reports/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)

public class TestRunner {

    public static void main(String[] args) {
        logDebug("Junit Thread: " + Thread.currentThread().getName());
    }

}