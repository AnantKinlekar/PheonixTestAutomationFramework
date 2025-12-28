package com.api.utils;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.api.bean.CreateJobBean;
import com.poiji.bind.Poiji;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderUtil3 {

    public static void main(String[] args) {
        Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("testData/PheonixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);
        while(iterator.hasNext()){
            CreateJobBean bean = iterator.next();
            CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(iterator.next());
        }
    }

}
