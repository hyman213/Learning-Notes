package io.hyman.algorithm.selection;

import java.util.Arrays;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/22 22:20
 * @version： 1.0.0
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] list = {9, 1, 2, 5, 7, 4, 8, 6, 3, 5};
        System.out.println("************直接选择排序************");
        System.out.println("排序前：" + Arrays.toString(list));
        selecttionSort(list);
        System.out.println("排序后：" + Arrays.toString(list));
    }

    public static void selecttionSort(int[] list) {
        int len = list.length;
        for (int i = 0; i < len; i++) {
            // 初始下标为i的为最小值
            int base = i;
            // 找到最小值的下标赋值为base
            for (int j = i + 1; j < len; j++) {
                if (list[j] < list[base]) {
                    base = j;
                }
            }
            // 如果base!=i，说明下标为i的值不为最小值，交换base与i下标的值
            if (base != i) {
                swap(list, i, base);
            }
        }
    }

    /**
     * 交换数组中两个位置的元素
     */
    public static void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

}
