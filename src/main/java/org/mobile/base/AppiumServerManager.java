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
            try {
                service = new AppiumServiceBuilder()
                        .withAppiumJS(new File(ConfigReader.get("appium_global_node_path")))
                        .usingPort(Integer.parseInt(ConfigReader.get("port")))
                        .build();
                service.start();
                logInfo("\033[32mAppium Server Started!\033[0m");
            } catch (Exception e) {
                throw new RuntimeException("Appium Server Start Failed!\n\n" + e.getMessage());
            }
        }
    }

    public static void stopServer() {
        if (service != null) {
            service.stop();
            logInfo("\033[31mAppium Server Stopped!\033[0m");
        }
    }
}
