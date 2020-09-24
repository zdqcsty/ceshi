package com.example.ceshi.copyFsToFs;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CopyFileTask implements Runnable {

    private FileSystem srcFs;
    private Path srcPath;
    private FileSystem dstFs;
    private Path dstPath;
    private AtomicReference<Exception> ex;
    private AtomicInteger cnt;

    public CopyFileTask(FileSystem srcFs, Path srcPath, FileSystem dstFs, Path dstPath, AtomicInteger cnt, AtomicReference<Exception> ex) {
        this.srcFs = srcFs;
        this.srcPath = srcPath;
        this.dstFs = dstFs;
        this.dstPath = dstPath;
        this.ex = ex;
        this.cnt = cnt;
    }

    @Override
    public void run() {
        try (FSDataInputStream inputStream = srcFs.open(srcPath)) {
            try (FSDataOutputStream outputStream = dstFs.create(dstPath, true, 16384)) {
                IOUtils.copyBytes(inputStream, outputStream, 8192);
            }
            cnt.decrementAndGet();
        } catch (Exception e) {
            ex.set(e);
        }
    }
}
