package com.example.ceshi.aaa;

/**
 * 1、当A线程访问同一对象的synchronized代码块的时候，B线程依然可以访问对象方法中其余非synchronized块的部分，第一部分的执行结果证明了这一点
 * 2、当A线程进入同一对象的synchronized代码块的时候，B线程如果要访问这段synchronized块，那么访问将会被阻塞，第二部分的执行结果证明了这一点
 * 所以，从执行效率的角度考虑，有时候我们未必要把整个方法都加上synchronized，而是可以采取synchronized块的方式，
 * 对会引起线程安全问题的那一部分代码进行synchronized就可以了。
 */
public class MyThread18_main {
    public static void main(String[] args) {

        ThreadDomain18 td = new ThreadDomain18();
        MyThread18 mt0 = new MyThread18(td);
        MyThread18 mt1 = new MyThread18(td);
        mt0.start();
        mt1.start();
    }
}