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

    public static void main(String[] args) throws Exception {
//        copyPathtoPath("/user/zgh/party","/user/zgh/digui");

/*        Path path =new Path("aaa","bbb");
        System.out.println(path.toString());*/
        download(getFileSystem());

    }

    public static void download(FileSystem fs) throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/user/zgh/tmp/demoaaa"));
        System.out.println(fileStatuses.length);
    }

    //用递归写的移动目录
    public static boolean copyPathtoPath(String srcPath, String dstPath) throws Exception {
        /**
         *    /user/zgh/party
         */
        Path path = new Path(srcPath);
        FileSystem fs = getFileSystem();
        if (fs.isDirectory(new Path(srcPath))) {
            fs.mkdirs(new Path(srcPath));
            String[] split = srcPath.split("/");
            String resultDstPath = dstPath + "/" + split[split.length - 1];
            FileStatus[] fileStatuses = fs.listStatus(new Path(srcPath));
            for (FileStatus fileStatus : fileStatuses) {
                copyPathtoPath(fileStatus.getPath().toString(), resultDstPath);
            }
        } else {
            int len;
            String[] split = srcPath.split("/");
            String resultDstPath = dstPath + "/" + split[split.length - 1];
            FSDataInputStream srcFis = fs.open(new Path(srcPath));
            FSDataOutputStream dstFos = fs.create(new Path(resultDstPath));
            byte[] descArray = new byte[2048];
            int bytesRead = srcFis.read(descArray);
            while (bytesRead >= 0) {
                dstFos.write(descArray);
                bytesRead = srcFis.read(descArray);
            }
        }
        return true;
    }
}
