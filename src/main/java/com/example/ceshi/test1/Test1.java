package com.example.ceshi.test1;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class Test1 {

    public static void main(String[] args) throws IOException {

/*
        String cp="/test/test.csv";


        String filePath = Test1.class.getResource(cp).getFile();

        File file=new File(filePath);
*/

        //路径问题
        Test1.class.getResourceAsStream("/test/test.csv");

        InputStream in = Test1.class.getResourceAsStream("/test/test.csv");


        String s1 = IOUtils.toString(in);

        System.out.println(s1);

//        String s = br.readLine();
//
//        System.out.println(s);

//        br.close();

    }


}



