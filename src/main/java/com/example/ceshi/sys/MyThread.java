package com.example.ceshi.sys;

public class MyThread implements Runnable {
    private Trans trans;
    private int num;

    public MyThread(Trans trans, int num) {
        this.trans = trans;
        this.num = num;
        System.out.println("hahah");
    }

    public void run() {
        while (true)
        {
            trans.printNum(num);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
