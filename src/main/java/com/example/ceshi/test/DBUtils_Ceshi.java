/*
package com.example.ceshi.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * ***********************************************************
 * 作者：liliang
 * 日期：2020/1/9 0009
 * ***********************************************************
 *//*

public class DBUtils_Ceshi {

    private static String KRB5_CONF = "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf";

    private static String PRINCIPAL = "test001@DEVTEST.BONC";

    private static String KEYTAB = "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.ketab";

    private static String HIVE_DRIVER = "org.apache.spark.sql.hive.jdbc.HiveDriver";
    // 注意：这里的principal是固定不变的，其指的hive服务所对应的principal,而不是用户所对应的principal

    private static String URL = "jdbc:spark://10.130.7.204:10001/devtest;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos";

    private static String noKerURL="jdbc:hive2://10.130.7.208:10000/ceshi";
    //private static String URL = "!connect jdbc:hive2://hadoop001.potato.hamburg:2188,hadoop002.potato.hamburg:2188,hadoop003.potato.hamburg:2188/default;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2_zk;principal=hs2/_HOST@POTATO.HAMBURG;auth=KERBEROS";

    private static String sql = "";

    private static ResultSet res;


    public static Connection get_conn() {
        System.out.println( KRB5_CONF);
        */
/** 使用Hadoop安全登录 **//*

        Configuration conf = new Configuration();
        conf.set("hadoop.security.authentication", "Kerberos");
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            // 默认：这里不设置的话，win默认会到 C盘下读取krb5.init
            System.setProperty("java.security.krb5.conf",  KRB5_CONF);
        } // linux 会默认到 /etc/krb5.conf 中读取krb5.conf,本文笔者已将该文件放到/etc/目录下，因而这里便不用再设置了
        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab(PRINCIPAL,  KEYTAB);
            UserGroupInformation logUser = UserGroupInformation.getLoginUser();

            if (null == logUser) {
                throw new RuntimeException("登录用户为空!");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Kerberos 认证失败");
        }
        try {
            //注册驱动
            Class.forName(HIVE_DRIVER);
            // 连接数据库
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 连接

    public static Connection getConnection() {
        try {
            //注册驱动
            Class.forName(HIVE_DRIVER);
// 连接数据库
            return DriverManager.getConnection(noKerURL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = get_conn();

        ArrayList arrayList = new ArrayList();
        arrayList.add("model_name");
        arrayList.add("accuracy_score");
        arrayList.add("balanced_accuracy_score");
        arrayList.add("f1_score");
        arrayList.add("hamming_loss");
        arrayList.add("precision_score");
        arrayList.add("recall_score");
        arrayList.add("roc_auc_score");
        arrayList.add("zero_one_loss");
        Map map = new HashMap();
        map.put("timestampFormat", "yyyy/MM/dd HH:mm:ss ZZ");
        map.put("header", "true");
        ((org.apache.spark.sql.hive.jdbc.HiveConnection)connection).constructTable("devtest.vbapff908cd5daef23bbf38f6336_parquet",arrayList,"/user/zgh/aaa","csv","overwrite",map);
        System.out.println("连接成功");
    }

    public static String getPath(String fileName) {
        String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
        return path;
    }

}
*/
