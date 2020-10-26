package com.example.ceshi.jdbc;

import org.apache.hadoop.fs.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Test {

    private static String tableName = "partitionceshi";

    public static void main(String[] args) throws SQLException {

        final Connection conn = JdbcTemplate.getConnection();
        Map<String, String> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        final ResultSet resultSet = conn.prepareStatement("show partitions " + tableName).executeQuery();
        while (resultSet.next()) {
            final String partition = resultSet.getString(1).trim();
            list.add(resultSet.getString(1).trim());
        }
        for (String partition : list) {
            final String normalPartition = getNormalPartition(partition);
            final PreparedStatement statement = conn.prepareStatement("desc  formatted " + tableName + " partition (" + normalPartition + ")");
            final ResultSet parRs = statement.executeQuery();
            while (parRs.next()) {
                if ("Location".equals(parRs.getString(1).trim())) {
                    final String partitionLocation = parRs.getString(2);
                    resultMap.put(partition, partitionLocation);
                    break;
                }
            }
        }

        System.out.println(resultMap);
    }

    public static String getNormalPartition(String partition) {
        final String[] split = partition.split("/");
        return String.join(",", split);
    }

    ;


}
