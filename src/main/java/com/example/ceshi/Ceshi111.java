package com.example.ceshi;

import com.zaxxer.hikari.HikariPoolMXBean;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

public class Ceshi111 {

    public static void main(String[] args) throws MalformedObjectNameException {

        int a = 18;
        int b = 23;
        try {
            String[] arg1 = new String[] { "python", "/home/hadoop/zgh/ceshi.py", String.valueOf(a), String.valueOf(b) };
            Process proc = Runtime.getRuntime().exec(arg1);// 执行py文件
            BufferedInputStream bis = new  BufferedInputStream(proc.getInputStream());



            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
