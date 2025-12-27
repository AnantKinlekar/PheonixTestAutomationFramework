package com.api.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReaderUtil {
    public static void main(String[] args) throws IOException {
        // Apache POI OOXML library
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/PheonixTestData.xlsx");
        XSSFWorkbook myworkbook = new XSSFWorkbook(is);
        //focus on sheet
        XSSFSheet mySheet = myworkbook.getSheet("LoginTestData");
        XSSFRow myRow;
        XSSFCell myCell;

        int lastRowIndex = mySheet.getLastRowNum();
        XSSFRow rowHeader = mySheet.getRow(0);
        int lastIndexOfCol = rowHeader.getLastCellNum() - 1; //because index based

        for(int rowIndex = 0; rowIndex <= lastIndexOfCol; rowIndex++){
            for(int colIndex=0; colIndex<=lastIndexOfCol; colIndex++){
                myRow = mySheet.getRow(rowIndex);
                myCell = myRow.getCell(colIndex);
                System.out.print(myCell + " ");
            }
            System.out.println();
        }
    }
}
