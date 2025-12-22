package com.demo.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ReadCSVFile_MapToPOJO {
    public static void main(String[] args) throws IOException, CsvException {

        InputStream inputStream =  Thread.currentThread().getContextClassLoader().getResourceAsStream("testdata/LoginCreds.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(inputStreamReader);

        CsvToBean<UserPOJO> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(UserPOJO.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<UserPOJO> userdetails = csvToBean.parse();
        System.out.println(userdetails);
    }
}
