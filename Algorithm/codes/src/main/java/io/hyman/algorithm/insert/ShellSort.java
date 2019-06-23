package io.hyman.algorithm.insert;

import java.util.Arrays;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 16:59
 * @version： 1.0.0
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] list = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0};
        System.out.println("************堆排序************");
        System.out.println("排序前：" + Arrays.toString(list));
        shellSort(list);
        System.out.println("排序后：" + Arrays.toString(list));
    }

    /**
     * 希尔排序算法
     */
    public static void shellSort(int[] list) {
        // 取增量
        int gap = list.length / 2;

        while (gap >= 1) {
            for (int i = gap; i < list.length; i++) {
                int temp = list[i];
                int j;

                for (j = i - gap; j >= 0 && list[j] > temp; j = j - gap) {
                    list[j + gap] = list[j];
                }
                list[j + gap] = temp;
            }

            // 缩小增量
            gap = gap / 2;
        }
    }

//    /**
//     * Shell插入排序算法
//     */
//    public static void shellSort(int[] arr) {
//        //逐渐缩小步长。此处gap以每次除以2进行减少
//        for (int gap = arr.length / 2; gap >= 1; gap /= 2) {
//            //直接排序法
//            for (int i = gap; i < arr.length; i++) {
//                int temp = arr[i];
//                while (i - gap >= 0 && arr[i - gap] > temp) {
//                    arr[i] = arr[i - gap];
//                    i = i - gap;
//                }
//                arr[i] = temp;
//            }
//        }
//    }

}
