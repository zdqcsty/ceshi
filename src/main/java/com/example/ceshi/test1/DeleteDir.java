package com.example.ceshi.test1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteDir {

    public static void main(String[] args) throws IOException {
        boolean b = deleteDir("E:\\user\\zgh");
        System.out.println(b);
    }

    public static boolean deleteDir(String path) throws IOException {
        File file=new File(path);
//        return file.exists();
         return true;
//        return file.delete();
//        return true;
    }


}
