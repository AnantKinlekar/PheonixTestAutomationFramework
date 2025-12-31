package com.database;

import java.sql.SQLException;

public class DemoRunner {
    public static void main(String[] args) throws SQLException {
        DataBaseManager.createConnection();
        DataBaseManager.createConnection();
        DataBaseManager.createConnection();
        DataBaseManager.createConnection();
    }
}
