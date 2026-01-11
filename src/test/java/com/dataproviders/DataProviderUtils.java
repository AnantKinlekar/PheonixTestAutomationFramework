package com.dataproviders;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.*;
import com.database.dao.CreateJobPayloadDataDao;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUtils {
    private static final Logger LOGGER = LogManager.getLogger(DataProviderUtils.class);

    @DataProvider(name = "LoginAPIDataProvider", parallel = true)
    public static Iterator<UserBean> loginAPIDataProvider() {
        LOGGER.info("Loading data from the csv file testData/LoginCreds.csv");
        return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);
    }

    @DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobDataProvider() {
        LOGGER.info("Loading data from the csv file testData/CreateJobData.csv");

        Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);

        List<CreateJobPayload> payloadList = new ArrayList<>();
        CreateJobBean tempBean;
        CreateJobPayload tempPayload;

        while (createJobBeanIterator.hasNext()) {
            tempBean = createJobBeanIterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }
        return payloadList.iterator();
    }

    @DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobFakeDataProvider() {
        String fakerCount = System.getProperty("fakerCount", "3");
        int fakerCountInt = Integer.parseInt(fakerCount);
        LOGGER.info("Generating fake data for create job api with the faker count: {}", fakerCount);

        Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
        return payloadIterator;
    }

    @DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
    public static Iterator<UserBean> LoginAPIJsonDataProvider() {
        LOGGER.info("Loading data from JSON file testData/loginAPITestData.json");
        return JsonReaderUtil.loadJson("testData/loginAPITestData.json", UserBean[].class);
    }

    @DataProvider(name = "createJobAPIJsonDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider() {
        LOGGER.info("Loading data from JSON file testData/CreateJobAPIData.json");
        return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json", CreateJobPayload[].class);
    }

    @DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
    public static Iterator<UserBean> LoginAPIExcelDataProvider() {
        LOGGER.info("Loading data from EXCEL file testData/PheonixTestData.xlsx and sheet is: LoginTestData");
        return ExcelReaderUtil.loadTestData("testData/PheonixTestData.xlsx", "LoginTestData", UserBean.class);
    }

    @DataProvider(name = "createJobExcelDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobExcelDataProvider() {
        LOGGER.info("Loading data from EXCEL file testData/PheonixTestData.xlsx and sheet is: CreateJobTestData");
        Iterator<CreateJobBean> iterator = ExcelReaderUtil.loadTestData("testData/PheonixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);
        List<CreateJobPayload> payloadList = new ArrayList<>();
        CreateJobBean tempBean;
        CreateJobPayload tempPayload;

        while (iterator.hasNext()) {
            tempBean = iterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }
        return payloadList.iterator();
    }

    @DataProvider(name = "createJobAPIDBDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobAPIDBDataProvider() {
        LOGGER.info("Loading data from DATABASE for createJobPayload");

        List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
        List<CreateJobPayload> createJobPayloadList = new ArrayList<>();
        for (CreateJobBean createJobBean : beanList) {
            CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(createJobBean);
            createJobPayloadList.add(createJobPayload);
        }
        return createJobPayloadList.iterator();
    }
}
