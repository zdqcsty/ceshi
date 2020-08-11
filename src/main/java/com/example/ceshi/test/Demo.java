package com.example.ceshi.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;

public class Demo {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
//    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.204:10000/demo;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos";
    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.208:10000/ceshi";

    public static void main(String[] args) throws SQLException, IOException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
//        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
//        //这一行也必须要加上
////        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
//        System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf");
//        //这一行在调测krb5的时候可以加上
//        //        System.setProperty("sun.security.krb5.debug", "true");
//        Configuration configuration = new Configuration();
//        configuration.addResource(new Path("ceshi/core-site.xml"));
//        configuration.addResource(new Path("ceshi/hdfs-site.xml"));
//        configuration.set("hadoop.security.authentication", "Kerberos");
//        UserGroupInformation.setConfiguration(configuration);
//        //这里keytab也是需要用绝对路径的
////        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "/opt/beh/metadata/key/test001.ketab");
//        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.ketab");
//
//        Connection connection = UGI.doAs(new PrivilegedAction<Connection>() {
//            @Override
//            public Connection run() {
//                try {
//                    Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");
//                    return connection;
//                } catch (Exception e) {
//                }
//                return null;
//            }
//        });

        long start = System.currentTimeMillis();

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet catalogs = metaData.getSchemas();

        while (catalogs.next()){
            String str = catalogs.getString(1);
            System.out.println(str);
        }
        long end = System.currentTimeMillis();

        System.out.println("获取hive schemal的时间是---------------------"+Double.valueOf(end-start)/1000);

        long table_start = System.currentTimeMillis();

//        ResultSet angie_test = metaData.getTables(null, "ceshi", "%", new String[]{"TABLE","VIEW"});

        ResultSet angie_test = connection.prepareStatement("show tables").executeQuery();
//        while (angie_test.next()){
//            String str = angie_test.getString(3);
//            System.out.println(str);
//        }

        long table_end = System.currentTimeMillis();
        System.out.println("获取hive table的时间是---------------------"+Double.valueOf(table_end-table_start)/1000);

        long close_start = System.currentTimeMillis();
//        connection.close();

        long close_end = System.currentTimeMillis();
        System.out.println("关闭连接的时间是---------------------"+Double.valueOf(close_end-close_start)/1000);
    }

    public static void getDO() {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
            long start = System.currentTimeMillis();

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet catalogs = metaData.getSchemas();

            while (catalogs.next()){
                String str = catalogs.getString(1);
                System.out.println(str);
            }
            long end = System.currentTimeMillis();

            System.out.println("获取hive schemal的时间是---------------------"+Double.valueOf(end-start)/1000);

            long table_start = System.currentTimeMillis();

//        ResultSet angie_test = metaData.getTables(null, "ceshi", "%", new String[]{"TABLE","VIEW"});

            ResultSet angie_test = connection.prepareStatement("show tables").executeQuery();
//        while (angie_test.next()){
//            String str = angie_test.getString(3);
//            System.out.println(str);
//        }

            long table_end = System.currentTimeMillis();
            System.out.println("获取hive table的时间是---------------------"+Double.valueOf(table_end-table_start)/1000);

            long close_start = System.currentTimeMillis();
//        connection.close();

            long close_end = System.currentTimeMillis();
            System.out.println("关闭连接的时间是---------------------"+Double.valueOf(close_end-close_start)/1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
//        //这一行也必须要加上
////        System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
//        System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf");
//        //这一行在调测krb5的时候可以加上
//        //        System.setProperty("sun.security.krb5.debug", "true");
//        Configuration configuration = new Configuration();
//        configuration.addResource(new Path("ceshi/core-site.xml"));
//        configuration.addResource(new Path("ceshi/hdfs-site.xml"));
//        configuration.set("hadoop.security.authentication", "Kerberos");
//        UserGroupInformation.setConfiguration(configuration);
//        //这里keytab也是需要用绝对路径的
////        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "/opt/beh/metadata/key/test001.ketab");
//        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.ketab");
//
//        Connection connection = UGI.doAs(new PrivilegedAction<Connection>() {
//            @Override
//            public Connection run() {
//                try {
//                    Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");
//                    return connection;
//                } catch (Exception e) {
//                }
//                return null;
//            }
//        });


    }

}
