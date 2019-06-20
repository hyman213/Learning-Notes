package io.hyman.algorithm;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/19 22:58
 * @version： 1.0.0
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] list = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        System.out.println("************快速排序************");
        System.out.println("排序前：");
        display(list);
        System.out.println("排序过程：");
        quickSort(list, 0, list.length - 1);
        System.out.println("排序后：");
        display(list);
    }

    /**
     * 快速排序算法
     */
    public static void quickSort(int[] list, int left, int right) {
        if (left < right) {
            // 分割数组，找到分割点
            int point = partition(list, left, right);

            // 递归调用，对左子数组进行快速排序
            quickSort(list, left, point - 1);
            // 递归调用，对右子数组进行快速排序
            quickSort(list, point + 1, right);
        }
    }

    /**
     * 分割数组，找到分割点
     *
     * @param list
     * @param left
     * @param right
     * @return
     */
    public static int partition(int[] list, int left, int right) {
        // 数组left作为基准数
        int first = list[left];
        while (left < right) {
            // 从右往左找到比基准数小的数，然后将该数与基准数交换位置
            while (left < right && list[right] >= first) {
                right--;
            }
            swap(list, left, right);
            display(list);
            // 从左往右找到比基准数大的数，然后将该数与基准数交换位置
            while (left < right && list[left] <= first) {
                left++;
            }
            swap(list, left, right);
            display(list);
        }
        // 结束后基准数位于left位置
        return left;
    }

    /**
     * 交换数组中left和right位置的两个数
     *
     * @param list
     * @param left
     * @param right
     */
    public static void swap(int[] list, int left, int right) {
        int temp;
        if (list != null && list.length > 0) {
            temp = list[left];
            list[left] = list[right];
            list[right] = temp;
        }
    }

    /**
     * 遍历打印
     *
     * @param list
     */
    public static void display(int[] list) {
        for (int num : list) {
            System.out.print(num + " ");
        }
        System.out.println("");
    }

}
