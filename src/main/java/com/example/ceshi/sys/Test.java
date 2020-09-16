package com.example.ceshi.sys;

public class Test {


    public static void main(String[] args) {

        Trans t = new Trans();
        Trans t1 = new Trans();
        Thread a = new Thread(new MyThread(t, 1));
        Thread b = new Thread(new MyThread(t1, 2));

        a.start();
        b.start();

    }

}
