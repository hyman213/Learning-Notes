package io.hyman.datastructure.queue;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/28 23:09
 * @version： 1.0.0
 */
public class SeqQueueTest {

    public static void main(String[] args) {
        SeqQueueOperate seqQueueOperate = new SeqQueueOperate();
        // 最大容量设置为5
        int maxSize = 5;
        SeqQueue queue = seqQueueOperate.init(maxSize);
        System.out.println("队列的最大容量是：" + queue.maxSize);

        // 当前队列的长度
        System.out.println("当前队列的长度是：" + seqQueueOperate.getLength(queue));
        System.out.println("");

        System.out.println("===========入队start ===========");
        System.out.println("插入6个元素试试");
        seqQueueOperate.enter(queue, 1);
        seqQueueOperate.enter(queue, 2);
        seqQueueOperate.enter(queue, 3);
        seqQueueOperate.enter(queue, 4);
        seqQueueOperate.enter(queue, 5);
        seqQueueOperate.enter(queue, 6);
        System.out.println("===========入队end =============");
        // 当前队列的长度
        System.out.println("当前队列的长度是：" + seqQueueOperate.getLength(queue));
        System.out.println("");

        // 出队
        System.out.println("===========出队start ===========");
        Object obj = seqQueueOperate.dequeue(queue);
        System.out.println("出队的元素是：" + obj);
        System.out.println("===========出队end =============");
        // 当前队列的长度
        System.out.println("当前队列的长度是：" + seqQueueOperate.getLength(queue));
        System.out.println("");

        System.out.println("------------------------------------");
        System.out.println("队头元素是：" + queue.data[queue.head]);
        System.out.println("队尾元素是：" + queue.data[queue.rear - 1]);
    }

}
