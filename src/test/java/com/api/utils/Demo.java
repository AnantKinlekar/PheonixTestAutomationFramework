package com.api.utils;

import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

import java.util.Iterator;

public class Demo {
    public static void main(String[] args) {
        Iterator<CreateJobBean> iterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);

        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }
}
