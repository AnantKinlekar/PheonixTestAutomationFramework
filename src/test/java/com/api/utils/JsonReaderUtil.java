package com.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JsonReaderUtil {
    public static <T> Iterator<T> loadJson(String fileName, Class<T[]> className) {
        //read the loginAPITestData.json file

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        T[] classArray = null;
        List<T> classList = null;

        try {
            classArray = objectMapper.readValue(inputStream, className);
            classList = Arrays.asList(classArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

       return classList.iterator();
    }
}
