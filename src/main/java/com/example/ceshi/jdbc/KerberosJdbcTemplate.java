package com.example.ceshi.jdbc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;


public class KerberosJdbcTemplate {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.204:10001/devtest;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos";


    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
        //这一行也必须要加上
        System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf");
        //这一行在调测krb5的时候可以加上
        //        System.setProperty("sun.security.krb5.debug", "true");
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("ceshi/core-site.xml"));
        configuration.addResource(new Path("ceshi/hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.ketab");

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

        final PreparedStatement statement = connection.prepareStatement("select * from step_24249383963 order by DATE_ID limit 20");
        statement.setQueryTimeout(5);
/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    statement.cancel();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }).start();*/

        statement.executeQuery();

//        ResultSet resultSet = connection.prepareStatement("select * from step_24249383963 limit 10").executeQuery();
//        while (resultSet.next()){
//            String str = resultSet.getString(1);
//            System.out.println(str);
//        }

        connection.close();
    }

}
