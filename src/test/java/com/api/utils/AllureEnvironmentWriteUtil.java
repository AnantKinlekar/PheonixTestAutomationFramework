package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriteUtil {
    private static final Logger LOGGER = LogManager.getLogger(AllureEnvironmentWriteUtil.class);

    public static void createEnvironmentPropertiesFile(){
        //create environment.properties file
        String folderPath = "target/allure-results";
        File file = new File(folderPath);
        file.mkdirs();
        Properties prop = new Properties();
        prop.setProperty("name", "Anant");
        prop.setProperty("Project Name", "Pheonix Test Automation Framework");
        prop.setProperty("Env", ConfigManager.env);
        prop.setProperty("BASE_URI", ConfigManager.getProperty("BASE_URI"));
        prop.setProperty("Operating System", System.getProperty("os.name"));
        prop.setProperty("Java Version", System.getProperty("java.version"));
        prop.setProperty("Operating System Version", System.getProperty("os.version"));
        FileWriter fw = null;
        try {
            fw = new FileWriter(folderPath + "/environment.properties");
            prop.store(fw, "my properties file");
            LOGGER.info("Created the environment properties file at {}", folderPath);
        } catch (IOException e) {
            LOGGER.error("Unable to Create the environment properties file at {}", folderPath);
            throw new RuntimeException(e);
        }
    }
}
