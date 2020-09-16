package com.example.ceshi.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Test {

    public static void main(String[] args) throws IOException {

    /*    FileSystem fs = HadoopUtils.getFileSystem();
        FileStatus[] status = fs.listStatus(new Path("/user/zgh/aaa/"));
        for (FileStatus file : status) {
//            System.out.println(file.getPath());
            org.apache.hadoop.fs.FileUtil.copy(fs, file.getPath(), fs, new Path("/user/zgh/hhh/"), false, new Configuration());
        }
        fs.close();*/


        List<String> headers=new ArrayList<>();
        headers.add("aaa");
        headers.add("bbb");
        headers.add("ccc");
        headers.add("ddd");

        String header = String.join(",", headers);
/*        StringJoiner sj = new StringJoiner(",");
        for (int i = 0; i < headers.size(); i++) {
            sj.add(headers.get(i));
        }*/
        System.out.println(header);
    }
}
