package com.example.ceshi.aaa;

public class MyThread19_1 extends Thread {
    private ThreadDomain19 td;

    public MyThread19_1(ThreadDomain19 td) {
        this.td = td;
    }

    public void run() {
        td.serviceMethodB();
    }
}
