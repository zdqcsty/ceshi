package com.example.ceshi.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {

    public static void main(String[] args) {
        deleteByPath("E:\\demo");

    }

    public static boolean deleteByPath(String path) {
        File file = new File(path);
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File sonFile : files) {
                    System.out.println(sonFile.getPath());
                    deleteByPath(sonFile.getPath());
                }
                Files.delete(Paths.get(file.getPath()));
            }
            Files.delete(Paths.get(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
