package com.example.ceshi.aaa;

public class main_21 {

    public static void main(String[] args) throws InterruptedException {
        ThreadDomain21 td = new ThreadDomain21();
        MyThread21_0 mt0 = new MyThread21_0(td);
        MyThread21_1 mt1 = new MyThread21_1(td);
        mt0.start();
        Thread.sleep(1000);
        mt1.start();
    }

}
