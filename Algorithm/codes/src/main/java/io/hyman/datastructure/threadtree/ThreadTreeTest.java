package io.hyman.datastructure.threadtree;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/29 16:23
 * @version： 1.0.0
 */
public class ThreadTreeTest {

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ThreadTree threadTree = new ThreadTree(data); // 创建普通二叉树
        System.out.println("中序递归遍历二叉树");
        threadTree.inList(threadTree.getRoot()); // 中序递归遍历二叉树
        System.out.println();

        threadTree.inThread(threadTree.getRoot()); // 采用中序遍历将二叉树线索化
        System.out.println("中序遍历线索化二叉树");
        threadTree.inThreadList(threadTree.getRoot()); // 中序遍历线索化二叉树
    }

}
