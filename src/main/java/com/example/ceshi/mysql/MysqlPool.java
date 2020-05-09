package com.example.ceshi.mysql;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlPool {

    public static String jdbcUrl="jdbc:mysql://10.130.2.62:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
    public static HikariDataSource hikariDataSource;

    static{
        hiveDataSource(jdbcUrl,"root","root");
    }

    public static void hiveDataSource(String jdbcurl, String username, String password){
        hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcurl);
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("root");
        hikariDataSource.setMinimumIdle(21);
        //空闲连接存活最大时间，默认600000（10分钟）
        hikariDataSource.setIdleTimeout(600000);
        //连接池最大连接数，默认是10
        hikariDataSource.setMaximumPoolSize(5);
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
            preparedStatement = connection.prepareStatement("select * from ceshi limit 10");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String str = resultSet.getString(1);
                System.out.println(str);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
