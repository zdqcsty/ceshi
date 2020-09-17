package com.example.ceshi.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;

public class KerberosJdbcTemplate {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
//    private static String CONNECTION_URL ="jdbc:hive2://edc-x86-mn1:2181,edc-x86-mn2:2181,edc-x86-mn3:2181/fz_ops_dev;principal=hive/hivecluster@FJBCHKDC;serviceDiscoveryMode=zookeeper;zooKeeperNamespace=hiveserver2;saslQop=auth;auth=KERBEROS?mapreduce.job.queuename=root.bdoc.renter_1.renter_27.dev_81";
    private static String CONNECTION_URL ="jdbc:hive2://10.48.138.245:10000/ods_prod;principal=hive/hivecluster@FJBCHKDC?mapreduce.job.queuename=root.bdoc.renter_1.renter_24.dev_45";
//    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.204:10001/devtest;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos";

    public static void main(String[] args) throws SQLException, IOException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        long conn_start = System.currentTimeMillis();
        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
        //这一行也必须要加上
        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
        //这一行在调测krb5的时候可以加上
        //        System.setProperty("sun.security.krb5.debug", "true");
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("/cmss/bch/bc1.3.6/hadoop/etc/hadoop/core-site.xml"));
        configuration.addResource(new Path("/cmss/bch/bc1.3.6/hadoop/etc/hadoop/hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
//        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("fz_edc_prod/bdoc@FJBCHKDC", "/home/hadoop/fz_edc_prod.keytab");
        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("renter_dp_prod/bdoc@FJBCHKDC", "/home/hadoop/renter_dp_prod.keytab");

        Connection connection = UGI.doAs(new PrivilegedAction<Connection>() {
            @Override
            public Connection run() {
                try {
                    Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");
                    return connection;
                } catch (Exception e) {
                }
                return null;
            }
        });

        long conn_end = System.currentTimeMillis();

        System.out.println("获取hive 连接的时间是---------------------"+Double.valueOf(conn_end-conn_start)/1000);

        long start = System.currentTimeMillis();

        ResultSet databases = connection.prepareStatement("show databases").executeQuery();

//        DatabaseMetaData metaData = connection.getMetaData();

//        ResultSet catalogs = metaData.getSchemas();

        while (databases.next()){
            String str = databases.getString(1);
            System.out.println(str);
        }

        long end = System.currentTimeMillis();

        System.out.println("获取hive schemal的时间是---------------------"+Double.valueOf(end-start)/1000);

        long table_start = System.currentTimeMillis();

//        ResultSet angie_test = metaData.getTables(catalogs.toString(), "fz_ops_dev", "%", new String[]{"TABLE","VIEW"});

        connection.prepareStatement("use ods_prod").execute();
        ResultSet angie_test = connection.prepareStatement("show tables").executeQuery();
//        connection.prepareStatement("show tables")

        while (angie_test.next()){
            String str = angie_test.getString(1);
            System.out.println(str);
        }

        long table_end = System.currentTimeMillis();
        System.out.println("aaaa获取hive table的时间是---------------------"+Double.valueOf(table_end-table_start)/1000);

        long close_start = System.currentTimeMillis();
        connection.close();

        long close_end = System.currentTimeMillis();

        System.out.println("关闭连接的时间是---------------------"+Double.valueOf(close_end-close_start)/1000);

    }

}