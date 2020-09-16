package com.example.ceshi.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    private static String driverName = "org.apache.spark.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/ceshi";

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
            ResultSet resultSet = connection.prepareStatement("select current_database()").executeQuery();

            while(resultSet.next()){
                String string = resultSet.getString(1);
                System.out.println(string);
            }

        } catch (SQLException throwables) {
            return null;
        }

        return connection;
    }
}
