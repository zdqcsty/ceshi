package com.example.ceshi.test.test2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author LiYang
 * @ClassName FileNumberReader
 * @Description 从多行文件中，读取固定分隔的数字的工具类
 * @date 2019/11/12 15:50
 */
public class FileNumberReader implements Iterator<Long> {

    //数据源文件的路径
    private String filePath;

    //读取是否结束
    private boolean isFinished;

    //FileReader类，用于读取文件
    private FileReader fileReader;

    //BufferedReader类，用于逐行读取文件
    private BufferedReader bufferedReader;

    //读入一行内容，转化的数字字符串List
    private List<String> numberList;

    //当前读入的数字文件行内容
    private String currentLine;

    //当前提取的数字的numberList的下标
    private int currentIndex;

    //数据源文件中数字的分隔符
    private String delimiter;

    /**
     * FileNumberReader类的构造方法
     *
     * @param filePath  数据源文件路径
     * @param delimiter 文件中数字的分隔符
     * @throws FileNotFoundException
     */
    public FileNumberReader(String filePath, String delimiter) throws FileNotFoundException {
        this.filePath = filePath;
        this.isFinished = false;
        this.fileReader = new FileReader(filePath);
        this.bufferedReader = new BufferedReader(this.fileReader);
        this.numberList = new ArrayList<>();
        this.delimiter = delimiter;
    }

    /**
     * 根据isFinished，判断是否还有下一个数字
     *
     * @return 是否还有下一个数字
     */
    @Override
    public boolean hasNext() {
        return !isFinished;
    }

    /**
     * 返回文件中下一个数字
     *
     * @return 下一个数字
     */
    @Override
    public Long next() {
        try {
            //如果当前的numberList读完了，则清空numberList
            if (currentIndex > numberList.size() - 1) {
                numberList.clear();
            }

            //如果numberList被清空或是初始状态
            if (numberList.size() == 0) {
                //读入一行内容
                currentLine = bufferedReader.readLine();

                //如果读入的内容为null，证明文件读到末尾了
                if (currentLine == null) {
                    //迭代结束
                    isFinished = true;

                    //关闭输入流
                    bufferedReader.close();
                    fileReader.close();

                    //返回null，表示该文件已读完
                    return null;
                }

                //如果文件没有读完，则将当前的数据行按分隔符弄成numberList
                numberList = new ArrayList<>(Arrays.asList(currentLine.split(delimiter)));

                //下标置为0，从第一个开始
                currentIndex = 0;
            }

            //返回当前numberList的currentIndex下标的数字
            return Long.parseLong(numberList.get(currentIndex++));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //出异常返回null
        return null;
    }

    /**
     * 测试文件数字读取工具类
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        /**
         * 数字源文件路径，该文件内容如下：
         * 334 6544 255 6711
         * 34655 3 512 343
         * 6545 63774 34782 82098
         * 77 394
         *
         * 大家可以改为自己的文件路径
         */
        String filePath = "E:\\tmp\\test.txt";

        //该文件数字分隔符为空格，大家可以改为自己的分隔符，注意是正则表达式
        String delimiter = " ";

        //创建文件数字阅读工具类实例
        FileNumberReader fileNumberReader = new FileNumberReader(filePath, delimiter);

        //迭代该类实例，通过该类实例的next()方法取到下一个数字
        while (fileNumberReader.hasNext()) {

            //最后一个输出不是数字，是null，这就证明文件读完了
            //可以作为判断的依据
            System.out.print(fileNumberReader.next() + "  ");
        }
    }

}
