package io.hyman.algorithm;

import java.util.Arrays;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/21 22:25
 * @version： 1.0.0
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] list = new int[]{23, 43, 12, 5, 47, 2, 99, 34};
        System.out.println("排序前，" + Arrays.toString(list));
        buddleSort(list);
        System.out.println("排序后，" + Arrays.toString(list));
    }

    public static void buddleSort(int[] list) {
        int len = list.length;
        int temp;
        // 做多少轮排序（最多length-1轮）
        for (int i = 0; i < len - 1; i++) {
            // 每一轮比较多少个, 每一轮都将右侧一位放在正确位置
            for (int j = 0; j < len - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }

}
