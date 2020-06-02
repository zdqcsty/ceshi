package com.example.ceshi.test;

import java.io.File;

public class Test5 {

    public static void main(String[] args) throws Exception {

        File f = PackageUtil.createTempJar("E:\\study_workSpace\\ceshi\\src\\main\\java\\com\\example\\ceshi\\test");

        System.out.println(f.getPath());

//	while(true){
//
//	}
    }
}
