package io.hyman.spring.util;

import io.hyman.spring.bean.Book;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/08/04 09:59
 * @version： 1.0.0
 */
public class SpringBeansTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring-beans.xml");
//        Book book = (Book) applicationContext.getBean("book");
//        Book book2 = (Book) applicationContext.getBean("book2");
        Book book3 = (Book) applicationContext.getBean("book3");
        Book book5 = (Book) applicationContext.getBean("book5");
        Book book6 = (Book) applicationContext.getBean("book6");
//        Book author4 = (Book) applicationContext.getBean("author4");
//        Book book4 = (Book) applicationContext.getBean("book4");
//        Book boo = applicationContext.getBean(Book.class);
//        System.out.println(book.toString());
//        System.out.println(book2);
//        System.out.println(book3);
//        System.out.println(book4);
        System.out.println(book5);
        System.out.println(book6);

//        System.out.println(boo.toString());
//        System.out.println(boo == book);// true
    }

}
