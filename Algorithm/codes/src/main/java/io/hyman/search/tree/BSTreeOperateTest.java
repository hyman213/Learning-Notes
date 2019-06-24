package io.hyman.search.tree;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/24 21:33
 * @version： 1.0.0
 */
public class BSTreeOperateTest {

    public static void main(String[] args) {
        BSTreeOperate bsTreeOperate = new BSTreeOperate();
        int[] list = new int[]{50, 30, 70, 10, 40, 90, 80};
        System.out.println("*********创建二叉排序树*********");
        BSTreeNode bsTreeNode = bsTreeOperate.create(list);
        System.out.println("中序遍历原始的数据：");
        bsTreeOperate.LDR(bsTreeNode);

        System.out.println("********查找节点*******");
        System.out.println("元素20是否在树中：" + bsTreeOperate.search(bsTreeNode, 20));

        System.out.println("********插入节点*******");
        System.out.println("将元素20插入到树中");
        bsTreeOperate.insert(20);
        System.out.println("中序遍历：");
        bsTreeOperate.LDR(bsTreeNode);

        System.out.println("");
        System.out.println("********查找节点*******");
        System.out.println("元素20是否在树中：" + bsTreeOperate.search(bsTreeNode, 20));
    }

}
