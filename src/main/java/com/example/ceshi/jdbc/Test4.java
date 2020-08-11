package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.sql.*;

public class Test4 {


    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/ceshi";
    private static DruidDataSource druidDataSource;


    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

        DatabaseMetaData metaData = connection.getMetaData();
        //获取database（通用的方式）
        ResultSet catalogs = metaData.getSchemas();

        while (catalogs.next()) {
            String str = catalogs.getString(1);
            System.out.println(str);
        }

        //获取ceshi database下面的表（通用方式）
        ResultSet angie_test = metaData.getTables(catalogs.toString(), "ceshi", "%", new String[]{"TABLE","VIEW"});

        while (angie_test.next()){
            String str = angie_test.getString(3);
            System.out.println(str);
        }

        connection.close();
    }
}
