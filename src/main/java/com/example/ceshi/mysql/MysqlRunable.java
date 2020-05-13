package com.example.ceshi.mysql;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class MysqlRunable implements Runnable {
    String sql;
    BufferedWriter bw;

    public MysqlRunable(String sql, BufferedWriter bw) {
        this.sql = sql;
        this.bw = bw;
    }

    @Override
    public void run() {

        Connection connection = MysqlPool.getConnection();
        try {
            long start = System.currentTimeMillis();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setFetchSize(Integer.MIN_VALUE);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            long end = System.currentTimeMillis();
            System.out.println("------"+Thread.currentThread()+"获取数据消耗时间"+(end-start)/1000);
            long start1 = System.currentTimeMillis();
            while (resultSet.next()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(resultSet.getString(i));
                }
                sb.append("\n");
//                bw.writeBytes(sb.toString());
                bw.write(sb.toString());
                sb.setLength(0);
            }
            long end1 = System.currentTimeMillis();
            connection.close();
            System.out.println("------"+Thread.currentThread()+"拼接写入消耗时间"+(end1-start1)/1000);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
