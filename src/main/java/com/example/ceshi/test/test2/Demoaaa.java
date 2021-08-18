package com.example.ceshi.test.test2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Demoaaa {

    public static void main(String[] args) throws FileNotFoundException {

        Random random = new Random(10);

        PrintWriter pw = new PrintWriter("E:\\tmp\\data.dat");

        for (int i = 0; i < 1000000; i++) {
            pw.println(random.nextInt(1000000));
        }

        pw.close();
    }

}
