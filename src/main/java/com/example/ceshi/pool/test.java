package com.example.ceshi.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 作者：liliang
 * 日期：2020/4/29
 */
public class test {
    public static void main(String[] args) throws SQLException {
      /*  String KRB5_CONF = "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\krb5.conf";
        String PRINCIPAL = "test001@DEVTEST.BONC";
        String KEYTAB = "E:\\study_workSpace\\ceshi\\src\\main\\resources\\ceshi\\test001.ketab";*/

        String HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
        String URL = "jdbc:hive2://10.130.7.208:10001/ceshi";
        ConnectionBeanPool connection = new ConnectionBeanPool(HIVE_DRIVER,URL,"hadoop","");
        long start = System.currentTimeMillis();
        ConnectionBean conn = connection.get("hadoop");
        Connection connection1 = conn.getConnection();
        long end = System.currentTimeMillis();
        System.out.println("花费时间---------"+(end-start)/1000);

        long astart = System.currentTimeMillis();
        PreparedStatement ps = connection1.prepareStatement("select * from vbap09c0c78ee8264386fed8b3a7 limit 10");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getObject(1));
        }
        long bend = System.currentTimeMillis();
        System.out.println("chaxun 花费时间---------"+(bend-astart)/1000);
        System.out.println("连接成功");
    }
}
