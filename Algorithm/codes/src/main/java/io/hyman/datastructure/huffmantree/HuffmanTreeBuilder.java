package io.hyman.datastructure.huffmantree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/29 17:15
 * @version： 1.0.0
 */
public class HuffmanTreeBuilder {

    public static void main(String[] args) {
        List<Node> nodes = Arrays.asList(
                new Node(1),
                new Node(3),
                new Node(5),
                new Node(7)
        );
        Node node = build(nodes);
        printTree(node);
    }

    /**
     * 构造哈夫曼树
     *
     * @param nodes
     * @return
     */
    private static Node build(List<Node> nodes) {
        nodes = new ArrayList<>(nodes);
        Collections.sort(nodes);
        while (nodes.size() > 1) {
            createAndReplace(nodes);
        }
        return nodes.get(0);
    }

    /**
     * 组合两个权值最小节点，并在节点列表中用它们的父节点替换它们
     *
     * @param nodes
     */
    private static void createAndReplace(List<Node> nodes) {
        Node left = nodes.get(0);
        Node right = nodes.get(1);
        Node parent = new Node(left.getValue() + right.getValue());
        parent.setLeftChild(left);
        parent.setRightChild(right);
        nodes.remove(0);
        nodes.remove(0);
        nodes.add(parent);
        Collections.sort(nodes);
    }

    private static void printTree(Node node) {
        Node left = null;
        Node right = null;
        if (node != null) {
            System.out.print(node.getValue());
            left = node.getLeftChild();
            right = node.getRightChild();
            System.out.println("(" + (left != null ? left.getValue() : " ") + "," + (right != null ? right.getValue() : " ") + ")");
        }
        if (left != null) {
            printTree(left);
        }
        if (right != null) {
            printTree(right);
        }
    }

}
