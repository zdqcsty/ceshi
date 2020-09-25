package com.example.ceshi.jdbc;

import java.sql.*;

public class Test {

    private static final String DRIVER_NAME = "org.apache.hive.jdbc.HiveDriver";
    private static final String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/hebing";

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection conn = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from vbapff3dddbd28479f2c3e0c37f7_parquet limit 10");

        while (resultSet.next()){

            Object object = resultSet.getObject(1);
            System.out.println(object.toString());
        }



    }



}
