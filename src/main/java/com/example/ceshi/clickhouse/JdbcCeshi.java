package com.example.ceshi.clickhouse;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCeshi implements Serializable {


    private static String driverName = "ru.yandex.clickhouse.ClickHouseDriver";
    private static String CONNECTION_URL = "jdbc:clickhouse://10.130.7.206:8123/";

    public static void main(String[] args) {

        Connection connection=null;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
           /* ResultSet resultSet = connection.prepareStatement("select current_database()").executeQuery();

            while(resultSet.next()){
                String string = resultSet.getString(1);
                System.out.println(string);
            }*/

        } catch (SQLException throwables) {
        }

    }

}
