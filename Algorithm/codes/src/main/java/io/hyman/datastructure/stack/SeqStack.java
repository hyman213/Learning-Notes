package io.hyman.datastructure.stack;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/27 21:52
 * @version： 1.0.0
 */
public class SeqStack {

    // 栈顶指针
    public int top;

    // 使用数组存放元素
    public Object[] data;

    public SeqStack(int length) {
        data = new Object[length];
    }

}
