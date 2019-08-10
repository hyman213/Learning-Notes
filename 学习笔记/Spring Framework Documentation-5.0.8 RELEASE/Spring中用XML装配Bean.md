# Spring中用XML装配Bean

## 基本配置

XML 配置是最原始最古老的 Bean 的装配方案，曾经我们的项目离不开它，而如今，我们却在慢慢的抛弃它，没办法，时代在进步，我们也要进步呀。为了能看懂前辈们写的代码，我们还是有必要来看一下如何通过 XML 来装配 Bean。

首先我们来创建一个普通的 Maven 工程（不用创建成 web 工程），创建成功之后，引入 Spring 相关的依赖，这里只要引入 spring-context 即可，这里我同时引入了lombok，如下：

```java
<dependencies>
	<dependency>
    	<groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
    </dependency>

    <dependency>
         <groupId>org.projectlombok</groupId>
         <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

创建成功之后，我们再来创建一个 Book 类，如下：

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    private Integer id;

    private String name;

    private Double price;

}
```

然后再在 resources 目录下创建一个 spring-beans.xml 文件，作为 Spring 的配置文件，然后在里边配置一个 Book bean，如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--<bean id="book" class="io.hyman.spring.bean.Book" scope="prototype"/>-->
    <bean id="book" class="io.hyman.spring.bean.Book"/>

</beans>
```

我们在 class 属性中配置类的全路径，id 则表示 bean 的名称，也可以通过 name 属性来指定 bean 的名称

```java
public static void main(String[] args) {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring-beans.xml");
    // 通过id(推荐)
    Book book = (Book) applicationContext.getBean("book");
    // 通过类型(单例)
    Book boo = applicationContext.getBean(Book.class);
    // 默认为单例，在spring-beans.xml中加入scope=prototype后，为false
    System.out.println(boo == book);// true
}
```

此时我们获取到的 Bean 中的属性全部为 null，没有值，这是因为我们在配置的时候没有给属性指定值。

在配置 Bean 时，给 Bean 指定相关的属性值，我们有几种不同的方式

### 构造方法指定

前提是我们为 Book 类提供一个有参构造方法（大家在创建有参构造方法时，一定记得再顺手加一个无参构造方法）

```xml
<bean id="book" class="io.hyman.spring.bean.Book">
    <constructor-arg name="id" value="1"/>
    <constructor-arg name="name" value="三国演义"/>
    <constructor-arg name="price" value="54.00"/>
</bean>
```

也可以使用下标来描述参数的顺序，注意如果使用下标，保证下标与构造方法中参数的顺序

```xml
<bean id="book2" class="io.hyman.spring.bean.Book">
    <constructor-arg index="0" value="1"/>
    <constructor-arg index="2" value="54.00"/>
    <constructor-arg index="1" value="三国演义"/>
</bean>
```

### 通过属性注入

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
</bean>
```

### p名称空间注入

p 名称空间本质上还是通过属性注入的，只不过写法有些差异，p 名称空间注入方式如下

此时xml文件中需导入`xmlns:p="http://www.springframework.org/schema/p`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


<bean id="book4" class="io.hyman.spring.bean.Book"
      p:id="4" p:name="红楼梦" p:price="62.00"/>
```

> 以上三种不同的属性注入方式，我给大家演示的都是注入基本数据类型，如果注入的是一个对象的话，只需要通过 ref 属性来指明对象的引用即可。

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
    <property name="author" ref="author3"/>
</bean>

<bean id="author3" class="io.hyman.spring.bean.Person">
    <property name="id" value="3"/>
    <property name="name" value="LuoGuanZhong"/>
    <property name="dynasty" value="Ming Dynasty"/>
</bean>
```

## 特殊属性注入

除了这些基本属性之外，还有一些特殊属性，例如集合、数组、map 等。我们分别来看。

### 集合/数组

```java
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

}
```

集合List属性注入时，可以通过 array 节点注入值

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
    <property name="author" ref="author3"/>
    <property name="hlodings">
        <array>
            <value>BeiJing</value>
            <value>ShangHai</value>
        </array>
    </property>
</bean>

<bean id="author3" class="io.hyman.spring.bean.Person">
    <property name="id" value="3"/>
    <property name="name" value="LuoGuanZhong"/>
    <property name="dynasty" value="Ming Dynasty"/>
