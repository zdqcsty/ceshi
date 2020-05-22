package com.example.ceshi.mysql;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;

public class HadoopWrite {

    public FileSystem getFileSystem()  {
        System.setProperty("HADOOP_USER_NAME","hadoop");
        FileSystem fileSystem=null;
        Configuration conf=new Configuration();
        conf.addResource("hebing/core-site.xml");
        conf.addResource("hebing/hdfs-site.xml");

        try {
            fileSystem = FileSystem.get(conf);
        } catch (IOException e) {
        }
        return fileSystem;
    }


    public static void main(String[] args) throws IOException {
        HadoopWrite hw=new HadoopWrite();
        FileSystem fs = hw.getFileSystem();
        FSDataOutputStream dos = fs.create(new Path("/user/zgh/demo"));
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(dos));

        long start = System.currentTimeMillis();
        Connection connection = MysqlPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from demo");
//            preparedStatement.setFetchSize(Integer.MIN_VALUE);
//            connection.setAutoCommit(false);
            preparedStatement.setFetchSize(3000);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            long end = System.currentTimeMillis();
            System.out.println("------"+Thread.currentThread()+"获取数据消耗时间"+(end-start)/1000);
            long start1 = System.currentTimeMillis();
            while (resultSet.next()){
                StringBuffer sb=new StringBuffer();
                for (int i=1;i<=columnCount;i++){
                    sb.append(resultSet.getString(i));
                }
                sb.append("\n");
//                dos.writeBytes(sb.toString());
                bw.write(sb.toString());
                sb.setLength(0);
            }
            long end1 = System.currentTimeMillis();
            System.out.println("------"+Thread.currentThread()+"拼接写入消耗时间"+(end1-start1)/1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("花费时间------------"+(end-start)/1000);

        dos.close();
        fs.close();
    }
}
