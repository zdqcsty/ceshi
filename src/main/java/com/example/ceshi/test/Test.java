package com.example.ceshi.test;

public class Test {

    private Test() {
    }

    public static Test getInstance() {
        return Demo.test;
    }

    private static class Demo {
        private static final Test test = new Test();
    }
}
