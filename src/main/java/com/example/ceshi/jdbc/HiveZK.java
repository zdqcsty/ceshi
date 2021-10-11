package com.example.ceshi.jdbc;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.*;

public class HiveZK {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    // 注意：这里的principal是固定不变的，其指的hive服务所对应的principal,而不是用户所对应的principal
    private static String url = "jdbc:hive2://hadooptd1.novalocal:2188,hadooptd2.novalocal:2188,hadooptd3.novalocal:2188/devtest;" +
            "principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos;" +
            "serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";

/*    private static String url = "jdbc:hive2://10.130.7.204:10000/devtest;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos" +
            "?mapreduce.job.queuename=dev001";*/

    public static void main(String[] args) {
        try {
            Connection conn = get_conn();
            Statement stmt = conn.createStatement();
            show_tables(stmt);
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("END");
        }
    }

    public static Connection get_conn() throws SQLException, ClassNotFoundException {
        /** 使用Hadoop安全登录 **/
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.addResource(new Path("ceshi/core-site.xml"));
        conf.addResource(new Path("ceshi/hdfs-site.xml"));
        conf.set("hadoop.security.authentication", "Kerberos");
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            // 默认：这里不设置的话，win默认会到 C盘下读取krb5.init
            System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf");
        } // linux 会默认到 /etc/krb5.conf 中读取krb5.conf,本文笔者已将该文件放到/etc/目录下，因而这里便不用再设置了
        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab("test001@DEVTEST.BONC",
                    "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.keytab");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection(url);
        return conn;
    }

    public static boolean show_tables(Statement statement) {
        String sql = "SHOW TABLES";
        System.out.println("Running:" + sql);
        try {
            ResultSet res = statement.executeQuery(sql);
            System.out.println("-----表信息-------------------------begin ");
            int i=0;
            while (res.next()) {
                i++;
                String curTableName = res.getString(1);
                System.out.println(curTableName);
                if(i==50){
                    break;
                }
            }
            System.out.println("-----表信息-------------------------end ");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
