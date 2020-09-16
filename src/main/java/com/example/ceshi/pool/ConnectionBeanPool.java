package com.example.ceshi.pool;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 * 作者：liliang
 * 日期：2020/4/29
 */
public class ConnectionBeanPool extends ObjectPool {

    private String url;  // 链接url
    private String usr;  // 账户名
    private String pwd;  // 密码
    private String KRB5_CONF;
    private String PRINCIPAL;
    private String KEYTAB ;

    public ConnectionBeanPool(String driver, String url, String usr, String pwd) {
        super();
        try {
            Class.forName(driver).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.url = url;
        this.usr = usr;
        this.pwd = pwd;
    }

    @Override
    protected ConnectionBean create() {
        try {
            ConnectionBean connectionBean = new ConnectionBean();
            /** 使用Hadoop安全登录 **/
            Configuration conf = new Configuration();
            Connection connection = DriverManager.getConnection(url, usr, pwd);
            if (connection == null) {
                System.out.print("null connection");
            }
            connectionBean.setConnection(connection);
            connectionBean.setUpdateTime(new Date().getTime());
            return connectionBean;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void expire(ConnectionBean o) {
        try {
            o.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validate(ConnectionBean o) {
        try {
            return (!(o.getConnection()).isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
