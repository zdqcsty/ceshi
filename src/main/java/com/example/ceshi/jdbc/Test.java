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
        PreparedStatement statement = connection.prepareStatement("desc formatted thriftComment");

//        ResultSetMetaData metaData = statement.getMetaData();

//        String catalogName = metaData.getColumnName(1);
//
        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            String str = rs.getString(1);
            System.out.println(str);
        }
//
//



    }



}
