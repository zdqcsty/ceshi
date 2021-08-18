package com.example.ceshi.test.test2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @author LiYang
 * @ClassName FileNumberWriter
 * @Description 将外部排序的有序数字写入文件的工具类
 * @date 2019/11/12 16:16
 */
public class FileNumberWriter {

    //写入的文件的路径（包含自定义的文件名）
    private String filePath;

    //数字分隔符
    private String delimiter;

    //每行写的数字的个数
    private int amountEachLine;

    //当前行数字的个数
    private int currentLineAmount;

    //待写入的文件类
    private File file;

    //用于写文件的FileWriter类
    private FileWriter fileWriter;

    //用于写文件的BufferedWriter类
    private BufferedWriter bufferedWriter;

    /**
     * 写数字的文件工具类
     * @param filePath 写入的文件的路径（包含自定义的文件名）
     * @param delimiter 数字分隔符
     * @param amountEachLine 每行写的数字的个数
     * @throws IOException
     */
    public FileNumberWriter(String filePath, String delimiter, int amountEachLine) throws IOException {
        this.filePath = filePath;
        this.delimiter = delimiter;
        this.amountEachLine = amountEachLine;
        this.fileWriter = new FileWriter(filePath);
        this.bufferedWriter = new BufferedWriter(fileWriter);

        //实例化该文件
        this.file = new File(filePath);

        //文件不存在，则创建
        if (!file.exists()){
            file.createNewFile();
        }
    }

    /**
     * 向指定文件写入数字（注意，最后一行会多写一个分隔符）
     * @param number 准备要写入的数字
     * @throws IOException
     */
    public void writeNumber(Long number) throws IOException {
        //当前行的第几个数字自增
        currentLineAmount ++;

        //如果已经超过了当前行的最大数量
        if (currentLineAmount > amountEachLine){

            //换一行
            bufferedWriter.newLine();

            //重置当前行的数字的第几个
            currentLineAmount = 1;
        }

        //如果已经是当前行的最后一个数字（写了这个数字就要换行了）
        if (currentLineAmount == amountEachLine){
            //只写入当前数字
            bufferedWriter.write(String.valueOf(number));

            //如果这个数字写了还不需要换行
        } else {
            //写入当前数字，以及分隔符
            bufferedWriter.write(number + delimiter);
        }
    }

    /**
     * 当有序数字写完后，调用该方法，关闭各种输出流
     * @throws IOException
     */
    public void writeFinish() throws IOException {
        bufferedWriter.close();
        fileWriter.close();
    }

    /**
     * 测试文件数字写入工具类
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //写入的文件的路径（包含文件名）
        String filePath = "C:\\Users\\Administrator\\Desktop\\write_number.txt";

        //为了美观，分隔符为英文逗号和空格
        String delimiter = ", ";

        //每行写4个数字
        int amountEachLine = 4;

        //FileNumberWriter工具类实例
        FileNumberWriter fileNumberWriter = new FileNumberWriter(filePath, delimiter, amountEachLine);

        //随机数类
        Random random = new Random();

        //写入30个一千万以内的随机整数
        for (int i = 1; i <= 30; i++) {
            //调用方法，写入数字（注意，最后一行有可能多一个分隔符）
            fileNumberWriter.writeNumber((long)random.nextInt(10000000));
        }

        //写完调用writeFinish()方法，关闭输出流
        fileNumberWriter.writeFinish();
    }

}