package com.example.ceshi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.Map;

@Controller
public class DemoController {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private static String CONNECTION_URL = "jdbc:hive2://10.130.2.179:10001/lxg;principal=hs2/node06-cuidong.novalocal@BONCDSC.GREAT;auth=kerberos";

    @RequestMapping(value = "/testHive",method = RequestMethod.POST)
    @ResponseBody
    public String testHive() {

//        for (Map.Entry<Integer, String> entry : map.entrySet()) {
//
//            System.out.println("==========");
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            System.out.println("==========");
//        }

//        Demo.getDO();

        return "sucess";
    }


    @RequestMapping(value = "/testThrift")
    @ResponseBody
    public Boolean testThrift(String keytab, String priciple, String queue) throws IOException, SQLException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication", "Kerberos");
        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab(priciple, keytab);

//        Connection connection = dataSource.getConnection();
        Connection connection = DriverManager.getConnection(CONNECTION_URL);


//        Thread.sleep(20000);
//        connection.prepareStatement("set spark.yarn.queue=hushunpeng").execute();
        String sql = "select count(*) from angie.xingnengceshi";
        String sql1 = "set set yarn.queue.name=" + queue;

//        connection.prepareStatement(sql).execute();
        connection.prepareStatement(sql).execute();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }


        connection.close();


        return Boolean.TRUE;
    }

    @RequestMapping(value = "/testMysql")
    @ResponseBody
    public void testMysql() throws IOException, SQLException {

        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/ceshi?serverTimezone=Asia/Shanghai&useSSL=false&autoReconnect=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8";
        String userName = "root";
        String password = "root";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
            stmt = conn.createStatement();
            String sql = "select * from products";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(1);
                System.out.println(id);
            }
/*            // 关闭资源
            rs.close();
            stmt.close();
            conn.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

}
