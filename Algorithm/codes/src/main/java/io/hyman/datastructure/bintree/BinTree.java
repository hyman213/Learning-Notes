package io.hyman.datastructure.bintree;

import java.util.Scanner;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/29 11:29
 * @version： 1.0.0
 */
public class BinTree {

    public static int length;

    /**
     * 生成根节点
     *
     * @param rootElement
     * @return
     */
    public static Node createRoot(int rootElement) {
        Node root = new Node();
        root.data = rootElement;
        return root;
    }

    /**
     * 插入节点
     *
     * @param root 二叉树的根节点
     * @return 返回二叉树的根节点
     * @throws Exception
     */
    public static Node insert(Node root) throws Exception {
        while (true) {
            System.out.println("请输入待插入节点的数据：");
            Node node = new Node();
            node.data = new Scanner(System.in).nextInt();

            // 获取父节点数据
            System.out.println("请输入它(待插入节点) 的父节点：");
            int parentNodeData = new Scanner(System.in).nextInt();

            // 确定插入方向
            System.out.println("请确定要插入到父节点的：1 左侧, 2 右侧");
            int direction = new Scanner(System.in).nextInt();

            // 插入节点
            root = insertNode(root, node, parentNodeData, direction);

            if (root == null) {
                System.out.println("未找到父节点，请重新输入！");
                continue;
            }
            System.out.println("插入成功，是否继续? 1继续，2退出");

            if (new Scanner(System.in).nextInt() == 1)
                continue;
            else
                break; // 退出循环
        }
        return root;
    }


    /**
     * 插入节点
     *
     * @param root           二叉树的根节点
     * @param node           待插入节点
     * @param parentNodeData 待插入节点的父节点
     * @param direction      插入到左边还是右边
     * @return
     */
    public static Node insertNode(Node root, Node node, int parentNodeData, int direction) throws Exception {
        if (root == null)
            return null;

        if (root.data == parentNodeData) {
            switch (direction) {
                case 1:
                    if (root.left != null) {
                        throw new Exception("左节点已存在，不能插入！");
                    } else {
                        root.left = node;
                    }
                    break;
                case 2:
                    if (root.right != null) {
                        throw new Exception("右节点已存在，不能插入！");
                    } else {
                        root.right = node;
                    }
                    break;
            }
        }

        // 向左子树查找父节点(递归)
        insertNode(root.left, node, parentNodeData, direction);
        // 向右子树查找父节点(递归)
        insertNode(root.right, node, parentNodeData, direction);
        return root;
    }


    /**
     * 查找节点
     *
     * @param root
     * @param data
     * @return
     */
    public static boolean getNode(Node root, int data) {
        if (root == null) return false;
        if (root.data == data) {
            return true;
        }
        boolean res1 = getNode(root.left, data);
        boolean res2 = getNode(root.right, data);
        return res1 || res2;
    }

    /**
     * 获取二叉树深度
     * 思路：分别递归左子树和右子树，取长度较大的那一个作为整个树的深度
     *
     * @param root
     * @return
     */
    public static int getLength(Node root) {
        if (root == null) return 0;
        int leftLength, rightLength;
        // 递归左子树节点
        leftLength = getLength(root.left);
        // 递归右子树节点
        rightLength = getLength(root.right);
        if (leftLength > rightLength) {
            return leftLength + 1;
        } else {
            return rightLength + 1;
        }
    }

    /**
     * 先序遍历
     * @param root
     */
    public static void DLR(Node root) {
        if (root == null) return;
        // 输出节点的值
        System.out.println(root.data);

        // 递归遍历左子树
        DLR(root.left);
        // 递归遍历右子树
        DLR(root.right);
    }

    /**
     * 中序遍历
     * 思路：先遍历左子树，再访问根节点，最后遍历右子树
     *
     * @param root 根节点
     */
    public static void LDR(Node root) {
        if (root == null)
            return;

        // 遍历左子树
        LDR(root.left);

        // 输出节点的值
        System.out.print(root.data + " ");

        // 遍历右子树
        LDR(root.right);
    }

    /**
     * 后序遍历
     * 思路：先遍历左子树，再遍历右子树，最后访问根节点
     *
     * @param root 根节点
     */
    public static void LRD(Node root) {
        if (root == null)
            return;

        // 遍历左子树
        LRD(root.left);

        // 遍历右子树
        LRD(root.right);

        // 输出节点的值
        System.out.print(root.data + " ");
    }

    /**
     * 按层遍历
     * 思路：从上到下，从左到右遍历节点
     *
     * @param root 根节点
     */
    public static void traversalLevel(Node root) {
        if (root == null)
            return;

        int head = 0;
        int tail = 0;

        // 申请保存空间
        Node[] nodeList = new Node[length];

        // 将当前二叉树保存到数组中
        nodeList[tail] = root;

        // 计算tail的位置
        tail = (tail + 1) % length; // 除留余数法

        while (head != tail) {
            Node tempNode = nodeList[head];

            // 计算head的位置
            head = (head + 1) % length;

            // 输出节点的值
            System.out.print(tempNode.data + " ");

            // 如果左子树不为空，则将左子树保存到数组的tail位置
            if (tempNode.left != null) {
                nodeList[tail] = tempNode.left;
                // 重新计算tail的位置
                tail = (tail + 1) % length;
            }

            // 如果右子树不为空，则将右子树保存到数组的tail位置
            if (tempNode.right != null) {
                nodeList[tail] = tempNode.right;
                // 重新计算tail的位置
                tail = (tail + 1) % length;
            }
        }
    }


}
