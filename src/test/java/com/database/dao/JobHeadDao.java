package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.JobHeadModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobHeadDao {
    private static final Logger LOGGER = LogManager.getLogger(JobHeadDao.class);

    private static final String JOB_HEAD_QUERY =
            """
                    select * from tr_job_head where tr_customer_id = ?
                    """;

    private JobHeadDao() {

    }

    public static JobHeadModel getJobHeadInfo(int customerId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JobHeadModel jobHeadModel = null;
        try {
            connection = DataBaseManager.getConnection();
            preparedStatement = connection.prepareStatement(JOB_HEAD_QUERY);
            preparedStatement.setInt(1, customerId);
            LOGGER.info("Executing the SQL Query: {}", JOB_HEAD_QUERY);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                jobHeadModel = new JobHeadModel(
                        resultSet.getInt("id"),
                        resultSet.getString("job_number"),
                        resultSet.getInt("tr_customer_id"),
                        resultSet.getInt("tr_customer_product_id"),
                        resultSet.getInt("mst_service_location_id"),
                        resultSet.getInt("mst_platform_id"),
                        resultSet.getInt("mst_warrenty_status_id"),
                        resultSet.getInt("mst_oem_id"));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot convert the result set to the JobHeadModel Bean");
            System.err.println(e.getMessage());
        }
        return jobHeadModel;
    }
}
