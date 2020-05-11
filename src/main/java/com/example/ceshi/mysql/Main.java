package com.example.ceshi.mysql;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Integer totalSize = 0;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 1,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(4),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        Connection connection = MysqlPool.getConnection();
        try {
            ResultSet resultSet = connection.prepareStatement("select count(1) from ceshi").executeQuery();
            while (resultSet.next()) {
                totalSize = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int avg = totalSize / 3;
        HadoopWrite hw = new HadoopWrite();
        FileSystem fs = hw.getFileSystem();
        FSDataOutputStream dos = null;
        String sql="select * from ceshi limit 1,338668";
        try {
            dos = fs.create(new Path("/user/zgh/demo"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadPool.submit(new MysqlRunable(sql,dos));


   /*     try {
            dos = fs.create(new Path("/user/zgh/demo"));
            for (int i = 0; i < 1; i++) {
                String sql = "select * from ceshi limit " + (i * avg + 1) + "," + avg;
                threadPool.submit(new MysqlRunable(sql,dos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        long end = System.currentTimeMillis();

        System.out.println((end-start)/1000);

    }

}
