package com.example.ceshi.mysql;

import org.apache.hadoop.fs.FileSystem;

public class MysqlRunable implements  Runnable{

    @Override
    public void run() {

        HadoopWrite hw=new HadoopWrite();
        FileSystem fs = hw.getFileSystem();


    }
}
