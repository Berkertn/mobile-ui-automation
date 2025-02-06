package org.mobile.runners;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.uiAutomation.steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, json:test-output/reports/cucumber-report.json, html:test-output/reports/cucumber-report.html")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@wip")
@ConfigurationParameter(key = EXECUTION_DRY_RUN_PROPERTY_NAME, value = "true")

class LocalRunner {
    static {
        configureParallelExecution();
    }

    private static void configureParallelExecution() {
        String parallelEnabled = System.getProperty("parallel", "false"); //false by default
        String parallelMode = System.getProperty("parallelMode", "same_thread"); // single thread by default

        System.setProperty("junit.jupiter.execution.parallel.enabled", parallelEnabled);
        System.setProperty("junit.jupiter.execution.parallel.mode.default", parallelMode);
    }
}
