package io.hyman.algorithm.insert;

import java.util.Arrays;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 16:59
 * @version： 1.0.0
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] list = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0};
        System.out.println("************堆排序************");
        System.out.println("排序前：" + Arrays.toString(list));
        insertSort(list);
        System.out.println("排序后：" + Arrays.toString(list));
    }

    /**
     * 直接插入排序算法
     */
    public static void insertSort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        for (int i = 1; i < arr.length; i++) { // 从第二个数开始, 认为第一个数已排好序
            int k = i - 1;
            int temp = arr[i]; // 先取出待插入数据保存，因为向后移位过程中会把覆盖掉待插入数
            while (k >= 0 && arr[k] > temp) {
                arr[k + 1] = arr[k]; // 将大于temp的数向后移
                k--;
            }
            arr[k + 1] = temp; // 将temp插入到正确位置上
        }
    }

}
