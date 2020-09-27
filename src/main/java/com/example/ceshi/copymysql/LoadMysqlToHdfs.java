package com.example.ceshi.copymysql;

import com.example.ceshi.hadoop.HadoopUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.sql.*;

public class LoadMysqlToHdfs {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://10.130.7.34:3306/ceshi?autoReconnect=true&useSSL=false";

    public static void main(String[] args) throws Exception {

/*        long start = System.currentTimeMillis();
        Connection conn = getConn();
        loadDataToHdfs(conn,"/user/zgh/cdcadaceww");
        long end = System.currentTimeMillis();
        System.out.println("写入到hdfs 所需时间为----"+(end-start)/1000);*/

        outFile("/tmp/yanaaagli.csv");

/*        File file=new File("/tmp/demo.txt");
        FileInputStream fis = new FileInputStream(file);
        String s = IOUtils.toString(fis);
        System.out.println(s);*/

    }

    public static boolean outFile(String path) throws SQLException {
        Connection conn = getConn();
        Statement stat = conn.createStatement();
        return stat.execute("SELECT * FROM yangli limit 10  into outfile '"+path+"' CHARACTER SET utf8 fields terminated by ',' optionally enclosed by '\\\"'");
    }

    public static boolean moveCsvToHdfs(String mysqlCsvPath, String HdfsPath, FileSystem fs) {


        return false;

    }


    public static void loadDataToHdfs(Connection conn,String path) throws Exception {

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from yangli limit 10");
/*        while(resultSet.next()){
            System.out.println(resultSet.getString(1)+","+resultSet.getString(2));
        }*/
        FileSystem fs = HadoopUtils.getFileSystem();
        FSDataOutputStream fos = fs.create(new Path(path));

        CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(fos), CSVFormat.DEFAULT);
        printer.printRecords(resultSet);
        printer.close();
    }


    public static Connection getConn(){

        Connection connection=null;
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            connection = DriverManager.getConnection(CONNECTION_URL,"root","yaliceshi1");
        } catch (SQLException throwables) {
            return null;
        }
        return connection;
    }


}
