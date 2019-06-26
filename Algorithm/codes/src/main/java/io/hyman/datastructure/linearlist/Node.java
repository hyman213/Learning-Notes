package io.hyman.datastructure.linearlist;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/26 21:35
 * @version： 1.0.0
 */
public class Node {

    // 数据域
    Object element;

    // 地址域
    Node next;

    public Node(Object element, Node next) {
        this.element = element;
        this.next = next;
    }

    public Object getElement() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
