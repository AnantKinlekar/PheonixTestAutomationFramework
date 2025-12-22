package com.demo.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.List;

public class ReadCSVFile {
    public static void main(String[] args) throws IOException, CsvException {
        //code to read csv file
        InputStream inputStream =  Thread.currentThread().getContextClassLoader().getResourceAsStream("testdata/LoginCreds.csv");

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        CSVReader csvReader = new CSVReader(inputStreamReader);

        List<String[]> dataList = csvReader.readAll();

        for(String[] dataArray :dataList){
            for(String data:dataArray){
                System.out.print(data + " ");
            }
            System.out.println();
        }
    }
}
