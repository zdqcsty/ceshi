//package com.example.ceshi;
//
//import com.sun.deploy.util.SessionState;
//import org.apache.hadoop.hive.conf.HiveConf;
//
//import javax.sql.DataSource;
//import java.sql.*;
//
//import static com.example.ceshi.Jdbc_Hive.dataSource;
//
//public class aaa {
//
//    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
//    private static String CONNECTION_URL = "jdbc:hive2://172.16.41.25:10001/zgh";
////    private static String CONNECTION_URL="jdbc:hive2://10.130.2.7:10001/hsp;principal=hs2/hadoop003.potato.hamburg@POTATO.HAMBURG;auth=KERBEROS";
//
//    public static void main(String[] args) {
//
//        try {
//            PreparedStatement ps = null;
//            long flag = 0;
//            Connection connection = null;
//            try {
//                connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            DatabaseMetaData metaData = connection.getMetaData();
//            ResultSetMetaData rsm = ps.
//            for (int i = 1; i < rsm.getColumnCount(); i++) {
//                rsm.getColumnDisplaySize()
//            }
//            System.out.println("======================================");
//            // 获取执行结果元数据（可以在不知道列名的情况下，盲取该表的所有数据）
//            while (rs.next()) {
//                for (int i = 1; i < rsm.getColumnCount(); i++) {
//                    System.out.println("列名：" + rsm.getColumnLabel(i) + "\t列值：" + rs.getObject(i));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////        PreparedStatement ps = null;
////        long flag = 0;
////        Connection connection = null;
////        try {
////            connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////
////        try {
////            connection.prepareStatement("desc aaaaaaaa").execute();
////        } catch (SQLException e) {
////            System.out.println("hahahha");
////        }
////        try {
////            connection.close();
////        } catch (SQLException e) {
////            e.printStackTrace();
////            connection = null;
////        }
//
//    }
//
//}
