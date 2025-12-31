package com.database;

import java.sql.SQLException;

public class DemoRunner {
    public static void main(String[] args) throws SQLException {
        DataBaseManagerOld.createConnection();
        DataBaseManagerOld.createConnection();
        DataBaseManagerOld.createConnection();
        DataBaseManagerOld.createConnection();
    }
}
