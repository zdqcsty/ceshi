package com.example.ceshi.sh;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtils {

    public static Connection getConnection(String jdbcUrl, String user) throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        return DriverManager.getConnection(jdbcUrl, user, "");
    }

    public static Connection getKerberosConnection(String jdbcUrl, Properties prop) throws Exception {

        Class.forName("org.apache.hive.jdbc.HiveDriver");
        System.setProperty("java.security.krb5.conf", prop.getProperty("kerberps.krb5.path"));
        Configuration configuration = new Configuration();
        configuration.addResource(new Path(prop.getProperty("hadoop.conf.dir") + "/core-site.xml"));
        configuration.addResource(new Path(prop.getProperty("hadoop.conf.dir") + "/hdfs-site.xml"));
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        //这里keytab也是需要用绝对路径的
        UserGroupInformation UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI(prop.getProperty("kerberos.keytab.priciple"), prop.getProperty("kerberos.keytab.path"));

        return UGI.doAs(new PrivilegedAction<Connection>() {
            @Override
            public Connection run() {
                try {
                    return DriverManager.getConnection(jdbcUrl);
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }


}
