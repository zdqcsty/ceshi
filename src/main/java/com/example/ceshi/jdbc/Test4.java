package com.example.ceshi.jdbc;

import java.util.concurrent.ConcurrentHashMap;

public class Test4 {

    public synchronized void getaaa(){
        System.out.println("hahah");
    }

    public synchronized void getbbb(){
        System.out.println("heiheihei");
        getaaa();
    }


    public static void main(String[] args) {

        System.out.println("caaa".hashCode() % 9);

    }


}