</bean>
```

也可以通过 list 节点注入值：

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
    <property name="author" ref="author3"/>
    <property name="hlodings">
        <list>
            <value>Beijing</value>
            <value>ShangHai</value>
        </list>
    </property>
</bean>
```

还有一个可能大家使用比较少的方式，就是通过 utils:list 来创建集合属性，然后配置到 Book 属性中去，即可

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
    <property name="author" ref="author3"/>
    <property name="hlodings" ref="holdings"/>
</bean>

<utils:list id="holdings">
    <value>ShenZhen</value>
    <value>HangZhou</value>
</utils:list>
```

### map

map 的注入也有几种不同的方式，可以通过属性指定，也可以通过 utils 来搞定，先来看第一种：

```java
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
```

在 xml 文件中通过如下方式指定属性值：

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
    <property name="author" ref="author3"/>
    <property name="hlodings" ref="holdings"/>
    <property name="descMap">
        <map>
            <entry key="ch01" value="张天师祈禳瘟疫 洪太尉误走妖魔"/>
            <entry key="ch02" value="王教头私走延安府 九纹龙大闹史家村"/>
        </map>
    </property>
</bean>
```

也可以通过 utils 来指定 map 的值，如下：

```xml
<bean id="book3" class="io.hyman.spring.bean.Book">
    <property name="id" value="3"/>
    <property name="name" value="水浒传"/>
    <property name="price" value="55.00"/>
    <property name="author" ref="author3"/>
    <property name="hlodings" ref="holdings"/>
    <property name="descMap" ref="descMap3"/>
</bean>

<utils:map id="descMap3">
    <entry key="ch01" value="张天师祈禳瘟疫 洪太尉误走妖魔"/>
    <entry key="ch02" value="王教头私走延安府 九纹龙大闹史家村"/>
</utils:map>
```

### properties

properties 属性也是一样的配置方案。既可以通过 props 属性指定，也可以通过 utils 来指定

只需将map中的`map`替换为`prop`即可

## 工厂方法装配

工厂方法装配可以分为静态工厂和实例工厂两种方式，我们分别来看。

### 静态工厂

静态工厂方法装配需要我们先创建一个静态工厂方法

```java
public class BookFactory {

    public static Book getInstance() {
        return new Book();
    }

}
```

然后在 XML 文件中装配：

```xml
<bean class="io.hyman.spring.bean.BookFactory" id="book5" factory-method="getInstance"/>
```

### 实例工厂

实例工厂方法则是指工厂方法是一个普通方法，不是静态的，像下面这样：

```java
public class BookFactory2 {

    public Book getInstance() {
        return new Book();
    }

}
```

然后在 XML 文件中，我们需要首先配置 BookFactory2 的实例，然后才能调用实例中的方法获取 Book 对象，如下：

```xml
<bean class="io.hyman.spring.bean.BookFactory2" id="bookFactory2"/>
<bean class="io.hyman.spring.bean.Book" id="book6" factory-bean="bookFactory2" factory-method="getInstance"/>

```

工厂方法装配的价值在哪里呢？

例如 Druid 中的 DataSource 对象，通过 `DruidDataSourceBuilder.create().build()` 方法来构建，如果我们想在 XML 中做这个配置，显然不太容易，此时就可以使用工厂方法装配了。

## The bean definition

| Property                 | Explained in…                                                |
| :----------------------- | :----------------------------------------------------------- |
| class                    | [Instantiating beans](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-class) |
| name                     | [Naming beans](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-beanname) |
| scope                    | [Bean scopes](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-scopes) |
| constructor arguments    | [Dependency Injection](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-collaborators) |
| properties               | [Dependency Injection](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-collaborators) |
| autowiring mode          | [Autowiring collaborators](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-autowire) |
| lazy-initialization mode | [Lazy-initialized beans](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-lazy-init) |
| initialization method    | [Initialization callbacks](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-lifecycle-initializingbean) |
| destruction method       | [Destruction callbacks](https://docs.spring.io/spring/docs/5.0.8.RELEASE/spring-framework-reference/core.html#beans-factory-lifecycle-disposablebean) |

