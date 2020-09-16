package com.example.ceshi.jdbc;

import java.sql.*;

public class Test {

    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static final String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/ceshi";

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

//        PreparedStatement statement = connection.prepareStatement("show partitions ceshi");
        PreparedStatement statement = connection.prepareStatement("desc ceshi");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }


    }



}
