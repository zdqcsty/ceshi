package com.example.ceshi.test;

import lombok.SneakyThrows;

public class Test1 {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("111");

        Thread thread=new Thread(new LogRunnable());
        thread.setDaemon(true);
        thread.start();


        Thread.sleep(3000);

        System.out.println("222");
    }

    static class LogRunnable implements Runnable {

        @SneakyThrows
        @Override
        public void run() {

            for (int i = 0; i < 100; i++) {
                Thread.sleep(1000);
                System.out.println("hahah");
            }

        }
    }
}
