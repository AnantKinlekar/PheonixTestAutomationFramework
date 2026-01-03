package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.CustomerProductDBModel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProductDao {
    private static final String CUSTOMER_PRODUCT_QUERY =
            """
                        select * from tr_customer_product where id  = ?
                    """;


    private CustomerProductDao(){

    }

    public static CustomerProductDBModel getCustomerProductInfo(int customerId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CustomerProductDBModel customerProductDBModel = null;
        try {
            connection = DataBaseManager.getConnection();
            preparedStatement = connection.prepareStatement(CUSTOMER_PRODUCT_QUERY);
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                customerProductDBModel = new CustomerProductDBModel(
                        resultSet.getInt("id"),
                        resultSet.getInt("tr_customer_id"),
                        resultSet.getString("dop"),
                        resultSet.getString("serial_number"),
                        resultSet.getString("imei2"),
                        resultSet.getString("imei1"),
                        resultSet.getString("popurl"),
                        resultSet.getInt("mst_model_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerProductDBModel;
    }
}

