package com.example.ceshi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DoubleConn {


    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
//    private static String HIVE_CONNECTION_URL = "jdbc:hive2://10.130.2.2:10000/zgh;principal=hs2/hadoop001.potato.hamburg@POTATO.HAMBURG;auth=KERBEROS";
    private static String HIVE_CONNECTION_URL = "jdbc:hive2://hadoop001.potato.hamburg:2188,hadoop002.potato.hamburg:2188,hadoop003.potato.hamburg:2188/zgh;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2;principal=hs2/_HOST@POTATO.HAMBURG";
    private static String SPARK_CONNECTION_URL = "jdbc:hive2://10.130.2.7:10001/zgh;principal=hs2/hadoop003.potato.hamburg@POTATO.HAMBURG;auth=KERBEROS";

    public static void main(String[] args) throws SQLException, IOException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }


//      登录Kerberos账号
        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab("hushunpeng@POTATO.HAMBURG", "/opt/beh/metadata/key/hushunpeng.keytab");

        Connection hiveConnection = DriverManager.getConnection(HIVE_CONNECTION_URL);
        String tableName = "hive" + UUID.randomUUID().toString().replace("-", "");
        System.out.println("表名为--------"+tableName);
        String sql="select count(*) from hiveb287ec65285d44778e00483395297fca";
//        String sql = "create table " + tableName + " as select * from demo limit 10";
        hiveConnection.prepareStatement("set mapreduce.job.queuename=hushunpeng").execute();
//        hiveConnection.prepareStatement(sql).execute();
        ResultSet resultSet1 = hiveConnection.prepareStatement(sql).executeQuery();
        while (resultSet1.next()) {
            System.out.println("+++++++++++++++++++++++++++++++++++");
            System.out.println(resultSet1.getInt(1));
        }

        hiveConnection.close();

        Connection sparkConnection = DriverManager.getConnection(SPARK_CONNECTION_URL);
        String sparkTableName = "spark" + UUID.randomUUID().toString().replace("-", "");
        String sparkSql = "select count(*) from hiveb287ec65285d44778e00483395297fca";
        sparkConnection.prepareStatement("set mapreduce.job.queuename=hushunpeng").execute();
        ResultSet resultSet = sparkConnection.prepareStatement(sparkSql).executeQuery();
        while (resultSet.next()) {
            System.out.println("---------------------------------");
            System.out.println(resultSet.getInt(1));
        }
        sparkConnection.close();

    }
}

