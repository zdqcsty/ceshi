package com.example.ceshi.aaa;

/**
 * 那么如果线程1访问了一个对象方法A的synchronized块，线程2对于同一对象同步方法B的访问应该是会被阻塞的，
 * 因为线程2访问同一对象的同步方法B的时候将会尝试去获取这个对象的对象锁，但这个锁却在线程1这里。
 */

public class MyThread20_main {
    public static void main(String[] args) throws Exception {
        ThreadDomain20 td = new ThreadDomain20();
        MyThread20_0 mt0 = new MyThread20_0(td);
        MyThread20_1 mt1 = new MyThread20_1(td);
        mt0.start();
        mt1.start();
    }

}
