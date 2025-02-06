package org.mobile.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogConfig {
    public static ThreadLocal<Logger> logger = ThreadLocal.withInitial(() -> LogManager.getLogger(String.valueOf(Thread.currentThread().getName())));

    public static void logInfo(String message) {
        logger.get().info(message);
    }

    public static void logError(String message) {
        logger.get().error(message);
    }

    public static void logDebug(String message) {
        logger.get().debug(message);
    }
}
