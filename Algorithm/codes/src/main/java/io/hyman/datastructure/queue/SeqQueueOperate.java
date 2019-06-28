package io.hyman.datastructure.queue;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/28 22:53
 * @version： 1.0.0
 */
public class SeqQueueOperate {

    public SeqQueue init(int maxSize) {
        SeqQueue queue = new SeqQueue(maxSize);
        queue.head = 0;
        queue.rear = 0;
        return queue;
    }

    /**
     * 入队
     *
     * @param queue
     * @param object
     */
    public void enter(SeqQueue queue, Object object) {
        if (queue.rear >= queue.maxSize) {
            return;
        }
        queue.data[queue.rear] = object;
        queue.rear++;
    }

    /**
     * 出队
     *
     * @param queue
     * @return
     */
    public Object dequeue(SeqQueue queue) {
        if (queue.head == queue.rear) {
            return null;
        }
        Object object = queue.data[queue.head];
        queue.data[queue.head] = null;
        queue.head++;
        return object;
    }

    /**
     * 取队头
     *
     * @param queue
     * @return
     */
    public Object getHead(SeqQueue queue) {
        if (queue.head == queue.rear) {
            return null;
        }
        return queue.data[queue.head];
    }

    /**
     * 队长
     *
     * @param queue
     * @return
     */
    public int getLength(SeqQueue queue) {
        return queue.rear - queue.head;
    }

    public boolean isEmpty(SeqQueue queue) {
        return queue.head == queue.rear;
    }

    public boolean isFull(SeqQueue queue) {
        return queue.rear >= queue.maxSize;
    }


}
