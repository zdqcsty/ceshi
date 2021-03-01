package com.example.ceshi.test.jianzhi;

public class 替换空格 {

    public static void main(String[] args) {

        final String we_are_happy = replaceBlank("we are happy");
        System.out.println(we_are_happy);

    }


    //将字符串替换成02%
    public static String replaceBlank(String str) {

        char[] chars = str.toCharArray();
        char[] result = new char[chars.length * 3];

        int size = 0;
        for (char c : chars) {
            if (' ' == c) {
                result[size++] = '0';
                result[size++] = '2';
                result[size++] = '%';
            } else {
                result[size++] = c;
            }
        }

        return new String(result, 0, size);
    }
}
