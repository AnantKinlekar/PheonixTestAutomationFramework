package com.database.dao;

import com.database.DataBaseManager;
import com.database.model.MapJobProblemModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapJobProblemDao {
    private static final Logger LOGGER = LogManager.getLogger(MapJobProblemDao.class);

    private final static String PROBLEM_QUERY =
            """
                    select * from map_job_problem where tr_job_head_id = ?
                    """;

    private MapJobProblemDao() {

    }

    public static MapJobProblemModel getProblemDetails(int tr_job_head_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        MapJobProblemModel mapJobProblemModel = null;
        try {
            connection = DataBaseManager.getConnection();
            preparedStatement = connection.prepareStatement(PROBLEM_QUERY);
            preparedStatement.setInt(1, tr_job_head_id);
            LOGGER.info("Executing the SQL Query: {}", PROBLEM_QUERY);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
               mapJobProblemModel = new MapJobProblemModel(
                       resultSet.getInt("id"),
                       resultSet.getInt("tr_job_head_id"),
                       resultSet.getInt("mst_problem_id"),
                       resultSet.getString("remark")
                       );
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot convert the result set to the MapJobProblem Model");

            System.err.println(e.getMessage());
        }
        return mapJobProblemModel;
    }
}
