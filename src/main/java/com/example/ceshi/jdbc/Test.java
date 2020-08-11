package com.example.ceshi.jdbc;

import com.example.ceshi.test.Demo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {


        PrintWriter pw=new PrintWriter("E:\\data.csv");

        for (int i=0;i<3000000;i++){
            pw.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        pw.close();




    }

}
