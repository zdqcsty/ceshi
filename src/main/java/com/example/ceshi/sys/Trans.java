package com.example.ceshi.sys;

public class Trans {
    private static Object lock = new Object();

    public synchronized void printNum(int num) {
        System.out.print(Thread.currentThread());
        for (int i = 0; i < 25; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}

