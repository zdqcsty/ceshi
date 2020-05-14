package com.example.ceshi.mysql;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlPool {

    public static String jdbcUrl="jdbc:mysql://10.130.2.62:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
//    public static String jdbcUrl="jdbc:xcloud:@172.16.44.7:1905/SXLTDW";
//    public static String jdbcUrl="jdbc:hive2://10.130.2.132:10000/zgh";
    public static HikariDataSource hikariDataSource;

    static{
        hiveDataSource(jdbcUrl,"stage","stage123");
    }

    public static void hiveDataSource(String jdbcurl, String username, String password){
        hikariDataSource = new HikariDataSource();
//        hikariDataSource.setDriverClassName("com.bonc.xcloud.jdbc.XCloudDriver");
//        hikariDataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
        hikariDataSource.setJdbcUrl(jdbcurl);
//        hikariDataSource.setUsername("stage");
        hikariDataSource.setUsername("root");
//        hikariDataSource.setUsername("hadoop");
//        hikariDataSource.setPassword("stage123");
        hikariDataSource.setPassword("root");
        hikariDataSource.setMinimumIdle(10);
        //空闲连接存活最大时间，默认600000（10分钟）
        hikariDataSource.setIdleTimeout(600000);
        //连接池最大连接数，默认是10
        hikariDataSource.setMaximumPoolSize(10);
    }

    public static Connection getConnection(){
        Connection connection=null;
        try {
            connection= hikariDataSource.getConnection();
        } catch (SQLException e) {
        }
        return connection;
    }

    public static void main(String[] args) {

        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select count(*) from demo ");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String str = resultSet.getString(1);
                System.out.println(str);
            }

            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
