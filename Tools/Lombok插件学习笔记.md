# Lombok学习笔记

## 1. Lombok是什么

> Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
> Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

官网[https://projectlombok.org](https://projectlombok.org)



## 2. Lombok features

[官方文档](https://projectlombok.org/features/all)

### 2-1. `val`

> You can use `val` as the type of a local variable declaration instead of actually writing the type. When you do this, the type will be inferred from the initializer expression. The local variable will also be made final. This feature works on local variables and on foreach loops only, not on fields. The initializer expression is required.

```java
public String example() {
    val example = new ArrayList<String>();
    example.add("Hello, World!");
    val foo = example.get(0);
    return foo.toLowerCase();
}

等价于:

public String example() {
    final ArrayList<String> example = new ArrayList<String>();
    example.add("Hello, World!");
    final String foo = example.get(0);
    return foo.toLowerCase();
}
```



### 2-2. `var`

> Mutably! Hassle-free local variables.

### 2-3. `@NonNull`

> You can use `@NonNull` on the parameter of a method or constructor to have lombok generate a null-check statement for you.

该注解使用在属性上，该注解用于属的非空检查，当放在setter方法的字段上，将生成一个空检查，如果为空，则抛出NullPointerException。

### 2-4. `@Cleanup`

> You can use `@Cleanup` to ensure a given resource is automatically cleaned up before the code execution path exits your current scope. You do this by annotating any local variable declaration with the `@Cleanup` annotation like so:
> `@Cleanup InputStream in = new FileInputStream("some/file");`

该注解使用在属性前，该注解是用来保证分配的资源被释放。在本地变量上使用该注解，任何后续代码都将封装在try/finally中，确保当前作用于中的资源被释放。默认@Cleanup清理的方法为close，可以使用value指定不同的方法名称

### 2-5. `@Getter/@Setter`

该注解使用在类或者属性上.会默认生成一个无参构造和对应的getter和setter方法

### 2-6. `@ToString`

该注解使用在类上，该注解默认生成任何非讲台字段以名称-值的形式输出。

a、如果需要可以通过注释参数includeFieldNames来控制输出中是否包含的属性名称。

b、可以通过exclude参数中包含字段名称，可以从生成的方法中排除特定字段。

c、可以通过callSuper参数控制父类的输出

### 2-7.`@EqualsAndHashCode`

> Generates hashCode and equals implementations from the fields of your object



### 2-8. `@NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor`

`@NoArgsConstructor`  该注解使用在类上，该注解提供一个无参构造

`@RequiredArgsConstructor` 该注解使用在类上，使用类中所有带有 @NonNull 注解的或者带有 final 修饰的成员变量生成对应的构造方法

`@AllArgsConstructor` 该注解使用在类上，该注解提供一个全参数的构造方法，默认不提供无参构造。

### 2-9. `@Data`

> A shortcut for `@ToString`, `@EqualsAndHashCode`, `@Getter` on all fields, and `@Setter` on all non-final fields, and `@RequiredArgsConstructor`!

该注解使用在类上，该注解会提供getter、setter、equals、canEqual、hashCode、toString方法

### 2-10. `@Value`

这个注解用在 类 上，会生成含所有参数的构造方法，get 方法，此外还提供了equals、hashCode、toString 方法。

**注意：没有setter**

### 2-11.`@Builder`

会按builder模式生成一个内部类。提供在设计数据实体时，对外保持private setter，而对属性的赋值采用Builder的方式

@Builder声明实体，表示可以进行Builder方式初始化，@Value注解，表示只公开getter，对所有属性的setter都封闭，即private修饰，所以它不能和@Builder现起用

一般可以这样设计实体

```java
@Builder(toBuilder = true)
@Getter
public class UserInfo {
  private String name;
  private String email;
  @MinMoney(message = "金额不能小于0.")
  @MaxMoney(value = 10, message = "金额不能大于10.")
  private Money price;
}

@Builder注解赋值新对象
UserInfo userInfo = UserInfo.builder()
.name("zzl")
.email("bgood@sina.com")
.build();

如果使用了@Builder注解，而且需要修改原对象的属性值，要求实体上添加@Builder(toBuilder=true)
userInfo = userInfo.toBuilder()
.name("OK")
.email("zgood@sina.com")
.build();
```



### 2-12.`@SneakyThrows`

该注解使用在方法上，这个注解用在 方法 上，可以将方法中的代码用 try-catch 语句包裹起来，捕获异常并在 catch 中用 Lombok.sneakyThrow(e) 把异常抛出，可以使用 @SneakyThrows(Exception.class) 的形式指定抛出哪种异常。该注解需要谨慎使用

### 2-13. `@Synchronized`

该注解使用在类或者实例方法上，Synchronized在一个方法上，使用关键字可能会导致结果和想要的结果不同，因为多线程情况下会出现异常情况。Synchronized

关键字将在this示例方法情况下锁定当前对象，或者class讲台方法的对象上多锁定。这可能会导致死锁现象。一般情况下建议锁定一个专门用于此目的的独立锁，而不是允许公共对象进行锁定。该注解也是为了达到该目的。

### 2-14. `@Getter(lazy=true)`

### 2-15. `@Log`

> You put the variant of @Log on your class (whichever one applies to the logging system you use); you then have a static final log field, initialized to the name of your class, which you can then use to write log statements.
>
> 记录器的主题（或名称）将是使用注释进行@Log注释的类的类名称。这可以通过指定topic参数来定制。例如：@XSlf4j(topic="reporting")。

```java
//@CommonsLog 
private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class); 
//@JBossLog 
private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class); 
//@Log 
private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName()); 
//@Log4j 
private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class); 
//@Log4j2 
private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class); 
//@Slf4j 
private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class); 
//@XSlf4j 
private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);
```



### 2-16. `@Tolerate`

`@Tolerate `解决某些情况下使用Lombok注解生成的构造器或方法与开发者自己写构造器或方法因为冲突而被跳过的情况（如`@Builder`会忽略显示的无参构造方法）












