package com.example.ceshi.aaa;

public class MyThread20_1 extends Thread {
    private ThreadDomain20 td;

    public MyThread20_1(ThreadDomain20 td) {
        this.td = td;
    }

    public void run() {
        td.otherMethod();
    }
}