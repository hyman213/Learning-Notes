## Spring事务管理-事务的传播特性和隔离性

## 事务特性 - ACID

> A：原子性，表示一个事务的多个操作要么同时成功，要么同时失败
>
> C：一致性，表示一个原理的业务处理不能存在部分成功，部分失败的情况
>
> I：隔离性，多个事务执行时，数据需要隔离
>
> D：持久性，事务执行完成后，数据持久化，可以恢复

## 事务传播行为(7种) - Propagation

### Propagation.REQUIRED

> 当前方法必须运行在事务中，如果事务存在，则加入该事务，否则开启一个新事务
>
> This is the default setting of a transaction annotation

### Propagation.REQUIRES_NEW

> Create a new transaction, and suspend the current transaction if one exists
>
> 与PROPAGATION_REQUIRED的事务区别在于事务的回滚程度。因为ServiceB.methodB是新起一个事务，那么就是存在两个不同的事务。如果ServiceB.methodB已经提交，那么ServiceA.methodA失败回滚，ServiceB.methodB是不会回滚的。如果ServiceB.methodB失败回滚，如果他抛出的异常被ServiceA.methodA捕获，ServiceA.methodA事务仍然可能提交。



### Propagation.SUPPORTS

>Support a current transaction, execute non-transactionally if none exists.
>
>如果当前在事务中，即以事务的形式运行，如果当前不再一个事务中，那么就以非事务的形式运行。



### Propagation.NOT_SUPPORTED

> Execute non-transactionally, suspend the current transaction if one exists.
>
> 比如ServiceA.methodA的事务级别是PROPAGATION_REQUIRED ，而ServiceB.methodB的事务级别是PROPAGATION_NOT_SUPPORTED ，那么当执行到ServiceB.methodB时，ServiceA.methodA的事务挂起，而他以非事务的状态运行完，再继续ServiceA.methodA的事务。
>
> 如果ServiceB.methodB或者ServiceA.methodA中出现了异常，ServiceB.methodB中已经执行的操作是不会回滚的。因为methodB是以非事务状态运行的。



### Propagation.MANDATORY

> Support a current transaction, throw an exception if none exists
>
> 当前事务必须运行在事务中，如果不存在事务则报异常



### Propagation.NEVER

> Execute non-transactionally, throw an exception if a transaction exists.
>
> 当前方法不能运行在事务当中，如果存在事务则报异常



### Propagation.NESTED

> Execute within a nested transaction if a current transaction exists, behave like PROPAGATION_REQUIRED else.
>
> 事务嵌套，如果存在当前事务，该方法运行在嵌套事务中，嵌套的事务可以独立的提交或回滚，如果不存在事务开启一个新事务。
>
> 与PROPAGATION_REQUIRES_NEW的区别是，PROPAGATION_REQUIRES_NEW另起一个事务，将会与他的父事务相互独立， 而Nested的事务和他的父事务是相依的，他的提交是要等和他的父事务一块提交的。也就是说，如果父事务最后回滚，他也要回滚的。而Nested事务的好处是他有一个savepoint。也就是说ServiceB.methodB失败回滚，那么ServiceA.methodA也会回滚到savepoint点上，ServiceA.methodA可以选择另外一个分支，比如 ServiceC.methodC，继续执行，来尝试完成自己的事务。 但是这个事务并没有在EJB标准中定义。

如果熟悉 JDBC 中的保存点（SavePoint）的概念，那嵌套事务就很容易理解了，其实嵌套的子事务就是保存点的一个应用，一个事务中可以包括多个保存点，每一个嵌套子事务。另外，外部事务的回滚也会导致嵌套子事务的回滚。

### 几个注意点

- 正确设置@Transactional的rollbackFor属性

> 默认情况下，如果在事务中抛出了未检查异常（继承自 RuntimeException 的异常）或者 Error，则 Spring 将回滚事务；除此之外，Spring 不会回滚事务。
>
> 如果在事务中抛出其他类型的异常，并期望 Spring 能够回滚事务，可以指定 rollbackFor。

