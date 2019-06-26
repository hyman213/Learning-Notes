package io.hyman.datastructure.linearlist;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/26 21:39
 * @version： 1.0.0
 */
public class MyLinkedList {

    // 头结点
    private Node headNode;

    // 用来遍历链表的临时节点
    private Node tempNode;

    // 初始化方法
    public MyLinkedList() {
        // 初始化一个节点，这个节点的地址域为null
        Node node = new Node("Beijing", null);
        // 头结点不存储数据，数据域为null, 地址域存第一个节点的地址
        headNode = new Node(null, node);
    }

    /**
     * 插入节点
     *
     * @param newNode  需要插入的新节点
     * @param position 从0到链表长度，注意头结点不算进链表的长度，从头结点后面的节点开始算起，position为0
     */
    public void insert(Node newNode, int position) {
        tempNode = headNode;
        int i = 0;
        while (i < position) {
            // 遍历链表，将tempNode赋值为position-1位置处上的Node
            tempNode = tempNode.next;
            i++;
        }
        newNode.next = tempNode.next;
        tempNode.next = newNode;
    }

    /**
     * 遍历打印所有节点值
     */
    public void showAll() {
        tempNode = headNode.next;
        while (tempNode.next != null) {
            System.out.println(tempNode.element);
            tempNode = tempNode.next;
        }
        System.out.println(tempNode.element);
    }

    /**
     * 删除指定位置的节点
     *
     * @param position
     */
    public void delete(int position) {
        // 用tempNode从头开始查找position
        tempNode = headNode;
        int i = 0;
        while (i < position) {
            tempNode = tempNode.next;
            i++;
        }
        // 此时tempNode指向position上一个节点
        tempNode.next = tempNode.next.next;
    }

    /**
     * 查找节点
     *
     * @param position
     */
    public Node find(int position) {
        // 用tempNode从头开始查找position
        tempNode = headNode;
        int i = 0;
        while (i < position) {
            tempNode = tempNode.next;
            i++;
        }
        // 此时tempNode指向position上一个节点
        return tempNode.next;
    }

    /**
     * 获取链表长度
     *
     * @return
     */
    public int size() {
        tempNode = headNode.next;
        int size = 0;
        while (tempNode.next != null) {
            tempNode = tempNode.next;
            size++;
        }
        size++;
        return size;
    }

}
