//package com.example.ceshi.mysql;
//
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//
//import java.io.IOException;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//public class Test1 {
//
//    public static void main(String[] args) {
//
//        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 1,
//                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(4),
//                new ThreadPoolExecutor.DiscardOldestPolicy());
//        HadoopWrite hw = new HadoopWrite();
//        FileSystem fs = hw.getFileSystem();
//        FSDataOutputStream dos = null;
//        try {
//            dos = fs.create(new Path("/user/zgh/demo"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String sql = "select * from ceshi limit 1,10";
//        threadPool.submit(new MysqlRunable(sql,dos));
//        try {
//            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
//            dos.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//}
