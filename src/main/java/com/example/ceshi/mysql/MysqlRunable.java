package com.example.ceshi.mysql;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class MysqlRunable implements Runnable {
    String sql;
    FSDataOutputStream dos;

    public MysqlRunable(String sql, FSDataOutputStream dos) {
        this.sql = sql;
        this.dos = dos;
    }

    @Override
    public void run() {

        Connection connection = MysqlPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            System.out.println("--------------------");
            System.out.println(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(resultSet.getString(i));
                }
                sb.append("\n");
                dos.writeBytes(sb.toString());
                sb.setLength(0);
            }
            connection.close();
            System.out.println("结束");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
