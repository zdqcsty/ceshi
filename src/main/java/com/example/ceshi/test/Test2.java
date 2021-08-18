package com.example.ceshi.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test2 {

    public static void main(String[] args) {

        //这种情况说明执行不到
/*        try {
            Thread thread = new Thread(new ExecutTest());
            thread.start();
        }catch (Exception e){
            System.out.println("hahahah");
        }*/


        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<?> submit = executorService.submit(new ExecutTest());

        try {
            submit.get();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    static class ExecutTest implements Runnable {
        @Override
        public void run() {
            throw new RuntimeException("HAHAHA");
        }
    }

}

