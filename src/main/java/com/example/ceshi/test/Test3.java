//package com.example.ceshi.test;
//
//import com.example.ceshi.mysql.HadoopWrite;
//import com.example.ceshi.mysql.MysqlPool;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.security.UserGroupInformation;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.sql.*;
//
//public class Test3 {
//
//    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
//    //    private static String CONNECTION_URL ="jdbc:hive2://10.130.2.7:10001/hsp;principal=hs2/hadoop003.potato.hamburg@POTATO.HAMBURG;auth=KERBEROS";
////    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.54:10001/stress;principal=hs2/hadoop10.novalocal@BONCST.LBHY;auth=kerberos";
////    private static String CONNECTION_URL ="jdbc:hive2://10.130.2.132:10001/zgh";
//    private static String CONNECTION_URL ="jdbc:hive2://shujuyun01-cuidong:2188,shujuyun02-cuidong:2188,shujuyun03-cuidong:2188/zgh;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2_zk";
//
//    public static void main(String[] args) throws IOException {
//
//        try {
//            Class.forName(driverName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        Configuration configuration = new Configuration();
//        configuration.set("hadoop.security.authentication", "Kerberos");
//        UserGroupInformation.setConfiguration(configuration);
//        UserGroupInformation.loginUserFromKeytab("hs2/hadoop10.novalocal@BONCST.LBHY", "/opt/beh/metadata/key/hs10.keytab");
//
////        Connection connection = dataSource.getConnection();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
//
////        Thread.sleep(20000);
////        connection.prepareStatement("set spark.yarn.queue=hushunpeng").execute();
//        String sql = "select * from tongjiaaa limit 10";
//
//
//        String createTableSql = "create external table bbb ( `column_0` STRING, `column_1` STRING, `column_2` STRING, `column_3` STRING, `column_4` BIGINT, `column_5` BIGINT, `column_6` BIGINT, `column_7` BIGINT, `column_8` BIGINT, `column_9` BIGINT, `column_10` BIGINT, `column_11` BIGINT )" +
//                " row format delimited fields terminated by ',' LOCATION  'hdfs://beh001/datascience-vbap-data-mart/dataSet/vbapfdfa851bb7e94a9ca3e19344e54c1713/data' TBLPROPERTIES ( 'skip.header.line.count'='1', 'serialization.null.format' = \"''\")";
//
////        String sql1= "create table  hhh ROW FORMAT DELIMITED FIELDS TERMINATED BY ','  TBLPROPERTIES ( 'serialization.null.format'= '\\'\\'') as select * from ceshi";
//        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
////
////        connection.close();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString(1));
//        }
//
//
//        connection.close();
//    }
//
//}
