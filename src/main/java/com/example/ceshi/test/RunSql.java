package com.example.ceshi.test;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class RunSql implements Runnable {

    private static Logger LOG = org.slf4j.LoggerFactory.getLogger(JdbcTemplate.class);

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10001/hebing";

    public AtomicReference<Exception> ex;
    public String sql;
    public Semaphore sem;

    public RunSql(AtomicReference<Exception> ex, Semaphore sem, String sql) {
        this.ex = ex;
        this.sql = sql;
        this.sem = sem;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.exit(1);
        }
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");
        } catch (SQLException throwables) {
            return null;
        }
        return connection;
    }


    @Override
    public void run() {
        Statement state = null;
        Connection conn = null;
        try {
            sem.acquire();
            conn = getConnection();
            state = conn.createStatement();
            double start = System.currentTimeMillis();
            state.execute(sql);
            double end = System.currentTimeMillis();
            LOG.info("execute sql " + sql + " cost time " + ((end - start) / 1000 ));
        } catch (Exception e) {
            ex.set(e);
        } finally {
            sem.release();
            try {
                if (state != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
    }
}
