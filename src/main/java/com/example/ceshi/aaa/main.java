package com.example.ceshi.aaa;

public class main {
    public static void main(String[] args) {
        ThreadDomain19 td = new ThreadDomain19();
        MyThread19_0 mt0 = new MyThread19_0(td);
        MyThread19_1 mt1 = new MyThread19_1(td);
        mt0.start();
        mt1.start();
    }

}