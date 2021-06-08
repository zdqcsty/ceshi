package com.example.ceshi.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestVolatile {
    public static void main(String[] args) {
        myData myData = new myData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) { //使用IntelliJ IDEA的读者请注意，
            // 在IDEA中运行这段程序，会由于IDE自动创建一条名为Monito rCtrl-Break的线程,所以为2
            Thread.yield();//当前线程由执行态变为就绪态，让出cpu
        }
        System.out.println(Thread.currentThread().getName() + "\t" + myData.atomicInteger);
    }
}

class myData {
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();// 表示i++
    }
}

