package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.stereotype.Component;

@Component
public class ConnectionPool {

    public static DruidDataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.facebook.presto.jdbc.PrestoDriver");
        druidDataSource.setUrl("jdbc:presto://10.130.7.204:16001/dev001/devtest");
        druidDataSource.setUsername("hadoop");
        //最小空闲连接数量
        druidDataSource.setMinIdle(5);
        //连接池最大连接数，默认是10
        druidDataSource.setMaxActive(10);
        return druidDataSource;
    }

}
