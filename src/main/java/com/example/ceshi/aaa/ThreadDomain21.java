package com.example.ceshi.aaa;

public class ThreadDomain21 {
    private String userNameParam;
    private String passwordParam;
    private static String anyString = new String();

    public void setUserNamePassword(String userName, String password) {
        try {
            synchronized (anyString) {
                System.out.println("线程名称为：" + Thread.currentThread().getName() +
                        "在 " + System.currentTimeMillis() + " 进入同步代码块");
                userNameParam = userName;
                Thread.sleep(3000);
                passwordParam = password;
                System.out.println("线程名称为：" + Thread.currentThread().getName() +
                        "在 " + System.currentTimeMillis() + " 离开同步代码块");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void demo() {
        System.out.println("jinru tongbufangfa --");
    }
}