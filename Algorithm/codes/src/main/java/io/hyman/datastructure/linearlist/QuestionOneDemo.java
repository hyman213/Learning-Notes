package io.hyman.datastructure.linearlist;

import java.util.Stack;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/29 23:05
 * @version： 1.0.0
 */
public class QuestionOneDemo {

    public void reversePrint(Node head) {
        if (head == null) return;
        Stack<Node> stack = new Stack<>();

        Node tempNode = head;
        // 从头节点开始遍历链表，将除了头节点之外的所有节点压栈
        while (tempNode.getNext() != null) {
            tempNode = tempNode.getNext();
            stack.push(tempNode);
        }

        while (stack.size() > 0) {
            // 出栈
            Node node = stack.pop();
            System.out.println(node.getElement());
        }
    }

}
