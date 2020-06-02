package com.example.ceshi.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Test4 {

    public static void main(String[] args) throws FileNotFoundException {

        PrintWriter pw=new PrintWriter("F:\\data.csv");
        for(int i=0;i<10000000;i++){
            String str="demovavjddacdacacadcdcahjdvhfdbvfsbvfhfihifewho\n";
            pw.write(str);
        }
        pw.close();
    }
}
