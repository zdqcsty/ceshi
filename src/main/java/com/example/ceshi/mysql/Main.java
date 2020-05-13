package com.example.ceshi.mysql;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
            ResultSet resultSet = connection.prepareStatement("select count(1) from DWA.DWA_DEMO").executeQuery();
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


        try {
            dos = fs.create(new Path("/user/zgh/demo"));
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(dos));
            for (int i = 0; i < 3; i++) {

                String sql = "select * from DWA.DWA_DEMO limit (" + (i * avg + 1) + "," + (i * avg + 1+avg)+")";
                System.out.println(sql);
                threadPool.submit(new MysqlRunable(sql,bw));
            }
            threadPool.shutdown();
            long end = System.currentTimeMillis();
            System.out.println((end-start)/1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
