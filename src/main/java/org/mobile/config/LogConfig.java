package org.mobile.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogConfig {
    public static ThreadLocal<Logger> logger = ThreadLocal.withInitial(() -> LogManager.getLogger(String.valueOf(Thread.currentThread().getName())));

    public static Logger getLogger() {
        return logger.get();
    }

    public static void logInfo(String message) {
        getLogger().info(message);
    }

    public static void logError(String message) {
        getLogger().error(message);
    }

    public static void logDebug(String message) {
        getLogger().debug(message);
    }

    public static void removeLogger() {
        logger.remove(); // Will clear the ThreadLocal value for this thread
    }
}
