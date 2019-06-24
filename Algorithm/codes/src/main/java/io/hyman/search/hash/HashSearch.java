package io.hyman.search.hash;


import java.util.Arrays;
import java.util.Scanner;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/24 22:42
 * @version： 1.0.0
 */
public class HashSearch {

    static int hashLength = 7;
    static int[] hashTable = new int[hashLength];

    static int[] list = new int[]{25, 65, 14, 23, 54, 26, 28};

    public static void main(String[] args) {

        System.out.println("*******哈希查找*******");

        // 创建哈希表
        for (int i = 0; i < list.length; i++) {
            insert(hashTable, list[i]);
        }
        System.out.println(Arrays.toString(list));

        while (true) {
            System.out.println("输入要查找的数：");
            int data = new Scanner(System.in).nextInt();
            if (data == -1) System.exit(0);
            int result = search(hashTable, data);
            if (result == -1) {
                System.out.println("对不起，没有找到！");
            } else {
                System.out.println("数据的位置是：" + result);
            }
        }

    }

    /**
     * 哈希表插入
     *
     * @param hashTable
     * @param data
     */
    private static void insert(int[] hashTable, int data) {
        // 哈希函数，除留余数法
        int hashAddress = modhash(hashTable, data);

        // 如果不为0，则说明发生冲突
        while (hashTable[hashAddress] != 0) {
            // 利用 开放定址法 解决冲突
            hashAddress = (++hashAddress) % hashTable.length;
        }

        // 将待插入值存入字典中
        hashTable[hashAddress] = data;
    }

    /**
     * 方法：哈希表查找
     *
     * @param hashTable
     * @param data
     * @return
     */
    public static int search(int[] hashTable, int data) {
        // 哈希函数，除留余数法
        int hashAddress = modhash(hashTable, data);

        while (hashTable[hashAddress] != data) {
            // 利用 开放定址法 解决冲突
            hashAddress = (++hashAddress) % hashTable.length;
            if (hashTable[hashAddress] == 0 || hashAddress == modhash(hashTable, data)) {
                return -1;
            }
        }
        // 查找成功，返回下标
        return hashAddress;
    }

    /**
     * 构建哈希函数：除留余数法
     *
     * @param hashTable
     * @param data
     * @return
     */
    public static int modhash(int[] hashTable, int data) {
        return data % hashTable.length;
    }

}
