package io.hyman.spring.bean;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: Hyman
 * @date: 2019/08/04 09:56
 * @version： 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    private Integer id;

    private String name;

    private Double price;

    private Person author;

    private List<String> hlodings;

    private Map<String, String> descMap;

}
