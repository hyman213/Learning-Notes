package io.hyman.datastructure.stack;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/27 22:35
 * @version： 1.0.0
 */
public class StackTest {

    public static void main(String[] args) {
        SeqStackOperation seqStackOperate = new SeqStackOperation();

        // 初始化一个栈，最大长度为5
        SeqStack seqStack = seqStackOperate.init(5);

        // 看看能否放进6个元素
        seqStackOperate.push(seqStack, 1);
        seqStackOperate.push(seqStack, 2);
        seqStackOperate.push(seqStack, 3);
        seqStackOperate.push(seqStack, 4);
        seqStackOperate.push(seqStack, 5);
        seqStackOperate.push(seqStack, 6);

        // 输出栈当前的长度
        int length = seqStackOperate.getLength(seqStack);
        System.out.println("栈当前的长度：" + length);
        System.out.println("");

        // 出栈
        Object obj = seqStackOperate.pop(seqStack);
        System.out.println("出栈的元素是 ---- " + obj);
        System.out.println("");

        // 输出栈当前的长度
        length = seqStackOperate.getLength(seqStack);
        System.out.println("栈当前的长度：" + length);
        System.out.println("");

        // 输出当前的栈顶元素
        obj = seqStackOperate.top(seqStack);
        System.out.println("当前的栈顶元素是 ---- " + obj);
        System.out.println("");
    }

}
