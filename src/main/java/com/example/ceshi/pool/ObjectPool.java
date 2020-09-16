package com.example.ceshi.pool;

import org.slf4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 作者：liliang
 * 日期：2020/4/29
 */
public abstract class ObjectPool {

    static Logger logger = org.slf4j.LoggerFactory.getLogger(ObjectPool.class);
    private static long expirationTime;

    private static HashMap<String, ConcurrentHashMap<String, ConnectionBean>> locked;
    private static HashMap<String, ConcurrentLinkedQueue<ConnectionBean>> unlocked;

    public ObjectPool() {
        if (locked == null) {
            locked = new HashMap<>();
        }
        if (unlocked == null) {
            unlocked = new HashMap<>();
        }
        expirationTime = 30 * 60 * 1000; // 30 minute 过期
    }

    protected abstract ConnectionBean create();

    public abstract boolean validate(ConnectionBean o);

    public abstract void expire(ConnectionBean o);

    public ConnectionBean get(String useName) {
        synchronized (locked) {
            String key = Thread.currentThread().getName() + useName;
            System.out.println("连接池的key:"+key);
            logger.info("【POOL】 lock the LOCKED map, the useName is {}", useName);
            long now = System.currentTimeMillis();
            ConcurrentLinkedQueue<ConnectionBean> beans;
            if (!unlocked.isEmpty()) {
                beans = unlocked.get(useName);
                if (beans != null) {
                    while (!beans.isEmpty()) {
                        // 获取头元素，并在资源队列中删除头元素
                        ConnectionBean bean = beans.poll();
                        // 如果头元素的时间过期了，那么关闭连接
                        if (now - bean.getUpdateTime() > expirationTime) {
                            logger.info("【POOL】 the connection is out of time ,bean time is {}", bean.getUpdateTime());
                            // 释放
                            expire(bean);
                            bean = null;
                        } else {
                            if (validate(bean)) {
                                logger.info("【POOL】 get the connection from poll and the useName is {}",
                                        useName);
                                bean.setUpdateTime(now);
                                // 放入锁定的队列中并返回 锁定队列需要
                                locked.get(useName).put(key, bean);
                                return bean;
                            } else {
                                // 如果链接已经关闭
                                unlocked.remove(useName);
                                expire(bean);
                                bean = null;
                            }
                        }
                    }
                }
            }
            // 由于unlock可能为空，所以初始化对应的useName
            unlocked.put(useName, new ConcurrentLinkedQueue<ConnectionBean>());
            System.out.println("unlocked===========:"+unlocked.toString());
            // 如果没有链接则新建一个操作
            ConnectionBean bean = create();
                logger.info("【POOL】 the pool could not provide a connection, so create it,useName is {}",
                        useName);
                if (locked.get(useName) == null) {
                    logger.info("【POOL】 the useName in pool is null, create a new Map in LOCKED, useName is {}",
                            useName);
                    locked.put(useName, new ConcurrentHashMap<String, ConnectionBean>());
                }
                locked.get(useName).put(key, bean);
            System.out.println("locked==========:"+locked.toString());
            return bean;
        }
    }


    public void release(String useName) {
        synchronized (locked) {
            String key = Thread.currentThread().getName() + useName;
            ConcurrentHashMap<String, ConnectionBean> connectionBeans = locked.get(useName);
            ConnectionBean bean = connectionBeans.get(key);
            connectionBeans.remove(key);
            bean.setUpdateTime(System.currentTimeMillis());
            unlocked.get(useName).add(bean);
            System.out.println("......................................................" + Thread.currentThread().getName());
        }
    }
}
