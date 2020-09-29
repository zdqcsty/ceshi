package com.example.ceshi.bingfa;

import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class CopyDirectory {

    public static void main(String[] args) throws Exception {
        CopyDirectory cd=new CopyDirectory();

        final boolean b = cd.copyDirectory("E:\\step_step_23520057332_parquet", "E:\\bbb");

        System.out.println(b);
    }
    /**
     * 这次使用CountDownLatch去完成操作
     * @param srcPath
     * @param dstPath
     * @return
     */
    public boolean copyDirectory(String srcPath,String dstPath) throws Exception {

        if (!checkPathExist(srcPath)){
            throw new RuntimeException("srcPath do not exist");
        }

        if (!checkPathExist(dstPath)){
            File file=new File(dstPath);
            file.mkdirs();
        }

        File file=new File(srcPath);
        final ThreadPoolExecutor executor = getExecutor();

        final File[] files = file.listFiles();
        final int length = files.length;
        CountDownLatch cdl=new CountDownLatch(length);

        AtomicReference<Exception> acf=new AtomicReference<Exception>();

        for (File f:files){
            String resultDstPath=dstPath+"/"+f.getName();
            CopyFileTask cft=new CopyFileTask(f.getPath(),resultDstPath,acf,cdl);
            executor.execute(cft);
        }
        try {
            cdl.await();
            if (acf.get() == null){
                return true;
            }
            throw acf.get();
        } catch (InterruptedException e) {
            return false;
        }
    }


    public boolean checkPathExist(String path){
        File file =new File(path);
        return file.exists()&&file.isDirectory();
    }

    public ThreadPoolExecutor getExecutor(){
        return new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public void test() throws Exception {
        File file=new File("E:\\demo\\a.txt");
        BufferedReader br=new BufferedReader(new FileReader(file));
        System.out.println(br.readLine());
    }

}