- @Transactional 只能应用到 public 方法才有效
- 避免 Spring 的 AOP 的自调用问题

> 在 Spring 的 AOP 代理下，只有目标方法由外部调用，目标方法才由 Spring 生成的代理对象来管理，这会造成自调用问题。若同一类中的其他没有@Transactional 注解的方法内部调用有@Transactional 注解的方法，有@Transactional 注解的方法的事务被忽略，不会发生回滚。



### 示例

#### 同一个Service中不同方法调用

自调用问题：同一个Service中，方法A调用方法B

```java
// 在同一个Service中, 在非事务方法中调用一个申明事务的方法
public void create(Coffee coffee) {
    coffeeDao.create(coffee);
    updateById(1);
}

@Transactional
public boolean updateById(int id) {
    coffeeDao.updateById(id);
    System.out.println(1 / 0);
    return true;
}
// create和updateById都会执行成功，不回滚
// updateById 尽管有@Transactional 注解，但它被内部方法create调用，事务被忽略，出现异常事务不会发生回滚。

```



```java
public void create(Coffee coffee) {
    coffeeDao.create(coffee);
    updateById(1);
}

public boolean updateById(int id) {
    coffeeDao.updateById(id);
    System.out.println(1/0);
    return true;
}

// create和updateById都会执行成功，不回滚
```

> Propagation.REQUIRED - 默认 - 当前方法必须运行在事务中，如果事务存在，则加入该事务，否则开启一个新事务

```java
@Transactional
public void create(Coffee coffee) {
    coffeeDao.create(coffee);
    updateById(1);
}

public boolean updateById(int id) {
    coffeeDao.updateById(id);
    System.out.println(1 / 0);
    return true;
}
// create和updateById都会回滚, updateById会加入到create的事务中

JDBC Connection [HikariProxyConnection@1876962112 wrapping com.mysql.jdbc.JDBC4Connection@3ed3bb5c] will be managed by Spring
==>  Preparing: INSERT INTO t_coffee(name, price, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: latte(String), 20(BigDecimal)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4eb99d44]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4eb99d44] from current transaction
==>  Preparing: UPDATE t_coffee SET price = 50 WHERE id = ? 
==> Parameters: 1(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4eb99d44]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4eb99d44]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4eb99d44]
```

> Propagation.REQUIRES_NEW

```java
@Transactional
public void create(Coffee coffee) {
    coffeeDao.create(coffee);
    updateById(1);
}

@Transactional(propagation = Propagation.REQUIRES_NEW)
public boolean updateById(int id) {
    coffeeDao.updateById(id);
    System.out.println(1 / 0);
    return true;
}

// create和updateById都会回滚, 貌似updateById没有开启新事务, 有待考证自调用中与不同Service中调用，是不是不同

JDBC Connection [HikariProxyConnection@533697105 wrapping com.mysql.jdbc.JDBC4Connection@5d060fd1] will be managed by Spring
==>  Preparing: INSERT INTO t_coffee(name, price, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: latte(String), 20(BigDecimal)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2835193e]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2835193e] from current transaction
==>  Preparing: UPDATE t_coffee SET price = 50 WHERE id = ? 
==> Parameters: 1(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2835193e]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2835193e]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2835193e]
```



> Propagation.SUPPORTS - 当前方法不需要事务上下文，如果存在事务则加入

```java
@Transactional
public void create(Coffee coffee) {
    coffeeDao.create(coffee);
    updateById(1);
}

@Transactional(propagation = Propagation.SUPPORTS)
public boolean updateById(int id) {
    coffeeDao.updateById(id);
    System.out.println(1 / 0);
    return true;
}

==>  Preparing: INSERT INTO t_coffee(name, price, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: latte(String), 20(BigDecimal)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@555682b0]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@555682b0] from current transaction
==>  Preparing: UPDATE t_coffee SET price = 50 WHERE id = ? 
==> Parameters: 1(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@555682b0]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@555682b0]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@555682b0]
```


