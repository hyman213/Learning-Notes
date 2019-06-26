package io.hyman.datastructure.linearlist;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/26 21:56
 * @version： 1.0.0
 */
public class TestMyLinkedList {

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();

        Node node1 = new Node("ShangHai", null);
        Node node2 = new Node("Guangzhou", null);
        Node node3 = new Node("Hangzhou", null);

        // 依次插入到最前面
        linkedList.insert(node1, 0);
        linkedList.insert(node2, 0);
        linkedList.insert(node3, 0);

        linkedList.showAll();

        // 删除节点1
        linkedList.delete(1);

        System.out.println("删除后的数据");
        linkedList.showAll();

        System.out.println("size = " + linkedList.size());
    }

}
