package com.example.ceshi.aaa;

public class MyThread21_1 extends Thread {
    private ThreadDomain21 td;

    public MyThread21_1(ThreadDomain21 td) {
        this.td = td;
    }

    public void run() {
        td.demo();
//        td.setUserNamePassword("B", "B");
    }
}
