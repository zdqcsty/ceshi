package com.example.ceshi.aaa;

/**
 * 看到对于serviceMethodB()方法synchronized块的访问必须等到对于serviceMethodA()方法synchronized块的访问结束之后。
 * 那其实这个例子，我们也可以得出一个结论：synchronized块获得的是一个对象锁，换句话说，synchronized块锁定的是整个对象。
 */

public class MyThread19_main {
    public static void main(String[] args) {
        ThreadDomain19 td = new ThreadDomain19();
        MyThread19_0 mt0 = new MyThread19_0(td);
        MyThread19_1 mt1 = new MyThread19_1(td);
        mt0.start();
        mt1.start();
    }

}