package com.api.utils;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties prop = new Properties();
    private static String path ;
    public static String env ;
    private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);

    static {
        LOGGER.info("Loading the properties file");
        if(System.getProperty("env") == null){
            LOGGER.warn("ENV Variable is not set......Using QA as the env");
        }
        env = System.getProperty("env", "qa");
        LOGGER.info("Running the test in the env {}", env);
        env = env.toLowerCase().trim();
        System.out.println("Running test on Environment: " + env);
        switch (env) {
            case "dev" -> path = "config/config.dev.properties";

            case "qa" -> path = "config/config.qa.properties";

            case "uat" -> path = "config/config.uat.properties";

            default ->  path = "config/config.properties";

        }
        LOGGER.info("Using the properties file from the path: {}", path);

        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (input == null) {
            LOGGER.error("Can not find the properties file from the path: {}", path);
            throw new RuntimeException("Config file not found at path! :" + path);
        }
        try {
            prop.load(input);
        } catch (NullPointerException e) {
            LOGGER.error("Can not find the file from the path: {}", path, e);
            throw new NullPointerException();
        } catch (IOException e) {
            LOGGER.error("Something went wrong..... Please check the file path: {}", path, e);
            throw new RuntimeException(e);
        }

    }

    @Step("Getting property value from property file")
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
