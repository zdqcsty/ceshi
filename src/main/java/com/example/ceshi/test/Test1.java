package com.example.ceshi.test;

import java.util.Arrays;

public class Test1 {

    public static void main(String[] args) {
        int[] arr = {9, 2, 3, 1, 7, 23, 2};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int low, int high) {
        int i = low;
        int j = high;
        int flag = arr[low];

        while (i >= j) {
            return;
        }

        while (i < j) {
            while (i < j && arr[j] > flag) {
                j--;
            }
            while (i < j && arr[i] <= flag) {
                i++;
            }
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
        }

        arr[low] = arr[i];
        arr[i] = flag;

        quickSort(arr, low, i - 1);
        quickSort(arr, j + 1, high);
    }

}
