# FREEMARKER学习笔记

## 什么是FreeMarker

FreeMarker 是一款 *模板引擎*： 即一种基于模板和要改变的数据， 并用来生成输出文本(HTML网页，电子邮件，配置文件，源代码等)的通用工具。 它不是面向最终用户的，而是一个Java类库，是一款程序员可以嵌入他们所开发产品的组件。模板编写为FreeMarker Template Language (FTL)

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/05/20190530091403.png)

FreeMarker 是 [免费的](http://www.fsf.org/philosophy/free-sw.html)， 基于Apache许可证2.0版本发布

## 模板开发入门

### Template + data-model = output

模板和静态HTML是相同的，只是它会包含一些 FreeMarker 指令

```html
<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <h1>Welcome ${user}!</h1>
  <p>Our latest product:
  <a href="${latestProduct.url}">${latestProduct.name}</a>!
</body>
</html>
```

标量类型可以分为如下的类别：字符串、 数字、 日期/时间、 布尔值

### 基础语法

* 插值表达式 - ${...}
* FTL标签(指令) - 以`#`开头，用户自定义指令使用`@`
* 注释 - `<#--`和 `-->`来标识

### 插值

字符串 - 注意转义

数字 - `number_format来设置, 或内建函数string`

日期时间 - `date_format, time_format 和 datetime_format`

表达式：

- 直接指定值

  - [字符串](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_direct_string)： `"Foo"` 或者 `'Foo'` 或者 `"It's \"quoted\""` 或者 `'It\'s "quoted"'` 或者 `r"C:\raw\string"`
  - [数字](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_direct_number)： `123.45`
  - [布尔值](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_direct_boolean)： `true`， `false`
  - [序列](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_direct_seuqence)： `["foo", "bar", 123.45]`； 值域： `0..9`, `0..<10` (或 `0..!10`), `0..`
  - [哈希表](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_direct_hash)： `{"name":"green mouse", "price":150}`

- 检索变量

  - [顶层变量](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_var_toplevel)： `user`
  - [从哈希表中检索数据](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_var_hash)： `user.name`， `user["name"]`
  - [从序列中检索数据](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_var_sequence)： `products[5]`
  - [特殊变量](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_var_special)： `.main`

- 字符串操作

  - [插值(或连接)](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_stringop_interpolation)： `"Hello ${user}!"` (或 `"Hello " + user + "!"`)
  - [获取一个字符](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_get_character)： `name[0]`
  - [字符串切分：](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_stringop_slice) 包含结尾： `name[0..4]`，不包含结尾： `name[0..<5]`，基于长度(宽容处理)： `name[0..*5]`，去除开头：`name[5..]`

- 序列操作

  - [连接](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_sequenceop_cat)： `users + ["guest"]`
  - [序列切分](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_seqenceop_slice)：包含结尾： `products[20..29]`， 不包含结尾： `products[20..<30]`，基于长度(宽容处理)：`products[20..*10]`，去除开头： `products[20..]`

- 哈希表操作

  - [连接](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_hashop_cat)： `passwords + { "joe": "secret42" }`

- [算术运算](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_arit)： `(x * 1.5 + 10) / 2 - y % 100`

- [比较运算](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_comparison)： `x == y`， `x != y`， `x < y`， `x > y`， `x >= y`， `x <= y`， `x lt y`， `x lte y`， `x gt y`， `x gte y`， 等等。。。。。。

- [逻辑操作](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_logicalop)： `!registered && (firstVisit || fromEurope)`

- [内建函数](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_builtin)： `name?upper_case`, `path?ensure_starts_with('/')`

- [方法调用](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_methodcall)： `repeat("What", 3)`

- 处理不存在的值

  ：

  - [默认值](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_missing_default)： `name!"unknown"` 或者 `(user.name)!"unknown"` 或者 `name!` 或者 `(user.name)!`
  - [检测不存在的值](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_missing_test)： `name??` 或者 `(user.name)??`

- [赋值操作](http://freemarker.foofun.cn/dgui_template_exp.html#dgui_template_exp_assignment)： `=`, `+=`, `-=`, `*=`, `/=`, `%=`, `++`, `--`

### 基本指令

* if指令

语法：`<#if condition> 和</#if>`

 `==` 是用来判断它两侧的值是否相等的操作符; `!=` 就是"不等于"

```html
<#if animals.python.price < animals.elephant.price>
  Pythons are cheaper than elephants today.
<#elseif animals.elephant.price < animals.python.price>
  Elephants are cheaper than pythons today.
<#else>
  Elephants and pythons cost the same today.
</#if>
```

- list指令

语法：`<#list sequence as loopVariable>repeatThis</#list>`

```html
<#list animals as animal>
   <tr><td>${animal.name}<td>${animal.price} Euros
</#list>
```

* include指令

使用 `include` 指令， 我们可以在模板中插入其他文件的内容。

`copyright_footer.html`

```html
<hr>
<i>
Copyright (c) 2000 <a href="http://www.acmee.com">Acmee Inc</a>,
<br>
All Rights Reserved.
</i>
```

插入该页时，可以使用include指令

```html
<html>
<head>
  <title>Test page</title>
</head>
<body>
  <h1>Test page</h1>
  <p>Blah blah...
  <#include "/copyright_footer.html">
</body>
</html>
```

* 联合使用

指令间可以相互嵌套

* 内置函数

内建函数使用 `?`(问号)代替 `.`(点)来访问它们。常用内建函数

- `user?html` 给出 `user` 的HTML转义版本， 比如 `&` 会由 `&amp;` 来代替。
- `user?upper_case` 给出 `user` 值的大写版本 (比如 "JOHN DOE" 来替代 "John Doe")
- `animal.name?cap_first` 给出 `animal.name` 的首字母大写版本(比如 "Mouse" 来替代 "mouse")
- `user?length` 给出 `user` 值中 *字符*的数量(对于 "John Doe" 来说就是8)
- `animals?size` 给出 `animals` 序列中 *项目* 的个数(我们示例数据模型中是3个)
- 如果在 `<#list animals as animal>` 和对应的 `</#list>` 标签中：
  - `animal?index` 给出了在 `animals` 中基于0开始的 `animal`的索引值
  - `animal?counter` 也像 `index`， 但是给出的是基于1的索引值
  - `animal?item_parity` 基于当前计数的奇偶性，给出字符串 "odd" 或 "even"。在给不同行着色时非常有用，比如在 `<td class="${animal?item_parity}Row">`中。

一些内建函数需要参数来指定行为，比如：

- `animal.protected?string("Y", "N")` 基于 `animal.protected` 的布尔值来返回字符串 "Y" 或 "N"。
- `animal?item_cycle('lightRow','darkRow')` 是之前介绍的 `item_parity` 更为常用的变体形式。
- `fruits?join(", ")` 通过连接所有项，将列表转换为字符串， 在每个项之间插入参数分隔符(比如 "orange,banana")
- `user?starts_with("J")` 根据 `user` 的首字母是否是 "J" 返回布尔值true或false。

内建函数应用可以链式操作，比如`user?upper_case?html` 会先转换用户名到大写形式，之后再进行HTML转义。(这就像可以链式使用 `.`(点)一样)

## 程序开发指南

### 入门

```html
public class Test {

    public static void main(String[] args) throws Exception {
        
        /* ------------------------------------------------------------------------ */    
        /* You should do this ONLY ONCE in the whole application life-cycle:        */    
    
        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW);

        /* ------------------------------------------------------------------------ */    
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */    

        /* Create a data-model */
        Map root = new HashMap();
        root.put("user", "Big Joe");
        Map latest = new HashMap();
        root.put("latestProduct", latest);
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("test.ftl");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        // Note: Depending on what `out` is, you may need to call `out.close()`.
        // This is usually the case for file output, but not for servlet output.
    }
}
```



#### 创建Configuration实例

```java
// Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.22) do you want to apply the fixes that are not 100%
// backward-compatible
Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:
cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
```

注意：不需要重复创建 `Configuration` 实例； 它的代价很高，尤其是会丢失缓存。`Configuration`实例就是应用级别的单例。

#### 创建数据模型

- 使用 `java.lang.String` 来构建字符串。
- 使用 `java.lang.Number` 来派生数字类型。
- 使用 `java.lang.Boolean` 来构建布尔值。
- 使用 `java.util.List` 或Java数组来构建序列。
- 使用 `java.util.Map` 来构建哈希表。
- 使用自定义的bean类来构建哈希表，bean中的项和bean的属性对应。比如， `product` 的 `price` 属性 (`getProperty()`)可以通过 `product.price` 获取

```java
// Create the root hash
Map<String, Object> root = new HashMap<>();
// Put string ``user'' into the root
root.put("user", "Big Joe");
// Create the hash for ``latestProduct''
Map<String, Object> latest = new HashMap<>();
// and put it into the root
root.put("latestProduct", latest);
// put ``url'' and ``name'' into latest
latest.put("url", "products/greenmouse.html");
latest.put("name", "green mouse");

// 也可以直接放入对象
Product latestProducts = getLatestProductFromDatabaseOrSomething();
root.put("latestProduct", latestProduct);
```

#### 获取模板

```java
Template temp = cfg.getTemplate("test.ftl");
```

当调用这个方法的时候，将会创建一个 `test.ftl` 的 `Template` 实例，通过读取 `/where/you/store/templates/test.ftl` 文件，之后解析(编译)它。`Template` 实例以解析后的形式存储模板， 而不是以源文件的文本形式。

`Configuration` 缓存 `Template` 实例，当再次获得 `test.ftl` 的时候，它可能不会再读取和解析模板文件了， 而只是返回第一次的 `Template`实例。

#### 合并模板和数据模型

```java
Writer out = new OutputStreamWriter(System.out);
temp.process(root, out);
```

### 数据模型

### 配置

配置(configuration)就是 `freemarker.template.Configuration` 对象， 它存储了常用(全局，应用程序级)的设置，定义了想要在所有模板中可用的变量(称为共享变量)。 而且，它会处理 `Template` 实例的新建和缓存

#### 共享变量

```java
Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
...
cfg.setSharedVariable("warp", new WarpDirective());
cfg.setSharedVariable("company", "Foo Inc.");
```

在所有使用这个配置的模板中，名为 `wrap` 的用户自定义指令和一个名为 `company` 的字符串将会在数据模型的根root上可见。在传递给 `Template.process` 的 根root对象里的变量将会隐藏同名的共享变量。

#### 配置设置

常用设置: `locale`，`number_format`， `default_encoding`，`template_exception_handler`

#### 模板加载

在 `Configuration` 中可以使用下面的方法来方便建立三种模板加载

```java
// 在磁盘的文件系统上设置了一个明确的目录， 它确定了从哪里加载模板。如果目录不存在，将会抛出异常。
void setDirectoryForTemplateLoading(File dir);
// 用Java的 ClassLoader 来加载类, 参数 prefix 是给模板的名称来加前缀的。类加载机制是首选用来加载模板的方法
void setClassForTemplateLoading(Class cl, String prefix);
// 需要Web应用的上下文和一个基路径作为参数
void setServletContextForTemplateLoading(Object servletContext, String path);
```

从多个位置加载模板

```java
FileTemplateLoader ftl1 = new FileTemplateLoader(new File("/tmp/templates"));
FileTemplateLoader ftl2 = new FileTemplateLoader(new File("/usr/data/templates"));
ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ftl2, ctl };
MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);

cfg.setTemplateLoader(mtl);
```

#### 错误控制

##### 可能的异常

关于 FreeMarker 发生的异常，可以分为如下几类：

- 当配置 FreeMarker 时发生异常
- 当加载和解析模板时发生异常：调用了 `Configuration.getTemplate(*...*)` 方法， FreeMarker就要把模板文件加载到内存中然后来解析它 (除非模板已经在 `Configuration` 对象中被 [缓存](http://freemarker.foofun.cn/pgui_config_templateloading.html#pgui_config_templateloading_caching) 了)。 在这期间，有两种异常可能发生：
  - 因模板文件没有找到而发生的 `IOException` 异常
  - 根据FTL语言的规则，模板文件发生语法错误时会导致 `freemarker.core.ParseException`异常，这种异常是 `IOException` 的一个子类。
- 当执行(处理)模板时发生的异常，也就是当调用了 `Template.process(*...*)` 方法时会发生的两种异常
  - 当试图写入输出对象时发生错误而导致的 `IOException` 异常。
  - 当执行模板时发生的其它问题而导致的 `freemarker.template.TemplatException` 异常。

##### 处理异常

FreeMarker 本身带错误控制器

- `TemplateExceptionHandler.DEBUG_HANDLER`： 打印堆栈跟踪信息(包括FTL错误信息和FTL堆栈跟踪信息)和重新抛出的异常。 这是默认的异常控制器
- `TemplateExceptionHandler.HTML_DEBUG_HANDLER`： 和 `DEBUG_HANDLER` 相同，但是它可以格式化堆栈跟踪信息， 那么就可以在Web浏览器中来阅读错误信息。 当你在制作HTML页面时，建议使用它而不是 `DEBUG_HANDLER`。
- `TemplateExceptionHandler.IGNORE_HANDLER`： 简单地压制所有异常(但是要记住，FreeMarker 仍然会写日志)。 它对处理异常没有任何作用，也不会重新抛出异常。
- `TemplateExceptionHandler.RETHROW_HANDLER`： 简单重新抛出所有异常而不会做其它的事情。 这个控制器对Web应用程序(假设你在发生异常之后不想继续执行模板)来说非常好

## 模板语言参考

### 内建参数参考

- [字符串内建函数](http://freemarker.foofun.cn/ref_builtins_string.html)
- [数字内建函数](http://freemarker.foofun.cn/ref_builtins_number.html)
- [日期内建函数](http://freemarker.foofun.cn/ref_builtins_date.html)
- [序列内建函数](http://freemarker.foofun.cn/ref_builtins_sequence.html)
- [哈希表内建函数](http://freemarker.foofun.cn/ref_builtins_hash.html)



## 使用技巧

### 如何避免对象/值为空时报错

方案1

```xml
<#if user.name?exists>  
 //TO DO  
</#if>  
  
<#if user.age??>  
 //TO DO  
</#if>  
```

方案2

```xml
通过设置默认值${name!''}来避免对象为空的错误。如果name为空，就以默认值（“!”后的字符）显示。
```

首选

对象user，name为user的属性的情况，user，name都有可能为空，那么可以写成**${(user.name)!''}**,表示user或者name为null，都显示为空。判断为空

```xml
<#if (user.name)??>
……
</#if>
```

示例：

```xml
当user.name为null 
${user.name}，异常 
${user.name!},显示空白 
${user.name!'vakin'}，若user.name不为空则显示本身的值，否则显示vakin 
${user.name?default('vakin')}，同上 
${user.name???string(user.name,'vakin')},同上
```







## 参考

[FreeMarker 中文官方参考手册](http://freemarker.foofun.cn/toc.html)