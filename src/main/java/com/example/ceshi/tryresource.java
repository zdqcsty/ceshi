package com.example.ceshi;

import java.io.BufferedReader;
import java.io.FileReader;

public class tryresource {

    public static void main(String[] args) {

        try (FileReader fr = new FileReader("E:\\data.csv");
             BufferedReader br = new BufferedReader(fr)) {

            System.out.println(br.readLine());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
