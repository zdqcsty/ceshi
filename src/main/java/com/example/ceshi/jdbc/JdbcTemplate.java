package com.example.ceshi.jdbc;

import java.sql.*;

public class JdbcTemplate {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10001/hebing";

    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");
        } catch (SQLException throwables) {
            return null;
        }

        return connection;
    }

    public static String getCurrentDatabase() throws Exception {
        try (Connection connection = getConnection();
             Statement state = connection.createStatement();
             ResultSet resultSet = state.executeQuery("select current_database()")) {
            while (resultSet.next()) {
                String database = resultSet.getString(1);
                return database;
            }
            throw new Exception("not found database");
        } catch (SQLException e) {
            throw new Exception("aaa");
        }
    }
}
