## JAVA 自定义注解

```java

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}

```

### 自定义注解类编写规则
  1. Annotation型定义为@interface, 所有的Annotation会自动继承java.lang.Annotation这一接口,并且不能再去继承别的类或是接口.
  2. 参数成员只能用public或默认(default)这两个访问权修饰
  3. 参数成员只能用基本类型byte,short,char,int,long,float,double,boolean八种基本数据类型和String、Enum、Class、annotations等数据类型,以及这一些类型的数组.
  4. 要获取类方法和字段的注解信息，必须通过Java的反射技术来获取 Annotation对象,因为你除此之外没有别的获取注解对象的方法
  5. 注解也可以没有定义成员, 不过这样注解就没啥用了

### java.lang.annotation提供了四种元注解，专门注解其他的注解

@Target指定注解作用的范围，其取值可有：

* ElementType.ANNOTATION_TYPE 注解类型声明
* ElementType.CONSTRUCTOR 构造方法声明
* ElementType.FIELD 字段声明（包括枚举常量）
* ElementType.LOCAL_VARIABLE 局部变量声明
* ElementType.METHOD 方法声明
* ElementType.PACKAGE 包声明
* ElementType.RARAMETER 参数声明
* ElementType.TYPE 类、接口（包括注解）或枚举

@Retention指定这个注解可以保留多久(用于描述注解的生命周期)，其取值：

* RetentionPolicy.RUNTIME 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。我们自定义的注解通常使用这种方式
* RetentionPolicy.CLASS 在类加载的时候丢弃。在字节码文件的处理中有用。注解默认使用这种方式
* RetentionPolicy.SOURCE 在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码。@Override, @SuppressWarnings都属于这类注解。

@Documented指定这个注解应该包含在注解项的文档中

@Inherited指定一个注解，作用在父类时，能够自动被子类继承

### 注解本质:
是一个继承了Annotation的特殊接口，其具体实现类是Java运行时生成的动态代理类。而我们通过反射获取注解时，返回的是Java运行时生成的动态代理对象$Proxy1。通过代理对象调用自定义注解（接口）的方法，会最终调用AnnotationInvocationHandler的invoke方法。该方法会从memberValues这个Map中索引出对应的值。而memberValues的来源是Java常量池。