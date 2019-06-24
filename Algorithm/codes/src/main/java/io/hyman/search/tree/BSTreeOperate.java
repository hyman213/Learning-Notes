package io.hyman.search.tree;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/24 21:12
 * @version： 1.0.0
 */
public class BSTreeOperate {

    // 树的根节点
    public BSTreeNode root;
    // 记录树节点的个数
    public int size;

    /**
     * 查找节点
     */
    public boolean search(BSTreeNode bsTreeNode, int key) {
        // 遍历完没有找到，查找失败
        if (bsTreeNode == null) {
            return false;
        }
        // 要查找的元素为当前节点，查找成功
        if (key == bsTreeNode.data) {
            return true;
        }
        // 继续去当前节点的左子树中查找，否则去当前节点的右子树中查找
        if (key < bsTreeNode.data) {
            return search(bsTreeNode.left, key);
        } else {
            return search(bsTreeNode.right, key);
        }
    }

    /**
     * 创建二叉排序树
     *
     * @param list
     * @return
     */
    public BSTreeNode create(int[] list) {
        for (int i = 0; i < list.length; i++) {
            insert(list[i]);
        }
        return root;
    }

    public void insert(int data) {
        insert(new BSTreeNode(data));
    }

    /**
     * 插入一个节点
     *
     * @param bsTreeNode
     */
    public void insert(BSTreeNode bsTreeNode) {
        // 如果是第一个节点
        if (root == null) {
            root = bsTreeNode;
            size++;
            return;
        }

        // 相比较的节点，根据current判断将bsTreeNode放在做节点还是右节点
        BSTreeNode current = root;
        while (true) {
            if (bsTreeNode.data <= current.data) {
                // 如果插入节点的值小于当前节点的值，说明该节点应插入到当前节点的左子树，而此时如果左子树为空，就直接设置当前节点的左子树为插入节点。
                if (current.left == null) {
                    current.left = bsTreeNode;
                    size++;
                    return;
                }
                current = current.left;// 当前节点设为左节点，再次比对
            } else {
                // 如果插入节点的值小于当前节点的值，说明该节点应插入到当前节点的左子树，而此时如果左子树为空，就直接设置当前节点的左子树为插入节点。
                if (current.right == null) {
                    current.right = bsTreeNode;
                    size++;
                    return;
                }
                current = current.right;// 当前节点设为右节点，再次比对
            }
        }
    }


    /**
     * 中序遍历
     *
     * @param bsTreeNode
     */
    public void LDR(BSTreeNode bsTreeNode) {
        if (bsTreeNode != null) {
            // 遍历左子树
            LDR(bsTreeNode.left);
            // 输出节点数据
            System.out.print(bsTreeNode.data + " ");
            // 遍历右子树
            LDR(bsTreeNode.right);
        }
    }

}
