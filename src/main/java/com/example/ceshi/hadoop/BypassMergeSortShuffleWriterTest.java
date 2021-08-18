package com.example.ceshi.hadoop;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class BypassMergeSortShuffleWriterTest {

    static final String URL = "E:\\study_workSpace\\ceshi\\src\\main\\resources\\demoaaa.txt";

    public static void main(String[] args) throws Exception {
//        write();
//        read();
//        combineFiles();
        writeDiffFile();
    }

    public static void write() throws Exception {
        DataOutputStream ds = new DataOutputStream(new FileOutputStream(URL, true));
        ds.writeInt(12);
        ds.writeInt(24);
        ds.writeInt(36);
        ds.close();
    }

    public static void read() throws Exception {
        DataInputStream dsi = new DataInputStream(new FileInputStream(URL));
        int a;

        while (dsi.read() != -1) {
            int i = dsi.readInt();
            System.out.println(i);
        }
    }

    public static void writeDiffFile() throws Exception {
        String outputUrl = "E:\\tmp\\zzz.txt";
        DataInputStream dsi = new DataInputStream(new FileInputStream(outputUrl));
        long aaa = dsi.readLong();
        long bbb = dsi.readLong();
        long ccc = dsi.readLong();

        System.out.println(aaa);
        System.out.println(bbb);
        System.out.println(ccc);
    }


    public static void combineFiles() throws Exception {

        String url = "E:\\tmp";

        String outputUrl = "E:\\tmp\\zzz.txt";

        FileOutputStream output = new FileOutputStream(new File(outputUrl));

        long aaa = IOUtils.copy(new FileInputStream(new File(url + File.separator + "aaa.txt")), output);
        long bbb = IOUtils.copy(new FileInputStream(new File(url + File.separator + "bbb.txt")), output);
        long ccc = IOUtils.copy(new FileInputStream(new File(url + File.separator + "ccc.txt")), output);

        Long[] arrLong = new Long[3];
        arrLong[0] = aaa;
        arrLong[1] = bbb;
        arrLong[2] = ccc;

        System.out.println(aaa);
        System.out.println(bbb);
        System.out.println(ccc);

        DataOutputStream ds = new DataOutputStream(new FileOutputStream(outputUrl, true));
        ds.writeLong(arrLong[0]);
        ds.writeLong(arrLong[1]);
        ds.writeLong(arrLong[2]);
        ds.close();

    }
}