#### 不同Service中方法之间的调用

```java
// 基本结构
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeDao coffeeDao;


    public boolean createA(String customer, List<String> items) {
        Order order = this.createOrder(customer, OrderState.INIT);
        Long orderId = order.getId();
        for (String item : items) {
            Coffee coffee = coffeeDao.findByName(item);
            if (coffee != null) {
                coffeeOrderService.createB(orderId, coffee.getId());
            }
        }
        return true;
    }

    public Order createOrder(String customer, OrderState state) {
        Order order = Order.builder().customer(customer).state(state).build();
        orderDao.create(order);
        return order;
    }
}


@Service
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderDao coffeeOrderDao;

    public boolean createB(long orderId, long itemId) {
        coffeeOrderDao.createCoffeeOrder(CoffeeOrder.builder().orderid(orderId).itemid(itemId).build());
        System.out.println(1 / 0);
        return true;
    }
}
```

1. 都没有申明事务

```java
public boolean createA(String customer, List<String> items) {
    Order order = this.createOrder(customer, OrderState.INIT);
    Long orderId = order.getId();
    for (String item : items) {
        Coffee coffee = coffeeDao.findByName(item);
        if (coffee != null) {
            coffeeOrderService.create(orderId, coffee.getId());
        }
    }
    return true;
}

public boolean createB(long orderId, long itemId) {
    coffeeOrderDao.createCoffeeOrder(CoffeeOrder.builder().orderid(orderId).itemid(itemId).build());
    System.out.println(1 / 0);
    return true;
}

// 执行成功，不会回滚。每次执行SQL都是新建一个SqlSession

JDBC Connection [HikariProxyConnection@403792361 wrapping com.mysql.jdbc.JDBC4Connection@7b7b13cb] will not be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3ef30dea]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4d1ef51] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1948733638 wrapping com.mysql.jdbc.JDBC4Connection@7b7b13cb] will not be managed by Spring
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4d1ef51]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@69edbc89] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1383439419 wrapping com.mysql.jdbc.JDBC4Connection@7b7b13cb] will not be managed by Spring
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 1(Long), 1(Long)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@69edbc89]
```

2. 在createB中申明事务

```java
@Transactional
public boolean createB(long orderId, long itemId) {
  coffeeOrderDao.createCoffeeOrder(CoffeeOrder.builder().orderid(orderId).itemid(itemId).build());
    System.out.println(1 / 0);
    return true;
}

// 仅在coffeeOrderService的createB方法（调用方法）中申明事务, coffeeOrderService之前的操作在异常发生后不会回滚，仅回滚本方法中的createCoffeeOrder操作

JDBC Connection [HikariProxyConnection@2079370698 wrapping com.mysql.jdbc.JDBC4Connection@54e0239a] will not be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2fe8922b]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@210df172] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1511507844 wrapping com.mysql.jdbc.JDBC4Connection@54e0239a] will not be managed by Spring
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@210df172]
Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2cd56873]
JDBC Connection [HikariProxyConnection@1926216341 wrapping com.mysql.jdbc.JDBC4Connection@54e0239a] will be managed by Spring
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 3(Long), 1(Long)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2cd56873]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2cd56873]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2cd56873]
```

3. 在createA上申明事务

```java
@Transactional
public boolean createA(String customer, List<String> items) {
    Order order = this.createOrder(customer, OrderState.INIT);
    Long orderId = order.getId();
    for (String item : items) {
        Coffee coffee = coffeeDao.findByName(item);
        if (coffee != null) {
            coffeeOrderService.createB(orderId, coffee.getId());
        }
    }
    return true;
}

// 仅在createA方法（调用方法）中申明事务, createB方法会加入到当前事务，发生异常后会同时回滚
注：发生回滚后，数据表t_order的AUTO_INCREMENT发生了变化

Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2]
JDBC Connection [HikariProxyConnection@867412988 wrapping com.mysql.jdbc.JDBC4Connection@5755b98d] will be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2] from current transaction
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2] from current transaction
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 2(Long), 1(Long)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@20ead1c2]
```

