package com.example.ceshi.jdbc;

import com.example.ceshi.hadoop.HadoopUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import scala.math.Ordering;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.StringJoiner;

public class Test4 {


    private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static final String CONNECTION_URL = "jdbc:hive2://10.130.7.208:10000/ceshi";


    public static void main(String[] args) throws SQLException, IOException, InterruptedException, URISyntaxException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = DriverManager.getConnection(CONNECTION_URL, "hadoop", "");

        ResultSet resultSet = connection.prepareStatement("select * from nullceshi limit 10").executeQuery();


//        String[] strArr = {"cid","cname","ename","phone","email","address"};
        String[] strArr = {"cid","cname","phone","email"};
        int columnCnt = resultSet.getMetaData().getColumnCount();
        Path file = new Path("/user/zgh/dcacdbcab","data.csv");
        try (FileSystem fs = HadoopUtils.getFileSystem()) {
            try (FSDataOutputStream outStream = fs.create(file)) {
                try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(outStream))) {
                    try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(strArr))) {
                        while (resultSet.next()) {
                            Object[] record = new Object[columnCnt];
                            for (int i = 1; i <= columnCnt; i++) {
                                record[i-1] = resultSet.getString(i);
                            }
                            printer.printRecord(record);
                        }
                    }
                }
            }
        }
    }

}

