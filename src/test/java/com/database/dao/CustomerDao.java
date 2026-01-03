package com.database.dao;
import com.database.DataBaseManager;
import com.database.model.CustomerDBModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDao {
    //executing the query f or tr_customer which will give details of customer

    private static final String CUSTOMER_DETAIL_QUERY = """ 
            select * from tr_customer where id = 145576
            """;

    public static CustomerDBModel getCustomerInfo() {
        Connection conn;
        Statement statement;
        ResultSet resultSet;
        CustomerDBModel customerDBModel = null;
        try {
            conn = DataBaseManager.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(CUSTOMER_DETAIL_QUERY);

            while(resultSet.next()) {
                customerDBModel = new CustomerDBModel(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("mobile_number") ,
                        resultSet.getString("mobile_number_alt") ,
                        resultSet.getString("email_id") ,
                        resultSet.getString("email_id_alt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerDBModel;
    }
}
