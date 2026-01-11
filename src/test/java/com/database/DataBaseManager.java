package com.database;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseManager {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseManager.class);

    private static boolean isVaultUp = true;
    private static final String DB_URL = loadSecret("DB_URL");
    private static final String DB_USERNAME = loadSecret("DB_USERNAME");
    private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");
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
            LOGGER.warn("Database connection is not available....Creating HikariDataSource");

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
                    LOGGER.info("HikariDataSource created");

                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        if (hikariDataSource == null) {
            LOGGER.info("Initializing the DataBase Connection using HikariCP");
            initializePool();
        } else if (hikariDataSource.isClosed()) {
            LOGGER.error("Hikari DataSource is closed");
            throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
        }
        connection = hikariDataSource.getConnection();
        return connection;
    }

    public static String loadSecret(String key) {
        String value = null;

        if (isVaultUp) {
            value = VaultDBConfig.getSecret(key);
            if (value == null) {
                LOGGER.error("Vault is Down !! Or some issue with Vault. Picking up value from .env file");
                isVaultUp = false;
                LOGGER.info("Reading the value for key: {} from the the env file", value);
                value = EnvUtil.getValue(key);
            }
        }
        return value;
    }
}
