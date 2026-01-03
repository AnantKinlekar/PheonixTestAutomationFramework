package com.database.dao;

import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerProductDBModel;

public class DemoDAORunner {
    public static void main(String[] args) {
        CustomerProductDBModel customerProductDBModel = CustomerProductDao.getCustomerProductInfo(145623);
        System.out.println(customerProductDBModel.getDop());

    }
}
