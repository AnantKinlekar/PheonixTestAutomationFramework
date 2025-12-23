package com.dataproviders;

import com.api.utils.CSVReaderUtil;
import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import org.testng.annotations.DataProvider;

import java.util.Iterator;

public class DataProviderUtils {

    @DataProvider(name="LoginAPIDataProvider",parallel = true)
    public static Iterator<UserBean> loginAPIDataProvider(){
        return CSVReaderUtil.loadCSV("testData/LoginCreds.csv");
    }
}
