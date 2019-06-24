package io.hyman.search.index;

import java.util.Arrays;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 22:45
 * @version： 1.0.0
 */
public class IndexSearch {

    // 主表
    static int[] mainList = new int[]{
            101, 102, 103, 104, 105, 0, 0, 0, 0, 0,
            201, 202, 203, 204, 0, 0, 0, 0, 0, 0,
            301, 302, 303, 0, 0, 0, 0, 0, 0, 0

    };

    // 索引表
    static IndexItem[] indexItemList = new IndexItem[]{
            new IndexItem(1, 0, 5), // 从0开始共5个
            new IndexItem(2, 10, 4),// 从10开始共4个
            new IndexItem(3, 20, 3),// 从20开始共3个
    };

    public static void main(String[] args) {
        System.out.println("********索引查找********");
        System.out.println("");
        System.out.println("原始数据：");
        System.out.println(Arrays.toString(mainList));

        int value = 106;
        System.out.println("插入数据：" + value);
        // 插入成功
        if (insert(value)) {
            System.out.println("插入后的主表：");
            System.out.println(Arrays.toString(mainList));
            System.out.println("元素" + value + "在列表中的位置为：" + indexSearch(value));
        }
    }

    /**
     * 索引查找算法
     *
     * @param key
     * @return
     */
    private static int indexSearch(int key) {
        IndexItem item = null;

        // 建立索引规则
        int index = key / 100;
        // 遍历索引表，找到对应的索引项
        for (int i = 0; i < indexItemList.length; i++) {
            //找到索引项
            if (indexItemList[i].getIndex() == index) {
                item = indexItemList[i];
                break;
            }
        }

        if (item == null) {
            return -1;
        }

        // 根据索引项，在主表中查找
        for (int i = item.getStart(); i < item.getStart() + item.getLength(); i++) {
            if (mainList[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 插入
     *
     * @param key
     * @return
     */
    private static boolean insert(int key) {
        IndexItem item = null;

        // 建立索引规则
        int index = key / 100;
        int i = 0;
        // 遍历索引表，找到对应的索引项
        for (i = 0; i < indexItemList.length; i++) {
            //找到索引项
            if (indexItemList[i].getIndex() == index) {
                item = indexItemList[i];
                break;
            }
        }

        if (item == null) {
            return false;
        }

        mainList[item.getStart() + item.getLength()] = key;
        indexItemList[i].setLength(indexItemList[i].getLength() + 1);
        return true;
    }

}
