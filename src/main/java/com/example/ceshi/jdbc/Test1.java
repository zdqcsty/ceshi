package com.example.ceshi.jdbc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {

    public static void main(String[] args) {

        System.out.println("main  xiancheng");

        ExecutorService executor = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ATS Logger %d").build());

        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    System.out.println("hahahah");
                    Thread.sleep(1000);
                }
            }
        });

        Thread thread=new Thread();
        thread.setDaemon(true);


        System.out.println("main  jieshu");

    }


}
