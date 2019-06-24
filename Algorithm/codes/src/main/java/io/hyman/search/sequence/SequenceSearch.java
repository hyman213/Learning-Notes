package io.hyman.search.sequence;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 22:17
 * @version： 1.0.0
 */
public class SequenceSearch {

    public static void main(String[] args) {
        int[] list = {90, 10, 20, 50, 70, 40, 80, 60, 30, 52};
        System.out.println("************顺序查找************");
        int result = sequenceSearch(list, 40);
        if (result != -1) {
            System.out.println("40在列表中的位置是：" + result);
        } else {
            System.out.println("对不起，列表中不存在该元素！");
        }
    }

    public static int sequenceSearch(int[] elementArray, int element) {
        for (int i = 0, len = elementArray.length; i < len; i++) {
            if (elementArray[i] == element) {
                return i;
            }
        }
        return -1;
    }

}
