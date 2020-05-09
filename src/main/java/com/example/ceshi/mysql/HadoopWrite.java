package com.example.ceshi.mysql;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
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
        long start = System.currentTimeMillis();
        HadoopWrite hw=new HadoopWrite();
        FileSystem fs = hw.getFileSystem();
        FSDataOutputStream dos = fs.create(new Path("/user/zgh/demo"));

        Connection connection = MysqlPool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from ceshi");
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()){
                StringBuffer sb=new StringBuffer();
                for (int i=1;i<=columnCount;i++){
                    sb.append(resultSet.getString(i));
                }
                sb.append("\n");
                dos.writeBytes(sb.toString());
                sb.setLength(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println((end-start)/1000);

        dos.close();
        fs.close();
    }
}
