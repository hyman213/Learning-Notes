package io.hyman.algorithm.exchange;

import java.util.Random;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/21 22:50
 * @version： 1.0.0
 */
public class BubbleVsQuick {

    public static void main(String[] args) {
        System.out.println("冒泡排序开始");
        testBubble();
        System.out.println("\n快速排序-挖坑法，开始");
        testQuick();
        System.out.println("\n快速排序-左右指针法，开始");
        testQuick2();
    }

    private static void testQuick() {
        int[] list = new int[20000];
        for (int i = 0; i < list.length; i++) {
            list[i] = new Random().nextInt(100000);
        }
        long start = System.currentTimeMillis();
        QuickSort.quickSort(list, 0, list.length - 1);
        System.out.printf("快速排序(挖坑法)耗时： %d ms", (System.currentTimeMillis() - start));
    }

    private static void testQuick2() {
        int[] list = new int[10000];
        for (int i = 0; i < list.length; i++) {
            list[i] = new Random().nextInt(100000);
        }
        long start = System.currentTimeMillis();
        QuickSort.quickSort2(list, 0, list.length - 1);
        System.out.printf("快速排序(左右指针法)耗时： %d ms", (System.currentTimeMillis() - start));
    }

    private static void testBubble() {
        int[] list = new int[10000];
        for (int i = 0; i < list.length; i++) {
            list[i] = new Random().nextInt(100000);
        }
        long start = System.currentTimeMillis();
        BubbleSort.buddleSort(list);
        System.out.printf("冒泡排序耗时： %d ms", (System.currentTimeMillis() - start));
    }

}
