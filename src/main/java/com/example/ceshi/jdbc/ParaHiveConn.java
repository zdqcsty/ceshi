package com.example.ceshi.jdbc;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class ParaHiveConn {

    public static void main(String[] args) throws Exception {


        Properties prop = new Properties();
        prop.load(new FileReader(new File("./conn.conf")));

        String basePath = prop.getProperty("basePath");
        String url = prop.getProperty("url");
        String sql = prop.getProperty("sql");
        String priciple = prop.getProperty("priciple");


        String coreSitePath = basePath + File.separator + "core-site.xml";
        String hdfsSitePath = basePath + File.separator + "hdfs-site.xml";
        String krb5Path = basePath + File.separator + "krb5.conf";
        String keyTabPath = basePath + File.separator + "test.keytab";


        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.addResource(new Path(coreSitePath));
        conf.addResource(new Path(hdfsSitePath));
        conf.set("hadoop.security.authentication", "Kerberos");
        System.setProperty("java.security.krb5.conf", krb5Path);

        try {
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation.loginUserFromKeytab(priciple,
                    keyTabPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection conn = DriverManager.getConnection(url);

        final ResultSet resultSet = conn.prepareStatement(sql).executeQuery();

        System.out.println("--------------------start------------");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        System.out.println("--------------------end------------");

        conn.close();

    }


}
