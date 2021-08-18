package com.example.ceshi.test;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author liming
 * @date 2020/9/3
 * @description
 */
public class TopKDemo {

    //模拟海量数据的文件
    private final File file = new File("file" + File.separator + "topkdata.txt");
    private final Random random = new Random();
    private final PriorityQueue<Long> priorityQueue = new PriorityQueue<>(10);

    public void computeTopK() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                addToTopKQueue(Long.valueOf(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
            Long target;
            while ((target = priorityQueue.poll()) != null) {
                System.out.println("target = " + target);
            }
        }
    }

    /**
     * init方法仅运行一次即可，是为准备模拟数据
     */
    public void init() {
        long start = System.currentTimeMillis();
        System.out.println("init");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            //先用100万数据，多了电脑可能受不了
            for (int i = 0; i < 1000000; i++) {
                fileWriter.write(String.valueOf(random.nextLong()) + System.lineSeparator());
            }
            //写入10个接近long的最大值的数，便于取出是验证正确结果
            for (int i = 0; i < 10; i++) {
                fileWriter.write(String.valueOf(Long.MAX_VALUE - i) + System.lineSeparator());
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("用时：" + (System.currentTimeMillis() - start));
        }
    }

    public void addToTopKQueue(Long target) {
        if (priorityQueue.size() < 10) {
            priorityQueue.add(target);
        } else {
            Long head = priorityQueue.peek();
            if (target > head) {
                priorityQueue.poll();
                priorityQueue.add(target);
            }
        }
    }

}