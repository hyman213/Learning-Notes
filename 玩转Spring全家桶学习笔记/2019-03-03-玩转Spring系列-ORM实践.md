---
title: 玩转Spring系列-ORM实践
date: 2019-03-03 18:03:13
category: "玩转Spring"
tags: Spring
---

# O/R Mapping实践

## 认识Spring Data JPA

### [Hibernate](http://hibernate.org/orm/)

一款开源的对象关系映射(Object / Relational Mapping)框架

JPA(Java Persistence API)为对象关系映射提供了一种基于POJO的持久化模型，简化数据持久化代码的开发工作，为Java社区屏蔽不同持久化API的差异

2006年，JPA 1.0作为JSR 220的一部分正式发布。
JSR是Java Specification Requests的缩写，意思是Java 规范提案。是指向JCP(Java Community Process)提出新增一个标准化技术规范的正式请求。任何人都可以提交JSR，以向Java平台增添新的API和服务。JSR已成为Java界的一个重要标准。


## 定义JPA的实体对象

### 常用JPA注解

** 实体 **
* @Entity、 @MappedSuperclass(标注父类)
* @Table(name)

** 主键 **
* @Id
    - @GenaratedValue(strategy, generator)
    - @SequenceGenerator(name, sequenceName)

** 映射 **
* @Column(name, nullable, length, insertable, updatable)
* @JoinTable(name)、 @JoinColumn(name)

** 关系 **
* @OneToOne、 @OneToMany、 @ManyToOne、 @ManyToMany
* @OrderBy


## SpringBucks

### 主要实体：咖啡、订单、顾客、服务员、咖啡师

![image](玩转Spring系列-ORM实践/spring0301.png)

![image](玩转Spring系列-ORM实践/spring0302.png)

![image](玩转Spring系列-ORM实践/spring0303.png)

### 实体定义

#### 引入依赖

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.joda</groupId>
            <artifactId>joda-money</artifactId>
            <version>1.0.1</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.jadira.usertype/usertype.core -->
        <dependency>
            <groupId>org.jadira.usertype</groupId>
            <artifactId>usertype.core</artifactId>
            <version>6.0.1.GA</version>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.22</version>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

#### BaseEntity

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createtime;

    @UpdateTimestamp
    private Date updatetime;

}
```

#### Coffee

```java
@Entity
@Table(name = "T_COFFEE")
@Builder
@Data
@ToString(callSuper = true) // 如果不加callsuper，toString不会有父类的属性
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {

    private String name;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;

}
```

#### CoffeeOrder

```java
@Entity
@Table(name = "T_ORDER")
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String customer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}
```

#### OrderState

```java
public enum OrderState {
    INTI, PAID, BREWING, BREWED, TAKEN, CANCELLED
}
```


## Spring Data JPA操作数据库

### Repository

@EnableJpaRepository

Repository<T, ID>接口

* CrudRepository<T, ID>
* PagingAndSortingRepository<T, ID>
* JpaRepository<T, ID>

### 定义查询

#### 根据方法名定义查询

* find..By... / read...By... / query...By... / get...By...
* count...By...
* ...OrderBy...[Asc / Desc]
* And / Or / IgnoreCase
* Top / First / Distinct

#### 分页查询

* PagingAndSortingRepository<T, ID>
* Pageable / Sort
* Slice<T> / Page<T>

```java
@NoRepositoryBean
public interface BaseRepository<T, Long> extends PagingAndSortingRepository<T, Long> {
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}
```

```java
public interface CoffeeRepository extends BaseRepository<Coffee, Long> {
}

public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);

    List<CoffeeOrder> findByItems_Name(String name);
}
```

```java
@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SpingbucksApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpingbucksApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
        findOrders();
    }

    private void findOrders() {
        coffeeRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(coffee -> log.info("Loading {}", coffee));

        List<CoffeeOrder> list = orderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

        list.forEach(o -> {
            log.info("Order {}", o.getId());
            o.getItems().forEach(i -> log.info(" Item {}", i));
        });

        list = orderRepository.findByItems_Name("latte");
        log.info("findByItems_Name: {}", getJoinedOrderId(list));

    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream().map(o -> o.getId().toString()).collect(Collectors.joining(","));
    }

    private void initOrders() {
        Coffee espresso = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        coffeeRepository.save(espresso);
        log.info("Coffee: {}", espresso);

        Coffee latte = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        coffeeRepository.save(latte);
        log.info("Coffee: {}", latte);

        CoffeeOrder order = CoffeeOrder.builder().customer("Li Lei")
                .items(Collections.singletonList(espresso))
                .state(OrderState.INTI).build();
        orderRepository.save(order);
        log.info("Order: {}", order);

        order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Arrays.asList(espresso, latte))
                .state(OrderState.INTI)
                .build();
        orderRepository.save(order);
        log.info("Order: {}", order);
    }
