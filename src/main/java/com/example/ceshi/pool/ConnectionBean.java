package com.example.ceshi.pool;

import java.sql.Connection;

/**
 * 作者：liliang
 * 日期：2020/4/29
 */
public class ConnectionBean {

    private Connection connection; // 链接信息
    private long updateTime;       // 更新时间

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

}
