package com.example.ceshi.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;


public class Test2 {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:hive2://172.31.2.16:9087/tech_cloud;principal=tech_cloud/sddx-hdp-dn005@BONC.COM";

    public static Connection getConnection() throws IOException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //这里注意  这里的路径不能是相对路径，一定要是绝对路径，不然报Can't get Kerberos realm的错误
        //这一行也必须要加上
        System.setProperty("java.security.krb5.conf", "./krb5.conf");
        //这一行在调测krb5的时候可以加上
        //        System.setProperty("sun.security.krb5.debug", "true");
        Configuration configuration = new Configuration();
        configuration.addResource(new Path("./core-site.xml"));
        configuration.addResource(new Path("./hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
        UserGroupInformation UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("xunlian@LIANGTAI.BONC", "./xunlian.keytab");

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
        return connection;
    }


    public static void main(String[] args) throws Exception {

        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new Options();
        options.addOption("table", true, "table");
        CommandLine commandLine = commandLineParser.parse(options, args);

        if (commandLine.getOptions() == null) {
            throw new Exception("nedd a option  -table");
        }
        String table = commandLine.getOptionValue("table");


        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        long countStart = System.currentTimeMillis();
        ResultSet resultSet = statement.executeQuery("select count(*) from " + table);
        long countEnd = System.currentTimeMillis();

        System.out.println("count 花费的时间为--" + (countEnd - countStart) / 1000);

        long nextStart = System.currentTimeMillis();
        if (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }
        long nextEnd = System.currentTimeMillis();

        System.out.println("next 花费的时间为--" + (nextEnd - nextStart) / 1000);
    }
}

