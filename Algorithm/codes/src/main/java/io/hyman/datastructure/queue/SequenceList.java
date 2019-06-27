package io.hyman.datastructure.queue;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/27 23:08
 * @version： 1.0.0
 */
public class SequenceList {
    // 默认的长度
    final int defaultSize = 10;
    // 最大长度
    int maxSize;
    // 当前长度
    int size;
    // 顺序表对象
    Object[] list;

    /**
     * 1、顺序表的初始化方法
     * 时间复杂度为O(1)
     *
     * @param size
     */
    private void init(int size) {
        this.size = 0;
        maxSize = size;
        list = new Object[size];
    }

    /**
     * 2、求顺序表的长度
     * 时间复杂度为O(1)
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 3、插入元素
     * 时间复杂度为O(n)
     *
     * @param index
     * @param obj
     * @throws Exception
     */
    public void insert(int index, Object obj) throws Exception {
        // 如果当前顺序表已满，那就不允许插入数据
        if (size == maxSize) {
            throw new Exception("顺序表已满，无法插入！");
        }
        // 插入元素的位置编号是否合法
        if (index < 0 || index > size) {
            throw new Exception("插入元素的位置编号不合法！");
        }
        // 移动元素。 要从后往前操作
        for (int i = size - 1; i >= index; i--) {
            list[i + 1] = list[i];
        }
        list[index] = obj;
        size++;
    }

    /**
     * 4、删除元素
     * 时间复杂度为O(n)
     *
     * @param index
     * @throws Exception
     */
    public void delete(int index) throws Exception {
        // 判断当前顺序表是否为空
        if (size == 0) {
            throw new Exception("顺序表为空，无法删除！");
        }
        // 删除元素的位置编号是否合法
        if (index < 0 || index > size - 1) {
            throw new Exception("删除元素的位置编号不合法！");
        }
        // 移动元素。 要从前往后操作
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        size--;
    }

    /**
     * 5、按序号查找元素
     * 时间复杂度为O(1)
     *
     * @param index
     * @return
     * @throws Exception
     */
    public Object get(int index) throws Exception {
        // 查找元素的位置编号是否合法
        if (index < 0 || index > size - 1) {
            throw new Exception("查找元素的位置编号不合法！");
        }
        return list[index];
    }

}
