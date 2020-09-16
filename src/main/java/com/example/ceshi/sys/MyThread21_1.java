package com.example.ceshi.sys;

public class MyThread21_1 extends Thread
{
    private ThreadDomain21 td;

    public MyThread21_1(ThreadDomain21 td)
    {
        this.td = td;
    }

    public void run()
    {
        td.setUserNamePassword("B", "B");
    }


    public static void main(String[] args)
    {
        ThreadDomain21 td = new ThreadDomain21();
        MyThread21_0 mt0 = new MyThread21_0(td);
        MyThread21_1 mt1 = new MyThread21_1(td);
        mt0.start();
        mt1.start();
    }
}
