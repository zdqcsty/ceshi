package com.example.ceshi.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HadoopUtils {

    public static FileSystem getFileSystem() {

        //以我们公司自己的合并环境当作示例
        System.setProperty("HADOOP_USER_NAME", "hadoop");   //以Hadoop用户，不然报权限错误
        FileSystem fileSystem = null;
        Configuration conf = new Configuration();
        conf.addResource("hebing/core-site.xml");
        conf.addResource("hebing/hdfs-site.xml");
        //这个对与内外网环境很有用 （如果内外网不配置这个将访问将会报错，访问ip报错，设置这个参数是直接访问主机名）
        conf.set("dfs.client.use.datanode.hostname", "true");

        try {
            fileSystem = FileSystem.get(conf);
        } catch (IOException e) {
        }
        return fileSystem;
    }

    public static void main(String[] args) {

        HadoopUtils hu=new HadoopUtils();


    }

    class demo{

        public  void getCeshi(){

            System.out.println("hahah");

        }

    }

}
