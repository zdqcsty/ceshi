package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test3 {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/test";


    public static void main(String[] args) throws SQLException {

        createTable();

        Connection connection = getConnection();
        Statement state = null;
        try {
            state = connection.createStatement();
            state.execute("create table cdacdacdacad as select * from testparquet limit 10");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        connection.close();
    }


    public static void createTable() throws SQLException {

        Connection connection = getConnection();
        Statement state = null;
        try {
            state = connection.createStatement();
            state.execute("set mapreduce.job.name=aaa");
            state.execute("create table democdacdacdabc as select * from testparquet limit 10");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
        connection.close();

    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException throwables) {
            return null;
        }
        return connection;
    }


    public static DruidDataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
        druidDataSource.setUrl("jdbc:hive2://10.130.7.208:10000/test");
        druidDataSource.setUsername("hadoop");
        //最小空闲连接数量
        druidDataSource.setMinIdle(1);
        //连接池最大连接数，默认是10
        druidDataSource.setMaxActive(1);
        return druidDataSource;
    }

}
