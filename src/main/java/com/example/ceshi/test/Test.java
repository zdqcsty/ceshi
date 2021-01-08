package com.example.ceshi.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class Test {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.load(new FileReader(new File("src/main/resources/demo.properties")));
        for (String str : props.stringPropertyNames()) {
            String property = props.getProperty(str);
            System.out.println(property);
        }
    }
}
