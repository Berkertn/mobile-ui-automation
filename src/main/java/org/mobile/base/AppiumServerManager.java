package org.mobile.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.mobile.utils.ConfigReader;

import java.io.File;

import static org.mobile.config.LogConfig.logInfo;

public class AppiumServerManager {
    private static AppiumDriverLocalService service;

    public static void startServer() {
        if (service == null) {
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")) // Appium'un kurulu olduğu path
                    .usingPort(Integer.parseInt(ConfigReader.get("port")))
                    .build();
            service.start();
            logInfo("\033[32mAppium Server Started!\033[0m");
        }
    }

    public static void stopServer() {
        if (service != null) {
            service.stop();
            logInfo("\033[31mAppium Server Stopped!\033[0m");
        }
    }
}
