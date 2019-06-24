package io.hyman.search.index;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/06/23 22:48
 * @version： 1.0.0
 */
public class IndexItem {

    // 索引项在主表的关键字
    private int index;

    // 块内的第1个元素在主表中的位置
    private int start;

    // 块的长度
    private int length;

    public IndexItem(int index, int start, int length) {
        this.index = index;
        this.start = start;
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