```



## Spring Data JPA的Repository从接口变成Bean

### 需要重看

### Repository Bean 是如何创建的

**JpaRepositoriesRegister**
* 激活了@EnableJpaRepositories
* 返回了JpaRepositoryConfigExtension

**RepositoryBeanDeﬁnitionRegistrarSupport.registerBeanDeﬁnitions**
* 注册 Repository Bean（类型是 JpaRepositoryFactoryBean ）

**RepositoryConﬁgurationExtensionSupport.getRepositoryConﬁgurations**
* 取得 Repository 配置

**JpaRepositoryFactory.getTargetRepository**
* 创建了Repository 


### 接⼝中的方法是如何被解释的

**RepositoryFactorySupport.getRepository 添加了了Advice**
* DefaultMethodInvokingMethodInterceptor
* QueryExecutorMethodInterceptor 

**AbstractJpaQuery.execute 执⾏行行具体的查询**

**语法解析在 Part 中**


## MyBatis操作数据库

### 认识MyBatis

MyBatis是一款优秀的持久层框架，支持定制SQL、存储过程和高级映射。
在Spring中使用MyBatis: MyBatis Spring Adapter; MyBatis Spring-Boot-Starter


```xml
mybatis.mapper-locations = classpath*:mapper/**/*.xml
mybatis.type-aliases-package = 类型别名的包名
mybatis.type-handlers-package = TypeHandler扫描包名
mybatis.configuration.map-underscore-to-camel-case = true
```

### Mapper的定义与扫描

@MapperScan配置扫描位置
@Mapper定义接口
映射的定义--xml与注解

Talk is cheap, show me the code

```java
@Mapper
public interface CoffeeMapper {

    @Insert("insert into t_coffee(name, price, create_time, update_time) " +
            "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    Long save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
            //map-underscore-to-camel-case = true 可以实现一样的效果
    })
    Coffee findById(@Param("id") Long id);
}
```

## MyBatis工具-MyBatis Generator

[官方](http://www.mybatis.org/generator/index.html)

### 运行方式

* 命令行
* Maven Plugin
* Eclipse Plugin
* Java程序
* Ant Task

### 配置MyBatis Generator(配置文件有顺序)

**generatorConfiguration**
**context**
* jdbcConnection
* javaModelGenerator
* sqlMapGenerator
* javaClientGenerator(ANNOTATEDMAPPER / XMLMAPPER / MIXEDMAPPER)
* table

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="H2Tables" targetRuntime="MyBatis3">
        <plugin type="org.mybatis.generator.plugins.FluentBuilderMethodsPlugin" />
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
        <plugin type="org.mybatis.generator.plugins.RowBoundsPlugin" />

        <jdbcConnection driverClass="org.h2.Driver"
                        connectionURL="jdbc:h2:mem:testdb"
                        userId="sa"
                        password="">
        </jdbcConnection>

        <javaModelGenerator targetPackage="hyman.spring.data.mybatis.model"
                            targetProject="./src/main/java">
            <property name="enableSubPackages" value="true" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <sqlMapGenerator targetPackage="hyman.spring.data.mybatis.mapper"
                         targetProject="./src/main/resources/mapper">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>

        <javaClientGenerator type="MIXEDMAPPER"
                             targetPackage="hyman.spring.data.mybatis.mapper"
                             targetProject="./src/main/java">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>

        <table tableName="t_coffee" domainObjectName="Coffee" >
            <generatedKey column="id" sqlStatement="CALL IDENTITY()" identity="true" />
            <columnOverride column="price" javaType="org.joda.money.Money" jdbcType="BIGINT"
                            typeHandler="hyman.spring.data.mybatis.handler.MoneyTypeHandler"/>
        </table>
    </context>
</generatorConfiguration>
```

### 生成时可以使用的插件

内置插件都在org.mybatis.generator.plugins包中
* FluentBuilderMethodsPlugin(生成builder方法)
* ToStringPlugin(toString方法)
* SerializablePlugin(Serializable)
* RowBoundsPlugin(方便分页)

```java
private void generateArtifacts() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
    List<String> warnings = new ArrayList<>();
    ConfigurationParser cp = new ConfigurationParser(warnings);
    Configuration config = cp.parseConfiguration(
            this.getClass().getResourceAsStream("/generatorConfig.xml")
    );
    DefaultShellCallback callback = new DefaultShellCallback(true);
    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
    myBatisGenerator.generate(null);
}
```

## MyBatis工具-MyBatis PageHelper

[官网](https://pagehelper.github.io/)

* 支持多种数据库
* 支持多种分页方式
* SpringBoot支持:[pagehelper-spring-boot-starter](https://github.com/pagehelper/pagehelper-spring-boot)




## SpringBucks项目进度小结



