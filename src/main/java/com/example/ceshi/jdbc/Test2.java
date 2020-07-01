package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.example.ceshi.mysql.MysqlPool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.*;

public class Test2 {


    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:presto://10.130.7.208:16001/hive/ceshi";
//    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.204:10001/devtest;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos";

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

        ResultSet resultSet = connection.prepareStatement("select count(*) from ceshi").executeQuery();
        while (resultSet.next()) {
            String str = resultSet.getString(1);
            System.out.println(str);
        }

        connection.close();
    }

}