package com.example.ceshi.jdbc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KerberosJdbcTemplateaCeshi {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.193:10000/;principal=hs2/liangtai1.novalocal@LIANGTAI.BONC;auth=kerberos";
//
//    private static String CONNECTION_URL ="jdbc:hive2://10.130.7.193:2188,10.130.7.174:2188,10.130.7.182:2188/default;principal=hs2/liangtai1.novalocal@LIANGTAI.BONC;serviceDiscoveryMode=zookeeper;zooKeeperNamespace=hiveserver2;saslQop=auth;auth=kerberos";
//    private static String CONNECTION_URL ="jdbc:hive2://liangtai1.novalocal:2188,liangtai2.novalocal:2188,liangtai3.novalocal:2188/default;principal=hs2/liangtai1.novalocal@LIANGTAI.BONC;serviceDiscoveryMode=zookeeper;zooKeeperNamespace=hiveserver2;saslQop=auth;auth=kerberos";

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
/*
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }*/

        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
        //这一行也必须要加上
        System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\liangtai\\krb5.conf");
        //这一行在调测krb5的时候可以加上
        //        System.setProperty("sun.security.krb5.debug", "true");
        Configuration configuration = new Configuration();
//        configuration.addResource(new Path("liangtai/core-site.xml"));
//        configuration.addResource(new Path("liangtai/hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("xunlian@LIANGTAI.BONC", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\liangtai\\xunlian.keytab");

        Connection connection = UGI.doAs(new PrivilegedAction<Connection>() {
            @Override
            public Connection run() {
                try {
                    Connection connection = DriverManager.getConnection(CONNECTION_URL);
                    return connection;
                } catch (Exception e) {
                }
                return null;
            }
        });

        ResultSet resultSet = connection.prepareStatement("select * from vbapfe3e312c0fea71426bd5dae5_parquet limit 10").executeQuery();
        while (resultSet.next()){
            String str = resultSet.getString(1);
            System.out.println(str);
        }

        connection.close();
    }

}
