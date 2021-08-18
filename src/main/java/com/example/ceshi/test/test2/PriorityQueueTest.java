package com.example.ceshi.test.test2;

import java.util.PriorityQueue;

/**
 * @author LiYang
 * @ClassName PriorityQueueTest
 * @Description 优先队列的JavaAPI应用测试
 * @date 2019/11/12 17:03
 */
public class PriorityQueueTest {

    /**
     * 测试JavaAPI的优先队列PriorityQueue<>类
     *
     * @param args
     */
    public static void main(String[] args) {
        //实例化一个优先队列，保存Long型数据
        PriorityQueue<Long> priorityQueue = new PriorityQueue<>();

        //调用add()方法，将数字入队
        priorityQueue.add(7L);
        priorityQueue.add(19L);
        priorityQueue.add(13L);

        //调用peek()方法，返回优先队列中的最小元素
        System.out.println(priorityQueue.peek());

        //调用poll()方法，将优先队列中的最小元素出队，并返回
        System.out.println(priorityQueue.poll());

        //重复上述操作，共计查看和出队三次
        //上面的三个数字将从小到大依次出来
        //注意，add()，peek()，poll()方法可以随时调用
        //如果优先队列的元素全部被poll了，再poll和peek，返回null
        System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue.poll());

        //再往下，就是null了
        System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue.poll());
    }

}
