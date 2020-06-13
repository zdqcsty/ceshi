package com.example.ceshi;

import com.example.ceshi.mysql.MysqlPool;
import com.example.ceshi.pythondemo.DiaoYong;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

@Controller
public class DemoController {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private static String CONNECTION_URL = "jdbc:hive2://10.130.2.179:10001/lxg;principal=hs2/node06-cuidong.novalocal@BONCDSC.GREAT;auth=kerberos";

    @RequestMapping(value = "/testHdfs")
    @ResponseBody
    public Boolean testHdfs(HttpServletResponse response) throws IOException, SQLException {

        InputStream resourceAsStream = DiaoYong.class.getClassLoader().getResourceAsStream("demo.txt");


        byte[] buff = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(resourceAsStream);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Boolean.TRUE;
    }

    @RequestMapping(value = "/testHive")
    @ResponseBody
    public Boolean testHive(String sql) throws IOException, SQLException {
        Connection connection = MysqlPool.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.execute();
//            ResultSet resultSet = preparedStatement.executeQuery();
/*            while (resultSet.next()) {
                String str = resultSet.getString(1);
                System.out.println(str);
            }*/
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
//            connection.close();
        }
        return Boolean.TRUE;


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
