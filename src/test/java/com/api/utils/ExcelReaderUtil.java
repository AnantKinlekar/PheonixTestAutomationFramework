package com.api.utils;

import com.poiji.bind.Poiji;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderUtil {

    private ExcelReaderUtil() {

    }

    public static <T> Iterator<T> loadTestData(String xlsxFile, String sheetName, Class<T> clazz) {
        // Apache POI OOXML library
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(xlsxFile);
        XSSFWorkbook myworkbook = null;
        try {
            myworkbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet mySheet = myworkbook.getSheet(sheetName);
        List<T> dataList = Poiji.fromExcel(mySheet, clazz);
        return dataList.iterator();
    }
}
