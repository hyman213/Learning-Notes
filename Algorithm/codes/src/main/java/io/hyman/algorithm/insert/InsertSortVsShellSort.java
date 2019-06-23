package io.hyman.algorithm.insert;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 18:04
 * @version： 1.0.0
 */
public class InsertSortVsShellSort {

    public static void main(String[] args) {
        testInsertSort();
        testShellSort();
    }

    private static void testInsertSort() {
        int[] list = new int[10000];
        for (int i = 0; i < 10000; i++) {
            list[i] = (int) (Math.random() * 100000);
        }
        // 直接插入排序
        long start = System.currentTimeMillis();
        InsertSort.insertSort(list);
        long end = System.currentTimeMillis();
        System.out.println("直接插入排序耗费的时间：" + (end - start));
    }

    private static void testShellSort() {
        int[] list = new int[10000];
        for (int i = 0; i < 10000; i++) {
            list[i] = (int) (Math.random() * 100000);
        }
        // 直接插入排序
        long start = System.currentTimeMillis();
        ShellSort.shellSort(list);
        long end = System.currentTimeMillis();
        System.out.println("Shell插入排序耗费的时间：" + (end - start));
    }

}
