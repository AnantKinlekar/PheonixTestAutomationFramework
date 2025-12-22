package com.api.utils;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CSVReaderUtil {
    /*
     * As it is Util class.
     * Constructor will be private
     * static methods.
     * Job: Help me read the CSV file and Map it to a bean
     *
     *
     * */
    private CSVReaderUtil() {

    }


    public static void loadCSV(String pathOfCSVFile) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(inputStreamReader);

        CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(UserBean.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<UserBean> userList = csvToBean.parse();
        System.out.println(userList);
    }
}
