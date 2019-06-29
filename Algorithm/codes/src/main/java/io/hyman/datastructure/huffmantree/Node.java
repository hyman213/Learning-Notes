package io.hyman.datastructure.huffmantree;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/29 17:13
 * @version： 1.0.0
 */
public class Node implements Comparable<Node> {

    private int value;

    private Node leftChild;

    private Node rightChild;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }


    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
