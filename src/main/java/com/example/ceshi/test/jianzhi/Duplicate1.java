package com.example.ceshi.test.jianzhi;

public class Duplicate1 {

    /**
     * @param intArray    输入数组
     * @param duplicaiton 将首次找到的重复数字利用duplicaiton[0] = ?存入数组
     * @return 如果输入数组无效返回false，duplicaiton[0]=-1
     * @Author rex
     * @Date 2018/6/8 下午10:08
     * @Description 找出数组中重复的数字
     */
    public static boolean findDuplicate(int[] intArray, int[] duplicaiton) {
        // 杜绝数组为空
        if (intArray.length == 0) {
            duplicaiton[0] = -1;
            return false;
        }
        // 杜绝数组有非法数字
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] < 0 || intArray[i] > intArray.length - 1) {
                duplicaiton[0] = -1;
                return false;
            }
        }
        for (int i = 0; i < intArray.length; i++) {

            while (intArray[i] != i) {
                if (intArray[i] == intArray[intArray[i]]) {
                    duplicaiton[0] = intArray[i];
                    return true;
                }
                int temp = intArray[i];
                intArray[i] = intArray[temp];
                intArray[temp] = temp;
            }
        }
        duplicaiton[0] = -1;
        return false;

    }

}