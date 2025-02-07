package org.mobile.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Config file not found in resources with the name of [%s]".formatted("config.properties"));
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config properties!", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Properties file is null \n", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
