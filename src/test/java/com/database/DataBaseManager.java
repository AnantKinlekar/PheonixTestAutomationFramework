package com.database;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseManager {
    private static final String DB_URL = EnvUtil.getValue("DB_URL");
    private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
    private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");
    private static final int MAX_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
    private static final int MIN_IDLE = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE"));
    private static final int CONNECTION_TIMEOUT_IN_SEC = Integer.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SEC"));
    private static final int IDLE_TIMEOUT_IN_SEC = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SEC"));
    private static final int MAX_LIFETIME_IN_MIN = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME_IN_MIN"));
    private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");
    private static HikariConfig hikariConfig;
    private volatile static HikariDataSource hikariDataSource;
    private static Connection connection;

    private DataBaseManager() {

    }

    private static void initializePool() {
        if (hikariDataSource == null) {
            synchronized (DataBaseManager.class) {
                if (hikariDataSource == null) {
                    HikariConfig hikariConfig = new HikariConfig();
                    hikariConfig.setJdbcUrl(DB_URL);
                    hikariConfig.setUsername(DB_USERNAME);
                    hikariConfig.setPassword(DB_PASSWORD);
                    hikariConfig.setMaximumPoolSize(MAX_POOL_SIZE);
                    hikariConfig.setMinimumIdle(MIN_IDLE);
                    hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SEC * 1000);
                    hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SEC * 1000);
                    hikariConfig.setMaxLifetime(MAX_LIFETIME_IN_MIN * 60 * 1000);
                    hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);
                    hikariDataSource = new HikariDataSource(hikariConfig);
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        if (hikariDataSource == null) {
            initializePool();
        } else if (hikariDataSource.isClosed()) {
            throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
        }
        connection = hikariDataSource.getConnection();
        return connection;
    }
}
