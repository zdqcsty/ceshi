package com.example.ceshi;

import com.example.ceshi.pythondemo.DiaoYong;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class DemoController {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    private static String CONNECTION_URL ="jdbc:hive2://10.130.2.179:10001/lxg;principal=hs2/node06-cuidong.novalocal@BONCDSC.GREAT;auth=kerberos";

    @RequestMapping(value = "/testHdfs")
    @ResponseBody
    public Boolean testHdfs(HttpServletResponse response) throws IOException, SQLException {

        InputStream resourceAsStream = DiaoYong.class.getClassLoader().getResourceAsStream("demo.txt");


        byte[] buff = new byte[1024];
        BufferedInputStream bis = new  BufferedInputStream(resourceAsStream);
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


    @RequestMapping(value = "/testThrift")
    @ResponseBody
    public Boolean testThrift(String keytab,String priciple,String queue) throws IOException, SQLException {

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
        String sql1="set set yarn.queue.name="+queue;

//        connection.prepareStatement(sql).execute();
        connection.prepareStatement(sql).execute();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getInt(1));
        }


        connection.close();


        return Boolean.TRUE;
    }



}
