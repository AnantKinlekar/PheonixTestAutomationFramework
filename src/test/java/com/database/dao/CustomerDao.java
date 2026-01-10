package com.database.dao;
import com.database.DataBaseManager;
import com.database.model.CustomerDBModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CustomerDao {
    private static final Logger LOGGER = LogManager.getLogger(CustomerDao.class);

    private CustomerDao() {
    }

    private static final String CUSTOMER_DETAIL_QUERY = """ 
            select * from tr_customer where id = ?
            """;

    public static CustomerDBModel getCustomerInfo(int customerId) {
        Connection conn;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        CustomerDBModel customerDBModel = null;
        try {
            LOGGER.info("Executing the SQL Query: {}", CUSTOMER_DETAIL_QUERY);

            conn = DataBaseManager.getConnection();
            preparedStatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                customerDBModel = new CustomerDBModel(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("mobile_number") ,
                        resultSet.getString("mobile_number_alt") ,
                        resultSet.getString("email_id") ,
                        resultSet.getString("email_id_alt"),
                        resultSet.getInt("tr_customer_address_id")
            );
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot convert the result set to the customerDBModel");
            e.printStackTrace();
        }
        return customerDBModel;
    }
}
