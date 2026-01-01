package com.database.dao;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

import java.util.ArrayList;
import java.util.List;

public class DaoDemoRunner {
    public static void main(String[] args) {
        List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
        List<CreateJobPayload> createJobPayloadList = new ArrayList<>();
        for(CreateJobBean createJobBean:beanList){
            CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(createJobBean);
            createJobPayloadList.add(createJobPayload);
        }
        System.out.println("______________________________________________________________________");
        for(CreateJobPayload createJobPayload :createJobPayloadList){
            System.out.println(createJobPayload);
        }
    }
}
