package com.example.ceshi.bingfa;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class CopyFileTask implements Runnable {

    private String srcFile;
    private String dstFile;
    private AtomicReference<Exception> exception;
    private CountDownLatch cdl;

    public CopyFileTask(String srcFile, String dstFile, AtomicReference<Exception> exception , CountDownLatch cdl) {
        this.srcFile = srcFile;
        this.dstFile = dstFile;
        this.exception=exception;
        this.cdl=cdl;
    }

    @Override
    public void run() {
        try (FileInputStream fis = new FileInputStream(srcFile);
             FileOutputStream fos = new FileOutputStream(dstFile)) {
            IOUtils.copy(fis,fos,4028);
        }catch (IOException e) {
            exception.set(e);
        }finally {
            cdl.countDown();
        }
    }
}