4. 两个方法中都申明事务后，同上

```java
// 两个方法中都申明事务后，同上

Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87]
JDBC Connection [HikariProxyConnection@2093551763 wrapping com.mysql.jdbc.JDBC4Connection@3baa0195] will be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87] from current transaction
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87] from current transaction
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 4(Long), 1(Long)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@36a94b87]
```

5. createB申明事务Propagation.REQUIRES_NEW

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public boolean createB(long orderId, long itemId) {
  coffeeOrderDao.createCoffeeOrder(CoffeeOrder.builder().orderid(orderId).itemid(itemId).build());
    System.out.println(1 / 0);
    return true;
}

// createA申明了事务，createB申明事务,指定propagation = Propagation.REQUIRES_NEW。执行createB会新启一个事务，在B中出现异常后，A和B同时回滚

Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
JDBC Connection [HikariProxyConnection@1545749267 wrapping com.mysql.jdbc.JDBC4Connection@522af60] will be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb] from current transaction
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
Transaction synchronization suspending SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2dfbf963]
JDBC Connection [HikariProxyConnection@1540844500 wrapping com.mysql.jdbc.JDBC4Connection@4ec4f015] will be managed by Spring
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 5(Long), 1(Long)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2dfbf963]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2dfbf963]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2dfbf963]
Transaction synchronization resuming SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5c3ef2fb]
```



```java
// createA中申明事务后，createB申明propagation=Propagation.SUPPORTS, 此时同Propagation.REQUIRED

// createA未申明事务，createB申明propagation=Propagation.SUPPORTS, 此时两个方法都以非事务方式执行(不知此时为何会新建一个transactional SqlSession?)
//    @Transactional
public boolean createA(String customer, List<String> items) {
    Order order = this.createOrder(customer, OrderState.INIT);
    Long orderId = order.getId();
    for (String item : items) {
        Coffee coffee = coffeeDao.findByName(item);
        if (coffee != null) {
            coffeeOrderService.createB(orderId, coffee.getId());
        }
    }
    return true;
}

Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18d9dfd5] was not registered for synchronization because synchronization is not active
2019-07-28 11:42:32.885  INFO 18256 --- [nio-8080-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2019-07-28 11:42:33.201  INFO 18256 --- [nio-8080-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
JDBC Connection [HikariProxyConnection@1687763648 wrapping com.mysql.jdbc.JDBC4Connection@6d9b3e7] will not be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@18d9dfd5]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5d0c8a57] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@2111770406 wrapping com.mysql.jdbc.JDBC4Connection@6d9b3e7] will not be managed by Spring
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5d0c8a57]
Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2556f84b]
JDBC Connection [HikariProxyConnection@1703417088 wrapping com.mysql.jdbc.JDBC4Connection@6d9b3e7] will be managed by Spring
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 10(Long), 1(Long)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2556f84b]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2556f84b]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2556f84b]
```

6. 在createB中申明事务, propagation=Propagation.NOT_SUPPORT

```java
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public boolean createB(long orderId, long itemId) {
    coffeeOrderDao.createCoffeeOrder(CoffeeOrder.builder().orderid(orderId).itemid(itemId).build());
    System.out.println(1 / 0);
    return true;
}

