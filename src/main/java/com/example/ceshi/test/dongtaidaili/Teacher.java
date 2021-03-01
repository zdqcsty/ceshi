package com.example.ceshi.test.dongtaidaili;

public class Teacher implements People {

    @Override
    public String work(String name) {
        System.out.println("老师教书育人...");
        return "教书";
    }
}