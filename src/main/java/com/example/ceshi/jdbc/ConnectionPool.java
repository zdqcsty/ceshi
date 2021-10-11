package com.example.ceshi.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class ConnectionPool {

    public static DruidDataSource getDataSource() {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:hive2://192.168.1.113:10001/hebing");
        druidDataSource.setUsername("hadoop");
        //最小空闲连接数量
        druidDataSource.setMinIdle(5);
        //连接池最大连接数，默认是10
        druidDataSource.setMaxActive(10);
        return druidDataSource;
    }


    public static void main(String[] args) throws SQLException {
        final DruidDataSource dataSource = getDataSource();

        final DruidPooledConnection connection = dataSource.getConnection();

    }

}
