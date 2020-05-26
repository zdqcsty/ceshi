package com.example.ceshi;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class Jdbc_Hive {


    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL ="jdbc:hive2://10.130.2.132:10001/zgh";

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
//        Configuration configuration = new Configuration();
//        configuration.set("hadoop.security.authentication", "Kerberos");
//        UserGroupInformation.setConfiguration(configuration);
//        UserGroupInformation.loginUserFromKeytab("hs2/hadoop10.novalocal@BONCST.LBHY", "/opt/beh/metadata/key/hs10.keytab");

//        Connection connection = dataSource.getConnection();
        Connection connection = DriverManager.getConnection(CONNECTION_URL,"hadoop","");

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet schemas = metaData.getSchemas();

        while (schemas.next()){
            System.out.println(schemas.getString(1));
        }


        connection.close();


    }

}