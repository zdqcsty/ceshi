package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Test4 {


    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static final String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/ceshi";


    public static void main(String[] args) throws SQLException, IOException, InterruptedException, URISyntaxException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

        DatabaseMetaData metaData = connection.getMetaData();

        //获取数据库
        ResultSet catalogs = metaData.getSchemas();
        while (catalogs.next()){
            System.out.println(catalogs.getString(1));
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
