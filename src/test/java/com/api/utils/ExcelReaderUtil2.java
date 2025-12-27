package com.api.utils;

import com.api.request.model.UserCredentials;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelReaderUtil2 {

    private ExcelReaderUtil2() {

    }

    public static Iterator<UserCredentials> loadTestData() {
        // Apache POI OOXML library
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PheonixTestData.xlsx");
        XSSFWorkbook myworkbook = null;

        try {
            myworkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //focus on sheet
        XSSFSheet mySheet = myworkbook.getSheet("LoginTestData");
        XSSFRow myRow;
        XSSFCell myCell;

        //read the excel file and store in a List<UserCredentials>
        // we want to know the indexes of username and password

        XSSFRow headerRows = mySheet.getRow(0);
        int userNameIndex = -1;
        int passwordIndex = -1;
        for (Cell cell : headerRows) {
            if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
                userNameIndex = cell.getColumnIndex();
            }
            if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
                passwordIndex = cell.getColumnIndex();
            }
        }
        int lastRowIndex = mySheet.getLastRowNum();
        XSSFRow rowData;
        UserCredentials userCredentials = null;
        ArrayList<UserCredentials> userCredentialsList = new ArrayList<>();
        for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
            rowData = mySheet.getRow(rowIndex);
            userCredentials = new UserCredentials(rowData.getCell(userNameIndex).toString(), rowData.getCell(passwordIndex).toString());
            userCredentialsList.add(userCredentials);
        }
        return userCredentialsList.iterator();
    }
}
