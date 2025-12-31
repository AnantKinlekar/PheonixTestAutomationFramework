package com.database;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariCPDemo {
    public static void main(String[] args) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
        hikariConfig.setUsername(ConfigManager.getProperty("DB_USER_NAME"));
        hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setConnectionTimeout(10000);
        hikariConfig.setIdleTimeout(10000);


        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Connection connection = ds.getConnection();
        System.out.println(connection);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT first_name,last_name,mobile_number from tr_customer;");
        while(resultSet.next()){
            System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name") + "->" + resultSet.getString("mobile_number"));
        }
        ds.close();
    }
}
