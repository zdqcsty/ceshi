package com.example.ceshi.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test4 {

    private static final Logger logger = LoggerFactory.getLogger(Test4.class);

    public static void main(String[] args) {
        logger.info("aaa");
        logger.error("bbb");

        String driverEngine="hiveserver2";

        if ("hiveserver2".equalsIgnoreCase(driverEngine)) {

            System.out.println("hahah");

        }


    }
}
