package com.example.ceshi.jdbc;

import java.sql.*;

public class Test3 {

    private static String CONNECTION_URL = "jdbc:presto://10.130.7.204:16001/dev001/devtest";

    public static void main(String[] args) throws SQLException {


        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

//        ResultSet resultSet = connection.prepareStatement("select * from deoiuytdemo").executeQuery();
        connection.prepareStatement("create table demobbb as select column_0 as aaa from vbapff8854f01d054adb9eca27289ab6d165_parquet").execute();

//        while (resultSet.next()) {
//            String str = resultSet.getString(1);
//            System.out.println(str);
//        }

        connection.close();
    }


}
