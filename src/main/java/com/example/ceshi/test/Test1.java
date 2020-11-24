package com.example.ceshi.test;


import java.util.HashMap;
import java.util.Map;

public class Test1 {

    public static void main(String[] args) {

        Map<String,String> map=new HashMap();

        map.put("aaa","bbb");

        final String aaa = map.get("aaa");

        System.out.println(aaa);
        aaa

    }

}
