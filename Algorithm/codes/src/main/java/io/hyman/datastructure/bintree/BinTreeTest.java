package io.hyman.datastructure.bintree;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/29 15:02
 * @version： 1.0.0
 */
public class BinTreeTest {

    public static void main(String[] args) throws Exception {
        System.out.println("*******链式存储的二叉树*******");
        // 创建根节点
        Node root = BinTree.createRoot(5);
        // 1、插入节点
        System.out.println("********插入节点*******");
        BinTree.insert(root);

        // 2、查找节点
        System.out.print("查找data为2的节点是否存在：");
        boolean result = BinTree.getNode(root, 2);
        System.out.println(result);

        // 3、获取二叉树的深度
        System.out.println("当前二叉树的深度为：" + BinTree.getLength(root));

        // 4、先序遍历
        System.out.print("先序遍历：");
        BinTree.DLR(root);
        System.out.println("");

        // 5、中序遍历
        System.out.print("中序遍历：");
        BinTree.LDR(root);
        System.out.println("");

        // 6、后序遍历
        System.out.print("后序遍历：");
        BinTree.LRD(root);
        System.out.println("");

        // 7、按层遍历
        System.out.print("按层遍历：");
        BinTree.length = 100;
        BinTree.traversalLevel(root);
        System.out.println("");
    }

}
