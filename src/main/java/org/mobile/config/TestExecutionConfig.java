package org.mobile.config;


import org.mobile.base.ThreadLocalManager;

import java.util.concurrent.ForkJoinPool;

import static org.mobile.config.LogConfig.logInfo;

public class TestExecutionConfig {

    static {


        String parallelEnabled = System.getProperty("parallel.choice", "methods");
        String threadCount = System.getProperty("thread.count", "2");

        if (!parallelEnabled.equalsIgnoreCase("none")) {
            ThreadLocalManager.setIsParallelEnabled(true);
        }
    }

    public static void initialize() {
    }
}

