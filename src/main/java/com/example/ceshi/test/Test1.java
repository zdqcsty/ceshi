package com.example.ceshi.test;

import com.example.ceshi.mysql.HadoopWrite;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class Test1 {

    public static void main(String[] args) {

        HadoopWrite hw=new HadoopWrite();
        FileSystem fs = hw.getFileSystem();

        try {
            FSDataOutputStream dos = fs.create(new Path("/user/zgh/demo"));
            dos.writeBytes("demodemo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
