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
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.204:10000/devtest;principal=hs2/hadooptd3.novalocal@DEVTEST.BONC?mapreduce.job.queuename=test001;hive.exec.max.created.files=200000";

    private static String url = "jdbc:hive2://hadooptd1.novalocal:2188,hadooptd2.novalocal:2188,hadooptd3.novalocal:2188/devtest;" +
            "principal=hs2/hadooptd3.novalocal@DEVTEST.BONC;auth=kerberos;" +
            "serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";

    //TODO
    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
        System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf");
        //这一行在调测krb5的时候可以加上
        //System.setProperty("sun.security.krb5.debug", "true");
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("ceshi/core-site.xml"));
        configuration.addResource(new Path("ceshi/hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
        UserGroupInformation UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("test001@DEVTEST.BONC", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.keytab");

        Connection connection = UGI.doAs(new PrivilegedAction<Connection>() {
            @Override
            public Connection run() {
                try {
                    Connection connection = DriverManager.getConnection(url);
                    return connection;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });


        String sql = "SHOW TABLES";

        System.out.println(connection);

        System.out.println("Running:" + sql);
        try {
            ResultSet res = connection.prepareStatement(sql).executeQuery();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            String str = resultSet.getString(1);
            System.out.println(str);
        }*/
        connection.close();
    }

}
