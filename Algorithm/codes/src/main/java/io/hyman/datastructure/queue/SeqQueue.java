package io.hyman.datastructure.queue;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/27 22:55
 * @version： 1.0.0
 */
public class SeqQueue {

    // 保存数据
    public Object[] data;

    // 头指针
    public int head;

    // 尾指针
    public int rear;

    // 最大容量
    public int maxSize;

    public SeqQueue(int maxSize) {
        this.maxSize = maxSize;
        data = new Object[maxSize];
    }

}
