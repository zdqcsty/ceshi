package com.example.ceshi.bingfa;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownLoadFile implements Runnable {

    public static int srcFileSize;  // 原文件大小
    public static int threadNum = 5; // 线程数
    public static ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
    private String url; //源文件路径
    private String dst; //目标文件路径
    private int start; //开始下载位置
    private int end; //结束下载位置

    public DownLoadFile() {
        super();
    }

    public DownLoadFile(String url, String dst, int start, int end) {
        super();
        this.url = url;
        this.dst = dst;
        this.start = start;
        this.end = end;
    }

    /**
     *
     * @param url 要拷贝的文件
     * @param dst 拷贝到哪儿
     * @return
     * @throws IOException
     */
    private void DownLoad(String url,String dst) throws IOException {
        //  获取原文件总大小
        FileInputStream fileInputStream = new FileInputStream(url);
        srcFileSize = fileInputStream.available();
        fileInputStream.close();

        // 获取每个线程需要复制的字节大小
        int partSize = srcFileSize/threadNum;

        // 目的文件夹如果不存在，则创建
        File dstFile = new File(dst);
        if(!dstFile.exists()){
            dstFile.createNewFile();
        }

        // 启用threadNum个线程进行复制文件
        for(int i=0;i<threadNum;i++){
            int start = partSize * i;
            int end = (i==(threadNum-1))?srcFileSize:partSize*(i+1);
            DownLoadFile dLF = new DownLoadFile(url, dst, start, end);
            executorService.execute(dLF);
        }
        executorService.shutdown();
    }

    //执行拷贝
    public void run() {
        System.out.println("start：" + start + "  end:" + end );

        try {
            String threadName = Thread.currentThread().getName(); // 线程名
            System.out.println("线程"+threadName+"开始拷贝文件数据，拷贝数据段："+start+"-"+end);

            // 定位目标文件位置
            RandomAccessFile randomAccessFile = new RandomAccessFile(dst, "rw");
            randomAccessFile.seek(start);

            // 定位原文件位置
            InputStream inputStream = new FileInputStream(url);
            inputStream = skipFully(inputStream,start);

            // 复制文件内容
            int sumSize = end - start; // 复制总字节数
            int readSize = 1024*1024*8; // 缓存8MB
            while(sumSize>0){
                //  缓存大于拷贝字节，使用拷贝字节
                if(readSize>sumSize){
                    readSize = sumSize;
                }

                // 从原文件输入流获取拷贝字节数据
                byte[] tmp = new byte[readSize];
                inputStream.read(tmp);

                // 向目标文件写入拷贝字节数据
                randomAccessFile.write(tmp);
                sumSize -= readSize;
            }

            // 拷贝完毕后，双方都关闭
            inputStream.close();
            randomAccessFile.close();

            System.out.println("线程"+threadName+"拷贝数据结束，数据段："+start+"-"+end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream skipFully(InputStream in,long num)throws Exception{
        long remainning = num;
        long len = 0;
        while(remainning>0){
            len = in.skip(num);
            remainning -= len;
        }
        return in;
    }

    public static void main(String args[]) {
        DownLoadFile d = new DownLoadFile();
        try {
            d.DownLoad("D:\\1.exe","D:\\\\1_copy.exe");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}