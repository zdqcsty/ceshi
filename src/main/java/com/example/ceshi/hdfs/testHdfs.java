package com.example.ceshi.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.InputStream;

public class testHdfs {


    public static void main(String[] args) throws IOException {

        Configuration conf=new Configuration();

        conf.addResource("hebing/core-site.xml");
//        conf.addResource("hebing/yarn-site.xml");
        conf.addResource("hebing/hdfs-site.xml");
        FileSystem fs = FileSystem.get(conf);

//        InputStream open = fs.open(new Path("/datascience/32ef3f6c8eba4bfd82013fbf5f8de582/dataSet/vbap28ef4abb465d4c27afcd96a10696fbc2/datatype/Configuration.json"));
        InputStream open = fs.open(new Path("/user/zgh/test1.csv"));


        String s = IOUtils.toString(open);
        System.out.println(s);


    }

}
