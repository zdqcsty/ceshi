//package com.example.ceshi;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Runner implements ApplicationRunner {
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        TestJdbc bean =  SpringUtil.getBean(TestJdbc.class);
//
//        bean.hiveDataSource();
//
//        System.out.println("初始化完成-------------");
//    }
//}