package com.api.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnvUtil {
    public static Dotenv dotenv;
    private static final Logger LOGGER = LogManager.getLogger(EnvUtil.class);

    static {
        LOGGER.info("Loading the .env file...");
        dotenv = Dotenv.load();
    }
    private EnvUtil(){

    }

    public static String getValue(String varName){
        LOGGER.info("Reading the value of {} from .env", varName);
        return dotenv.get(varName);
    }

}
