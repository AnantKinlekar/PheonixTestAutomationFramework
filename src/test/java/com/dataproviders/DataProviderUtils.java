package com.dataproviders;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.*;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUtils {

    @DataProvider(name="LoginAPIDataProvider",parallel = true)
    public static Iterator<UserBean> loginAPIDataProvider(){
        return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);
    }

    @DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobDataProvider(){
       Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);

       List<CreateJobPayload> payloadList = new ArrayList<>();
       CreateJobBean tempBean;
       CreateJobPayload tempPayload;

       while(createJobBeanIterator.hasNext()){
           tempBean = createJobBeanIterator.next();
           tempPayload = CreateJobBeanMapper.mapper(tempBean);
           payloadList.add(tempPayload);
       }
        return payloadList.iterator();
    }

    @DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobFakeDataProvider(){
        String fakerCount = System.getProperty("fakerCount", "3");
        int fakerCountInt = Integer.parseInt(fakerCount);
        Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
        return payloadIterator;
    }

    @DataProvider(name="LoginAPIJsonDataProvider",parallel = true)
    public static Iterator<UserCredentials> LoginAPIJsonDataProvider(){
        return JsonReaderUtil.loadJson("testData/loginAPITestData.json", UserCredentials[].class);
    }

    @DataProvider(name="createJobAPIJsonDataProvider",parallel = true)
    public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider(){
        return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json", CreateJobPayload[].class);
    }

    @DataProvider(name="LoginAPIExcelDataProvider",parallel = true)
    public static Iterator<UserBean> LoginAPIExcelDataProvider(){
        return ExcelReaderUtil.loadTestData("testData/PheonixTestData.xlsx", "LoginTestData", UserBean.class);
    }

    @DataProvider(name="createJobExcelDataProvider",parallel = true)
    public static Iterator<CreateJobPayload> createJobExcelDataProvider(){
        Iterator<CreateJobBean> iterator = ExcelReaderUtil.loadTestData("testData/PheonixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);
        List<CreateJobPayload> payloadList = new ArrayList<>();
        CreateJobBean tempBean;
        CreateJobPayload tempPayload;

        while(iterator.hasNext()){
            tempBean = iterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }
        return payloadList.iterator();
    }
}
