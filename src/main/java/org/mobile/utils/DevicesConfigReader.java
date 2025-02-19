package org.mobile.utils;

import lombok.Getter;
import org.mobile.config.DeviceConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import java.nio.file.*;

import static org.mobile.base.DriverManager.parsePlatform;
import static org.mobile.base.ThreadLocalManager.getOSPlatform;

public class DevicesConfigReader {
    @Getter
    private static final List<DeviceConfig> deviceConfigs = new ArrayList<>();

    static {
        try {
            List<Path> configFiles = getDeviceConfigFiles();
            for (Path configFile : configFiles) {
                Properties properties = new Properties();
                try (InputStream input = Files.newInputStream(configFile)) {
                    properties.load(input);

                    DeviceConfig deviceConfig = new DeviceConfig(
                            properties.getProperty("deviceName"),
                            properties.getProperty("deviceUDID"),
                            parsePlatform(properties.getProperty("platform")),
                            Integer.parseInt(properties.getProperty("port")),
                            Boolean.parseBoolean(properties.getProperty("isRealDevice")),
                            Boolean.parseBoolean(properties.getProperty("isOnUse"))
                    );
                    //device config list is filtered based on the platform and isOnUse flag
                    if (deviceConfig.getPlatform() == getOSPlatform() && deviceConfig.getIsOnUse()) {
                        deviceConfigs.add(deviceConfig);
                    }
                }
            }
            if (deviceConfigs.isEmpty()) {
                throw new RuntimeException("No available devices found in configuration!");
            }
            // Sorts list based on port numbers (smallest to largest).
            // This ensures that when selecting a device (e.g., findFirst()), the one with the lowest port number is chosen first.
            deviceConfigs.sort(Comparator.comparingInt(DeviceConfig::getPort));
        } catch (IOException e) {
            throw new RuntimeException("Error loading device config properties!", e);
        }
    }

    private static List<Path> getDeviceConfigFiles() throws IOException {
        List<Path> configFiles = new ArrayList<>();
        URL resource = DevicesConfigReader.class.getClassLoader().getResource("devices");
        if (resource != null) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(resource.toURI()), "*.device.properties")) {
                stream.forEach(configFiles::add);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        if (configFiles.isEmpty()) {
            Path configDir = Paths.get("src/main/resources/devices");
            if (Files.exists(configDir)) {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(configDir, "*.devices.properties")) {
                    stream.forEach(configFiles::add);
                }
            }
        }

        if (configFiles.isEmpty()) {
            throw new RuntimeException("No device configuration files found in resources/devices directory!");
        }
        return configFiles;
    }
}