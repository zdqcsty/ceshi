package com.example.ceshi.bingfa;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Runner implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random rand= new Random(47);
    private final CountDownLatch start_latch;
    private final CountDownLatch end_latch;

    public Runner(CountDownLatch start_latch, CountDownLatch end_latch) {
        this.start_latch = start_latch;
        this.end_latch = end_latch;
    }

    @Override
    public void run() {
        try {
            start_latch.await();  //所有运动员都在准备状态中，等待教练释放开始门
            try {
                doWork();  //每个人跑步的时间不同
                end_latch.countDown();  //跑完后，告诉教练跑完了
            } catch (InterruptedException e) {
                System.out.println("Interrupted Runner" + id);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted Runner" + id);
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        System.out.println(this + "completed");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d", id);
    }
}

class Coach implements Runnable {
    private final CountDownLatch start_latch;
    private final CountDownLatch end_latch;

    public Coach(CountDownLatch start_latch, CountDownLatch end_latch) {
        this.start_latch = start_latch;
        this.end_latch = end_latch;
    }

    @Override
    public void run() {
        start_latch.countDown();  //教练释放了开始门，运动员们都开始跑
        System.out.println("Coach say: Ready!!!!  Go!!!!");
        try {
            end_latch.await();  //当结束门的count down减为0时，教练宣布所有人都跑完了。
            System.out.println("All runner passed the end point");
        } catch (InterruptedException ex) {
            System.out.println(this + " interrupted");
        }
    }
}

public class TestRunner {
    private static final int SIZE = 10;
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new Runner(startGate, endGate));
        }
        exec.execute(new Coach(startGate, endGate));
        exec.shutdown();
    }
}
