//package com.example.ceshi;
//
//import com.zaxxer.hikari.HikariDataSource;
//import com.zaxxer.hikari.HikariPoolMXBean;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.security.UserGroupInformation;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.management.JMX;
//import javax.management.MBeanServer;
//import javax.management.ObjectName;
//import java.io.IOException;
//import java.lang.management.ManagementFactory;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class TestJdbc {
//
//    public static HikariDataSource hikariDataSource;
//    private static HikariPoolMXBean poolProxy;
//    private static Map<String, HikariPoolMXBean> map = new HashMap<>();
//
//
////    static{
////        hiveDataSource();
////    }
//
//    public static void main(String[] args) throws SQLException {
//        HikariDataSource hikariDataSource = hiveDataSource();
//        Connection connection = hikariDataSource.getConnection();
//
//        ResultSet resultSet = connection.prepareStatement("select * from shouhang").executeQuery();
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString(1));
//        }
//
//    }
//
//    public static HikariDataSource hiveDataSource() {
//        hikariDataSource = new HikariDataSource();
//        try {
////            hikariDataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
//            hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
////            hikariDataSource.setJdbcUrl("jdbc:hive2://10.130.2.132:10001/zgh");
////            hikariDataSource.setJdbcUrl("jdbc:hive2://10.130.7.54:10001/stress;principal=hs2/hadoop10.novalocal@BONCST.LBHY;auth=kerberos");
//            hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ceshi?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding=utf8&useSSL=true&serverTimezone=UTC");
//            hikariDataSource.setUsername("root");
//            hikariDataSource.setPassword("root");
//            //最小空闲连接数量
//            hikariDataSource.setMinimumIdle(5);
//            //空闲连接存活最大时间，默认600000（10分钟）
////            dataSource.setIdleTimeout(600000);
//            //连接池最大连接数，默认是10
//            hikariDataSource.setMaximumPoolSize(5);
//            hikariDataSource.setPoolName("ceshi");
//
//            hikariDataSource.setMaxLifetime(6000);
//            //此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
////            dataSource.setMaxLifetime(600000);
//            //数据库连接超时时间,默认30秒，即30000
////            hikariDataSource.setIdleTimeout(10000);
////            hikariDataSource.setConnectionTimeout(60000);
////            dataSource.setConnectionTestQuery("SELECT 1");
//            //连接将被测试活动的最大时间量
////            dataSource.setValidationTimeout(3000);
//            hikariDataSource.setLoginTimeout(5);
//
//            hikariDataSource.setRegisterMbeans(true);
//
//            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
//            ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool (ceshi)");
//            HikariPoolMXBean poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);
//            map.put(poolName.toString(), poolProxy);
//
//            Connection connection = hikariDataSource.getConnection();
//            connection.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("无法初始化hiveDataSource", e);
//        }
//        return hikariDataSource;
//    }
//
//    public void HikariMonitor() {
//        if (map.size() == 0) {
//        } else {
//            for (Map.Entry<String, HikariPoolMXBean> entry : map.entrySet()) {
//                HikariPoolMXBean value = entry.getValue();
//                System.out.println("HikariPoolState = HikariPoolName---" + entry.getKey() + "" +
//                        "Active=[" + value.getActiveConnections() + "] Idle=[" + value.getIdleConnections() + "] " +
//                        "Wait=[" + value.getThreadsAwaitingConnection() + "] Total=[" + value.getTotalConnections() + "])");
//
//
////            System.out.println("HikariPoolState = "+map.get("ceshi")
////                    + "Active=[" + String.valueOf(poolProxy.getActiveConnections() + "] "
////                    + "Idle=[" + String.valueOf(poolProxy.getIdleConnections() + "] "
////                    + "Wait=["+poolProxy.getThreadsAwaitingConnection()+"] "
////                    + "Total=["+poolProxy.getTotalConnections()+"]")));
//            }
//        }
//    }
//
//    public static boolean createTableWithSql(String sql) throws SQLException, IOException {
////        Configuration configuration = new Configuration();
////        configuration.set("hadoop.security.authentication", "Kerberos");
////        UserGroupInformation.setConfiguration(configuration);
////        UserGroupInformation.loginUserFromKeytab("hs2/hadoop10.novalocal@BONCST.LBHY", "/opt/beh/metadata/key/hs10.keytab");
//
//        Connection connection = hikariDataSource.getConnection();
//        connection.prepareStatement(sql).execute();
//        return true;
//    }
//
//}