// createA中申明了事务，在createB中申明事务, propagation=Propagation.NOT_SUPPORT, 结果：createA中操作回滚, createB中操作没有回滚（这里有点疑问，为什么显示会重新开启一个新事务）

Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
JDBC Connection [HikariProxyConnection@423017752 wrapping com.mysql.jdbc.JDBC4Connection@622b4626] will be managed by Spring
==>  Preparing: INSERT INTO t_order(customer, state, create_time, update_time) VALUES (?, ?, now(), now()) 
==> Parameters: Li Lei(String), 0(Integer)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
Fetched SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189] from current transaction
==>  Preparing: SELECT * FROM t_coffee WHERE name = ? 
==> Parameters: mocha(String)
<==    Columns: id, create_time, update_time, name, price
<==        Row: 1, 2019-07-28 10:33:58.0, 2019-07-28 10:19:30.0, mocha, 25
<==      Total: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
Transaction synchronization suspending SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
Creating a new SqlSession
Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@73b57b37]
JDBC Connection [HikariProxyConnection@860195064 wrapping com.mysql.jdbc.JDBC4Connection@4cbbc79a] will be managed by Spring
==>  Preparing: INSERT INTO t_order_coffee(coffee_order_id, items_id) VALUES (?, ?) 
==> Parameters: 9(Long), 1(Long)
<==    Updates: 1
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@73b57b37]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@73b57b37]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@73b57b37]
Transaction synchronization resuming SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@1e8b7189]
```



```java
createA中未申明事务，createB中申明propagation = Propagation.MANDATORY, 执行B前抛出异常，A不会回滚
```



```java
createA中申明事务，createB中申明propagation = Propagation.NEVER, 执行B前抛出异常，A会回滚
```



```java
createA中申明事务, createB中申明propagation = Propagation.NESTED, 执行A时抛出异常，此时A和B会同时回滚。说明B中的事务会和A的事务一起提交。

@Transactional
    public boolean createA(String customer, List<String> items) {
        Order order = this.createOrder(customer, OrderState.INIT);
        Long orderId = order.getId();
        for (String item : items) {
            Coffee coffee = coffeeDao.findByName(item);
            if (coffee != null) {
                coffeeOrderService.createB(orderId, coffee.getId());
            }
        }
        System.out.println(1 / 0);
        return true;
    }
    
