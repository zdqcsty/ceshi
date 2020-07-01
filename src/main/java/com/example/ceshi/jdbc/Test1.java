package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.example.ceshi.mysql.MysqlPool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;
import java.util.Map;

public class Test1 {


    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    //    private static String CONNECTION_URL ="jdbc:hive2://10.130.2.132:10001/zgh";
    private static String CONNECTION_URL ="jdbc:hive2://172.16.43.166:21066/default;auth=KERBEROS;principal=hive/hadoop.hadoop.com@HADOOP.COM";

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.setProperty("java.security.krb5.conf", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\huawei\\krb5.conf");
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("huawei/core-site.xml"));
        configuration.addResource(new Path("huawei/hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
//        UserGroupInformation.loginUserFromKeytab("cpic@HADOOP.COM", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\huawei\\user.keytab");

        UserGroupInformation  UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("cpic@HADOOP.COM", "E:\\study_workSpace\\ceshi\\src\\main\\resources\\huawei\\user.keytab");

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
//        Connection connection = dataSource.getConnection();
//        Connection hiveConnection = DriverManager.getConnection(CONNECTION_URL);
//        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");




//        DruidPooledConnection connection = MysqlPool.druidDataSource.getConnection();
        ResultSet resultSet = connection.prepareStatement("select * from test20200519").executeQuery();
        while (resultSet.next()){
            String str = resultSet.getString(1);
            System.out.println(str);
        }

        connection.close();
    }

}