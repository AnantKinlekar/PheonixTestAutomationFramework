package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties prop = new Properties();
    private static String path ;
    private static String env ;

    static {
        env = System.getProperty("env", "qa"); //if nothing passed we will set env=qa
        env = env.toLowerCase().trim();
        System.out.println("Running test on Environment: " + env);
        switch (env) {
            case "dev" -> path = "config/config.dev.properties";

            case "qa" -> path = "config/config.qa.properties";

            case "uat" -> path = "config/config.uat.properties";

            default ->  path = "config/config.properties";

        }

        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (input == null) {
            throw new RuntimeException("Config file not found at path! :" + path);
        }
        try {
            prop.load(input);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
