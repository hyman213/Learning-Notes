package io.hyman.algorithm.merge;

import java.util.Arrays;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 18:23
 * @version： 1.0.0
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] list = {1, 3, 4, 5, 2, 6, 9, 7, 8, 0};
        System.out.println("************堆排序************");
        System.out.println("排序前：" + Arrays.toString(list));
        mergeSort(list, 0, list.length - 1);
        System.out.println("排序后：" + Arrays.toString(list));
    }


    public static int[] mergeSort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            mergeSort(a, low, mid);
            mergeSort(a, mid + 1, high);
            //左右归并
            merge(a, low, mid, high);
        }
        return a;
    }

    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int x = 0; x < temp.length; x++) {
            a[x + low] = temp[x];
        }
    }

}
