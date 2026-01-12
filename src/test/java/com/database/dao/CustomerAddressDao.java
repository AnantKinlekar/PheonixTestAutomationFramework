package com.database.dao;
import com.api.request.model.CustomerAddress;
import com.database.DataBaseManager;
import com.database.model.CustomerAddressDBModel;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAddressDao {
    private static final Logger LOGGER = LogManager.getLogger(CustomerAddressDao.class);

    private static final String CUSTOMER_ADDRESS_QUERY = """
            select id,
                    flat_number,
                    apartment_name,
                    street_name,
                    landmark,
                    area,
                    pincode,
                    country,
                    state
                    from tr_customer_address where id = ?
            """;
    private CustomerAddressDao() {

    }
    @Step("Retrieving the Customer Address Data from DB for a specific customer address Id")
    public static CustomerAddressDBModel getCustomerAddress(int customerAddressId){
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        CustomerAddressDBModel customerAddressDBModel = null;
        try {
            LOGGER.info("Getting the connection from DataBaseManager");
            connection = DataBaseManager.getConnection();
            preparedStatement = connection.prepareStatement(CUSTOMER_ADDRESS_QUERY);
            preparedStatement.setInt(1, customerAddressId);
            LOGGER.info("Executing the query {}", CUSTOMER_ADDRESS_QUERY);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                customerAddressDBModel = new CustomerAddressDBModel(
                        resultSet.getInt("id"),
                        resultSet.getString("flat_number"),
                        resultSet.getString("apartment_name"),
                        resultSet.getString("street_name"),
                        resultSet.getString("landmark"),
                        resultSet.getString("area"),
                        resultSet.getString("pincode"),
                        resultSet.getString("country"),
                        resultSet.getString("state")
                );
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot convert the result set to the CustomerAddressDBModel bean");
            e.printStackTrace();
        }
        return customerAddressDBModel;
    }
}
