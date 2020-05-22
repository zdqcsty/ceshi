package com.example.ceshi.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test {

    public static void main(String[] args) {

        Connection conn = null;
        String url = "jdbc:hive2://10.130.2.132:10001/hive";
        String driver = "org.apache.hive.jdbc.HiveDriver";
        String userName = "hadoop";
//        String password = "root";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, "");
//            conn.prepareStatement()


            ResultSet resultSet = conn.prepareStatement("select * from zgh.ceshi").executeQuery();
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
