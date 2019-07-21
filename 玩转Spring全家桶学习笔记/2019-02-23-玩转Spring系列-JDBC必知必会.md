---
title: 玩转Spring系列-JDBC必知必会
date: 2019-02-23 21:14:54
category: "玩转Spring"
tags: Spring
---

# JDBC必知必会

## 配置单数据源
`见代码`

## 配置多数据源
`见代码`

## 连接池

### [HikariCP](https://github.com/brettwooldridge/HikariCP)

It's Faster!
Spring Boot 2.x 默认使用HikariCP

![image](玩转Spring系列-JDBC必知必会/Spring0201.png)

#### HikariCP 为什什么快

* 字节码精简 ：优化代码，直到编译后的字节码最少，这样，CPU缓存可以加载更多的程序代码；
* 优化代理和拦截器 ：减少代码，例如HikariCP的Statement 
* proxy只有100行代码，只有BoneCP的十分之一；
* 自定义数组类型（FastStatementList）代替ArrayList,避免每次get()调用都要进行range check，避免调用remove()时的从头到尾的扫描；
* 自定义集合类型（ConcurrentBag ：提高并发读写的效率；

#### 常用HikariCP配置参数

[配置参考](https://github.com/brettwooldridge/HikariCP)

* spring.datasource.hikari.maximumPoolSize=10
* spring.datasource.hikari.minimumIdle=10
* spring.datasource.hikari.idleTimeout=600000
* spring.datasource.hikari.connectionTimeout=30000
* spring.datasource.hikari.maxLifetime=1800000

### [Alibaba Druid](https://github.com/alibaba/druid)

阿里巴巴数据库事业部出品，为监控而生的数据库连接池

Druid连接池是阿⾥巴巴开源的数据库连接池项⽬。Druid连接池为监控⽽生，内置强⼤大的监控功能，监控特性不影响性能。功能强大，能防SQL注入，内置Logging能诊断Hack应⽤用⾏行行为。


#### 实用功能

* 详细的监控
* ExceptionSorter，针对主流数据库的返回码都有支持
* SQL防注入
* 内置加密配置
* 众多扩展点，方便进行定制

#### Filter配置

```xml
spring.datasource.druid.filters=stat,config,wall,log4j  （全部使⽤用默认值）
```

#### 密码加密

```xml
spring.datasource.password=<加密密码>
spring.datasource.druid.filter.config.enabled=true
spring.datasource.druid.connection-properties=config.decrypt=true;config.decrypt.key=<public-key>
```

#### SQL防注⼊

```xml
spring.datasource.druid.filter.wall.enabled=true
spring.datasource.druid.filter.wall.db-type=h2
spring.datasource.druid.filter.wall.config.delete-allow=false
spring.datasource.druid.filter.wall.config.drop-table-allow=false
```

#### Druid Filter

* ⽤于定制连接池操作的各种环节
* 可以继承 FilterEventAdapter 以便⽅便地实现 Filter
* 修改 META-INF/druid-filter.properties 增加 Filter 配置

#### 慢日志

** 系统属性配置 **
* druid.stat.logSlowSql=true
* druid.stat.logSlowMillis=3000

** Spring Boot **
* spring.datasource.druid.filter.stat.enabled=true
* spring.datasource.druid.filter.stat.log-slow=true
* spring.datasource.druid.filter.stat.slow-sql-millis=3000

## Spring JDBC访问数据库




## Spring的事务抽象

### 事务抽象的核心接口
** PlatformTransactionManager **
* DataSourceTransactionManager
* HibernateTransactionManager
* JtaTransactionManager

** TransactionDefinition **
* Propagation
* Isolation
* Timeout
* Read-only status

### 事务传播特性

| 传播性 | 值 | 描述 |  
| --- | --- | --- | 
| PROPAGATION_REQUIRED | 0 | 当前有事务就用当前事务，没有就用新的（默认） | 
| PROPAGATION_SUPPORTS | 1 | 事务可有可无, 不是必须的 | 
| PROPAGATION_MANDATORY | 2 | 当前一定要有事务，否则抛异常 | 
| PROPAGATION_REQUIRES_NEW | 3 | 无论是否有事务，都起个新事务，把原先事务抛弃 | 
| PROPAGATION_NOT_SUPPORTED | 4 | 不支持事务，按非事务方式运行 | 
| PROPAGATION_NEVER | 5 | 不支持事务, 如果有事务则抛异常 | 
| PROPAGATION_NESTED | 6 | 当前有事务就在当前事务再起一个事务 |  

REQUIRES_NEW和NESTED的区别

### 事务隔离特性

### 编程式事务

** TransactionTemplate  **
* TransactionCallback
* TransactionCallbackWithoutResult

**  PlatformTransactionDefinition **
* 可以传入TransactionDefinition

### 声明式事务

声明式事务本质上是通过AOP来增强类的功能，Spring AOP本质就是为类做了一个代理

### 基于注解的配置方式

#### 开启事务注解的方式
* @EnableTransactionManager
* xml配置: <tx:annotation-driven/>
    - proxyTargetClass
    - mode
    - order

#### Transactional
* transactionManager
* propagation
* isolation
* timeout
* readOnly
* 怎么判断回滚



## Spring的JDBC异常抽象

Spring会将数据操作的异常转换为DataAccessException(数据库异常的基类)

### Spring是怎么样分辨错误码的

** 通过SQLErrorCodeSQLExceptionTranslator解析错误码 **

** ErrorCode定义 **

* org/springframework/jdbc/support/sql-error-codes.xml
* Classpath下的sql-error-codes.xml

## 答疑

### 工具类

* CygWin: Cygwin是一个在windows平台上运行的类UNIX模拟环境

### Spring常用注解

#### Java Config相关注解
* @Configuration
* @ImportResource
* @ComponentScan
* @Bean
* @ConfigurationProperties

#### 定义相关注解

* @Component / @Repository / @Service
* @Controller / @RestController
* @RequestMapping

#### 注入相关注解

* @Autowired / @Qualifier / @Resource
* @Value

### Actuator

Actuator提供的Endpoint
* /actuator/health   健康检查
* /actuator/beans    容器中所有Bean
* /actuator/mapping  Web的URL映射
* /actuator/env      环境信息

application.yml中配置：默认开启info和health
```
managerment.endpoints.web.exposure.include=*
```

### 多数据源、分库分表、读写分离

* 需要访问几个完全不同的数据库
* 需要访问同一个库的读写分离
* 需要访问一组做了分库分表的数据库

#### 数据库中间件
* TDDL(Taobao Distribute Data Layer)
* Cobar
* MyCAT
* Sharding-Shpere


