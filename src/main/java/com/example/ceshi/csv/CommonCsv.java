package com.example.ceshi.csv;


import com.example.ceshi.hadoop.HadoopUtils;
import com.example.ceshi.jdbc.JdbcTemplate;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.sql.*;

public class CommonCsv {

    public static void main(String[] args) throws IOException {


        readByAuto("E:\\studyWorkSpace\\ceshi\\src\\main\\resources\\datafile\\a.csv");



    }


    /**
     * 按照自己定义的数组表头读取
     * @param path
     * @throws IOException
     */
    public static  void  readByArr (String path) throws IOException {
        Reader in = new FileReader(path);
        String[] header = {"id", "name"};
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(header).parse(in);
        for (CSVRecord record : records) {
            String lastName = record.get("id");
            String firstName = record.get("name");
            System.out.println(lastName);
            System.out.println(firstName);
        }
    }

    /**
     * 按照csv的第一行读取
     * @param path
     * @throws IOException
     */
    public static void readByAuto(String path) throws IOException {
        Reader in = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String lastName = record.get("id");
            String firstName = record.get("name");
            System.out.println(lastName);
            System.out.println(firstName);
        }
    }


    /**
     * 将JDBC读取的数据写CSV到HDFS
     * @param path
     * @throws IOException
     */
    public static void  wirteToHdfs(String path) throws IOException {

        FileSystem fs = HadoopUtils.getFileSystem();
        FSDataOutputStream fos = fs.create(new Path("demo"));
        CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(fos), CSVFormat.DEFAULT);
        printer.printRecord();

        Connection connection = JdbcTemplate.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from demo");
            printer.printRecord(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