@Transactional(propagation = Propagation.NESTED)
public boolean createB(long orderId, long itemId) {
    coffeeOrderDao.createCoffeeOrder(CoffeeOrder.builder().orderid(orderId).itemid(itemId).build());
    //        System.out.println(1 / 0);
    return true;
}
```



## 事务的隔离级别(5种) - Isolation

事务的隔离级别定义了一个事务可能受其他并发事务的影响程度。 `org.springframework.transaction.annotation.Isolation`

### 并发事务带来的问题

在典型的应用程序中，多个事务并发运行，经常会操作相同的数据来完成各自的任务（多个用户对统一数据进行操作）。并发虽然是必须的，但可能会导致一下的问题。

- **脏读（Dirty read）:**  当一个事务正在访问数据并且对数据进行了修改，而这种修改还没有提交到数据库中，这时另外一个事务也访问了这个数据，然后使用了这个数据。因为这个数据是还没有提交的数据，那么另外一个事务读到的这个数据是“脏数据”，依据“脏数据”所做的操作可能是不正确的。

- **丢失修改（Lost to modify）:** 指在一个事务读取一个数据时，另外一个事务也访问了该数据，那么在第一个事务中修改了这个数据后，第二个事务也修改了这个数据。这样第一个事务内的修改结果就被丢失，因此称为丢失修改。

例如：事务1读取某表中的数据A=20，事务2也读取A=20，事务1修改A=A-1，事务2也修改A=A-1，最终结果A=19，事务1的修改被丢失。

- **不可重复读（Unrepeatableread）:** 指在一个事务内多次读同一数据。在这个事务还没有结束时，另一个事务也访问该数据。那么，在第一个事务中的两次读数据之间，由于第二个事务的修改导致第一个事务两次读取的数据可能不太一样。这就发生了在一个事务内两次读到的数据是不一样的情况，因此称为不可重复读。

- **幻读（Phantom read）:** 幻读与不可重复读类似。它发生在一个事务（T1）读取了几行数据，接着另一个并发事务（T2）插入了一些数据时。在随后的查询中，第一个事务（T1）就会发现多了一些原本不存在的记录，就好像发生了幻觉一样，所以称为幻读。

**不可重复读的重点是修改，幻读的重点在于新增或者删除。**

> 例1（同样的条件, 你读取过的数据, 再次读取出来发现值不一样了 ）：事务1中的A先生读取自己的工资为     1000的操作还没完成，事务2中的B先生就修改了A的工资为2000，导        致A再读自己的工资时工资变为  2000；这就是不可重复读。
>
> 例2（同样的条件, 第1次和第2次读出来的记录数不一样 ）：假某工资单表中工资大于3000的有4人，事务1读取了所有工资大于3000的人，共查到4条记录，这时事务2 又插入了一条工资大于3000的记录，事务1再次读取时查到的记录就变为了5条，这样就导致了幻读。

### Isolation.DEFAULT

使用后端数据库默认的隔离级别，Mysql 默认采用的 REPEATABLE_READ隔离级别 Oracle 默认采用的 READ_COMMITTED隔离级别

### Isolation.READ_UNCOMMITTED

> ```
> A constant indicating that dirty reads, non-repeatable reads and phantom reads
> can occur. This level allows a row changed by one transaction to be read by
> another transaction before any changes in that row have been committed
> (a "dirty read"). If any of the changes are rolled back, the second transaction will have retrieved an invalid row.
> ```

最低的隔离级别，允许读取尚未提交的数据变更，**可能会导致脏读、幻读或不可重复读**

### Isolation.READ_COMMITTED

> ```
> A constant indicating that dirty reads are prevented; non-repeatable reads
> and phantom reads can occur. This level only prohibits a transaction
> from reading a row with uncommitted changes in it.
> ```

允许读取并发事务已经提交的数据，**可以阻止脏读，但是幻读或不可重复读仍有可能发生**

### Isolation.REPEATABLE_READ

> ```
> A constant indicating that dirty reads and non-repeatable reads are
> prevented; phantom reads can occur. This level prohibits a transaction
> from reading a row with uncommitted changes in it, and it also prohibits
> the situation where one transaction reads a row, a second transaction
> alters the row, and the first transaction rereads the row, getting
> different values the second time (a "non-repeatable read").
> ```

对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，**可以阻止脏读和不可重复读，但幻读仍有可能发生。**



### Isolation.SERIALIZABLE

> ```
> A constant indicating that dirty reads, non-repeatable reads and phantom
> reads are prevented. This level includes the prohibitions in
> {@code ISOLATION_REPEATABLE_READ} and further prohibits the situation
> where one transaction reads all rows that satisfy a {@code WHERE}
> condition, a second transaction inserts a row that satisfies that
> {@code WHERE} condition, and the first transaction rereads for the
> same condition, retrieving the additional "phantom" row in the second read.
> ```

最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，**该级别可以防止脏读、不可重复读以及幻读**。但是这将严重影响程序的性能。通常情况下也不会用到该级别。



## 事务超时属性 - 一个事务允许执行的最长时间

事务超时，就是指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。在 TransactionDefinition 中以 int 的值来表示超时时间，其单位是秒



## 事务只读属性 - 对事物资源是否执行只读操作

事务的只读属性是指，对事务性资源进行只读操作或者是读写操作。所谓事务性资源就是指那些被事务管理的资源，比如数据源、 JMS 资源，以及自定义的事务性资源等等。如果确定只对事务性资源进行只读操作，那么我们可以将事务标志为只读的，以提高事务处理的性能。在 TransactionDefinition 中以 boolean 类型来表示该事务是否只读。

## 事务回滚策略

这些规则定义了哪些异常会导致事务回滚而哪些不会。默认情况下，事务只有遇到运行期异常时才会回滚，而在遇到检查型异常时不会回滚（这一行为与EJB的回滚行为是一致的）。 但是你可以声明事务在遇到特定的检查型异常时像遇到运行期异常那样回滚。同样，你还可以声明事务遇到特定的异常不回滚，即使这些异常是运行期异常。

























