package io.hyman.spring.bean;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/08/04 11:19
 * @version： 1.0.0
 */
public class BookFactory {

    public static Book getInstance() {
        return new Book();
    }

}
