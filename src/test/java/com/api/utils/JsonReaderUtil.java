package com.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JsonReaderUtil {
    private static final Logger LOGGER = LogManager.getLogger(JsonReaderUtil.class);

    @Step("Loading Data from JSON")
    public static <T> Iterator<T> loadJson(String fileName, Class<T[]> className) {

        LOGGER.info("Reading the JSON from the file {}", fileName);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        T[] classArray = null;
        List<T> classList = null;

        try {
            LOGGER.info("Converting JSON data to bean class: {}", className);
            LOGGER.info("Converting JSON data to bean class: {}", className);
            classArray = objectMapper.readValue(inputStream, className);
            classList = Arrays.asList(classArray);
        } catch (IOException e) {
            LOGGER.error("Cannot read the json from the file: {}", fileName, e);
            e.printStackTrace();
        }

        return classList.iterator();
    }
}
