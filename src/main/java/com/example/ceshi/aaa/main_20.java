package com.example.ceshi.aaa;

public class main_20 {

    public static void main(String[] args) throws Exception
    {
        ThreadDomain20 td = new ThreadDomain20();
        MyThread20_0 mt0 = new MyThread20_0(td);
        MyThread20_1 mt1 = new MyThread20_1(td);
        mt0.start();
        mt1.start();
    }

}
