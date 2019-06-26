package io.hyman.datastructure.linearlist;

/**
 * @Description: 线性表之顺序表
 * @author: Hyman
 * @date: 2019/06/26 20:55
 * @version： 1.0.0
 */
public class SequenceList {

    // 默认长度
    final int defaultSize = 10;

    // 最大长度
    int maxSize;

    // 当前长度
    int size;

    // 顺序表对象
    Object[] list;

    /**
     * 顺序表初始化
     *
     * @param size
     */
    private void init(int size) {
        this.size = 0;
        maxSize = size;
        list = new Object[size];
    }

    /**
     * 顺序表长度
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 插入元素
     *
     * @param index
     * @param object
     * @throws Exception
     */
    public void insert(int index, Object object) throws Exception {
        if (size == maxSize) {
            throw new Exception("顺序表已满，无法再插入数据");
        }
        if (index < 0 || index >= maxSize) {
            throw new Exception("插入元素的位置不合法");
        }
        // 从后往前操作
        for (int i = size - 1; i > index; i--) {
            list[i + 1] = list[i];
        }
        list[index] = object;
        size++;
    }

    /**
     * 删除元素
     *
     * @param index
     * @throws Exception
     */
    public void delete(int index) throws Exception {
        if (size == 0) {
            throw new Exception("顺序表为空，无法进行删除操作");
        }
        if (index < 0 || index >= maxSize) {
            throw new Exception("插入元素的位置不合法");
        }
        // 移动元素。 要从前往后操作
        for (int i = index; i < size - 1; i++) {
            list[index] = list[index - 1];
        }
        size--;
    }

    /**
     * 根据索引获取对象
     *
     * @param index
     * @return
     * @throws Exception
     */
    public Object get(int index) throws Exception {
        if (index < 0 || index > size - 1) {
            throw new Exception("查找元素的位置不合法");
        }
        return list[index];
    }

}
