package io.hyman.datastructure.stack;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/27 22:01
 * @version： 1.0.0
 */
public class SeqStackOperation {

    /**
     * 初始化
     *
     * @param length
     * @return
     */
    public SeqStack init(int length) {
        SeqStack seqStack = new SeqStack(length);
        seqStack.top = -1;
        return seqStack;
    }

    /**
     * 进栈
     *
     * @param seqStack
     * @param object
     */
    public void push(SeqStack seqStack, Object object) {
        // 检查栈是否满
        if (seqStack.top == seqStack.data.length - 1) {
            return;
        }
        seqStack.top++;
        seqStack.data[seqStack.top] = object;
    }

    /**
     * 出栈
     *
     * @param seqStack
     * @return
     */
    public Object pop(SeqStack seqStack) {
        if (seqStack.top == -1) {
            return null;
        }
        Object obj = seqStack.data[seqStack.top];
        seqStack.data[seqStack.top] = null;
        seqStack.top--;
        return obj;
    }

    /**
     * 获取栈顶元素
     *
     * @param seqStack
     * @return
     */
    public Object top(SeqStack seqStack) {
        if (seqStack.top == -1) {
            return null;
        }
        return seqStack.data[seqStack.top];
    }

    /**
     * 判断是否栈空
     *
     * @param seqStack
     * @return
     */
    public boolean isEmpty(SeqStack seqStack) {
        return seqStack.top == -1;
    }

    /**
     * 判断是否栈满
     *
     * @param seqStack
     * @return
     */
    public boolean isFull(SeqStack seqStack) {
        return seqStack.top == (seqStack.data.length - 1);
    }

    public int getLength(SeqStack seqStack) {
        return seqStack.top + 1;
    }

}
