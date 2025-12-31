package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunner3 {
    public static void main(String[] args) throws SQLException {
        Connection conn = DataBaseManager.getConnection();
        System.out.println(conn);
    }
}
