package com.example.ceshi.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test {

    public static void main(String[] args) {

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/ceshi?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
//            Thread.sleep(11000);
            ResultSet resultSet = conn.prepareStatement("select * from products").executeQuery();
            while (resultSet.next()){
                String str = resultSet.getString(2);
                System.out.println(str);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

}
