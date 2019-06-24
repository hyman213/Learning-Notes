package io.hyman.search.binary;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 22:30
 * @version： 1.0.0
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] list = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        System.out.println("************二分查找************");
        int result = binarySearch(list, 40);
        if (result != -1) {
            System.out.println("40在列表中的位置是：" + result);
        } else {
            System.out.println("对不起，列表中不存在该元素！");
        }
    }

    /**
     * 二分查找算法
     *
     * @param elementArray
     * @param element
     * @return
     */
    public static int binarySearch(int[] elementArray, int element) {
        int low = 0;
        int high = elementArray.length - 1;

        while (low <= high) {
            int middle = (high + low) / 2;


            if (elementArray[middle] == element) { // 中间元素是否与给定值相等
                return middle;
            } else if (elementArray[middle] > element) { // 在中间元素的左半区查找
                high = middle - 1;
            } else { // 在中间元素的右半区查找
                low = middle + 1;
            }
        }
        return -1;
    }

}
