package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.sql.*;

public class Test3 {

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

//        Connection connection = getDataSource().getConnection();

//        Thread.sleep(120000);

        String createTableSqlCsv = "create external table bugceshi (aaa string,id int)  ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde' WITH SERDEPROPERTIES (\"separatorChar\" = \",\",\"quoteChar\" = \"\\\"\",\"escapeChar\" = \"\\\\\") " +
                "  stored as textfile LOCATION  '/user/zgh/bug' TBLPROPERTIES ( 'serialization.null.format' =  \"''\")";

        connection.prepareStatement(createTableSqlCsv).execute();

//        ResultSet resultSet = connection.prepareStatement("select * from test limit 10").executeQuery();
//        while (resultSet.next()) {
//            String str = resultSet.getString(1);
//            System.out.println(str);
//        }
        connection.close();
    }

    public static DruidDataSource getDataSource() {
        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
        druidDataSource.setUrl(CONNECTION_URL);
        druidDataSource.setUsername("hadoop");
        //最小空闲连接数量
        druidDataSource.setMinIdle(5);
        //连接池最大连接数，默认是10
        druidDataSource.setMaxActive(10);

        //监控泄露的配置
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(100);
        druidDataSource.setLogAbandoned(true);

        return druidDataSource;
    }


}
