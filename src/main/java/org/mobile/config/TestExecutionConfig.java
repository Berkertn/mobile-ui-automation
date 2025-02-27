package org.mobile.config;


import org.mobile.base.ThreadLocalManager;

import java.util.concurrent.ForkJoinPool;

import static org.mobile.config.LogConfig.logInfo;

public class TestExecutionConfig {

    static {


        String parallelEnabled = System.getProperty("parallel", "true");
        String parallelMode = System.getProperty("parallelMode", "same_thread");
        String threadCount = System.getProperty("threadCount", "2");


        System.setProperty("cucumber.execution.parallel.enabled", parallelMode);
        System.setProperty("cucumber.execution.parallel.config.strategy", "fixed");
        System.setProperty("cucumber.execution.parallel.config.fixed.parallelism", threadCount);
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", threadCount);


        System.out.println("\n-----------UPDATED----------\n");
        System.out.println("Checking Cucumber parallel execution updated settings...");
        System.out.println("cucumber.execution.parallel.enabled = " + System.getProperty("cucumber.execution.parallel.enabled"));
        System.out.println("cucumber.execution.parallel.config.strategy = " + System.getProperty("cucumber.execution.parallel.config.strategy"));
        System.out.println("cucumber.execution.parallel.config.fixed.parallelism = " + System.getProperty("cucumber.execution.parallel.config.fixed.parallelism"));
        System.out.println("ForkJoinPool common parallelism = " + ForkJoinPool.commonPool().getParallelism());
        System.out.println("\n---------------------\n");


        if (Boolean.parseBoolean(parallelEnabled)) {
            ThreadLocalManager.setIsParallelEnabled(true);
        } else {
              System.setProperty("junit.jupiter.execution.parallel.config.fixed.parallelism", "1");
              System.setProperty("cucumber.execution.parallel.config.fixed.parallelism", "1");

        }
    }

    public static void initialize() {
        logInfo("Parallel Enabled: " + System.getProperty("junit.jupiter.execution.parallel.enabled"));
        logInfo("Parallel Mode Default: " + System.getProperty("junit.jupiter.execution.parallel.mode.default"));
        logInfo("Parallel Mode Classes: " + System.getProperty("junit.jupiter.execution.parallel.mode.classes.default"));
        logInfo("Parallelism Level: " + System.getProperty("junit.jupiter.execution.parallel.config.fixed.parallelism"));
        logInfo("Cucumber-Parallelism: " + System.getProperty("cucumber.execution.parallel.enabled"));
        logInfo("Cucumber-Parallelism Level: " + System.getProperty("cucumber.execution.parallel.config.fixed.parallelism"));
        logInfo("Cucumber-Strategy: " + System.getProperty("cucumber.execution.parallel.strategy"));
    }
}

