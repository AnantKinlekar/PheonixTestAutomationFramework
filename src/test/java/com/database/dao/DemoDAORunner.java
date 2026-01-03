package com.database.dao;

import com.database.model.CustomerDBModel;

public class DemoDAORunner {
    public static void main(String[] args) {
        CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
        System.out.println(customerDBData);
    }
}
