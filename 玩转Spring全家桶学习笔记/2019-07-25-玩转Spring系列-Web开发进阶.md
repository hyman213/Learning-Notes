Web开发进阶

## 设计好的RESTful Web Service

> REST提供了了⼀一组架构约束，当作为⼀一个整体来应⽤用时，强调组件交互的可伸缩性、接⼝口的通⽤用性、组件的独⽴立部署、以及⽤用来减少交互延迟、增强安全性、封装遗留留系统的中间组件。
>
> ​																																				- Roy Thomas Fielding

### REST原则 RESTful架构

如果一个架构符合REST原则，就称它为RESTful架构

> **URL定位资源，用HTTP动词（GET,POST,DELETE,DETC）描述操作。**
>
> 看Url就知道要什么
> 看http method就知道干什么
> 看http status code就知道结果如何

REST -- REpresentational State Transfer
之所以晦涩是因为前面主语被去掉了，全称是 Resource Representational State Transfer：通俗来讲就是：资源在网络中以某种表现形式进行状态转移。分解开来：
Resource：资源，即数据（前面说过网络的核心）。比如 newsfeed，friends等；
Representational：某种表现形式，比如用JSON，XML，JPEG等；
State Transfer：状态变化。通过HTTP动词实现。

[阮一峰 - 理解RESTful架构](http://www.ruanyifeng.com/blog/2011/09/restful.html)

[阮一峰 - RESTful API 设计指南](<http://www.ruanyifeng.com/blog/2014/05/restful_api.html>)

> **名称**
>
> Fielding将他对互联网软件的架构原则，定名为REST，即Representational State Transfer的缩写。我对这个词组的翻译是"表现层状态转化"。
>
> 如果一个架构符合REST原则，就称它为RESTful架构。
>
> **要理解RESTful架构，最好的方法就是去理解Representational State Transfer这个词组到底是什么意思，它的每一个词代表了什么涵义。**如果你把这个名称搞懂了，也就不难体会REST是一种什么样的设计。
>
> **资源（Resources）**
>
> REST的名称"表现层状态转化"中，省略了主语。"表现层"其实指的是"资源"（Resources）的"表现层"。
>
> **所谓"资源"，就是网络上的一个实体，或者说是网络上的一个具体信息。**它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的实在。你可以用一个URI（统一资源定位符）指向它，每种资源对应一个特定的URI。要获取这个资源，访问它的URI就可以，因此URI就成了每一个资源的地址或独一无二的识别符。
>
> 所谓"上网"，就是与互联网上一系列的"资源"互动，调用它的URI。
>
> **表现层（Representation）**
>
> "资源"是一种信息实体，它可以有多种外在表现形式。**我们把"资源"具体呈现出来的形式，叫做它的"表现层"（Representation）。**
>
> 比如，文本可以用txt格式表现，也可以用HTML格式、XML格式、JSON格式表现，甚至可以采用二进制格式；图片可以用JPG格式表现，也可以用PNG格式表现。
>
> URI只代表资源的实体，不代表它的形式。严格地说，有些网址最后的".html"后缀名是不必要的，因为这个后缀名表示格式，属于"表现层"范畴，而URI应该只代表"资源"的位置。它的具体表现形式，应该在HTTP请求的头信息中用Accept和Content-Type字段指定，这两个字段才是对"表现层"的描述。
>
> **状态转化（State Transfer）**
>
> 访问一个网站，就代表了客户端和服务器的一个互动过程。在这个过程中，势必涉及到数据和状态的变化。
>
> 互联网通信协议HTTP协议，是一个无状态协议。这意味着，所有的状态都保存在服务器端。因此，**如果客户端想要操作服务器，必须通过某种手段，让服务器端发生"状态转化"（State Transfer）。而这种转化是建立在表现层之上的，所以就是"表现层状态转化"。**
>
> 客户端用到的手段，只能是HTTP协议。具体来说，就是HTTP协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。它们分别对应四种基本操作：**GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源。**

> 综合上面的解释，我们总结一下什么是RESTful架构：
>
> 　　（1）每一个URI代表一种资源；
>
> 　　（2）客户端和服务器之间，传递这种资源的某种表现层；
>
> 　　（3）客户端通过四个HTTP动词，对服务器端资源进行操作，实现"表现层状态转化"。

### 实现Restful Web Service

- 识别资源
  - 找到能用CRUD操作的名词
  - 将资源组织为集合
  - 将资源合并为复合资源
  - 计算或处理函数
- 选择合适的资源粒度
  - 服务端：网络效率、表述的多少、客户端的易用程度
  - 客户端：可缓存性、修改频率、可变性
- 设计URI
  - 对资源进行合理的分组或划分
  - 用(/)表示资源间的层次关系
  - 使用(&)来分隔参数
  - 避免出现文件扩展名(.php, .aspx...)
- 选择合适的HTTP方法和返回码
- 设计资源的表述

### HTTP方法和返回码

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/07/20190725095643.png)

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/07/20190725095708.png)

**HTTP状态码**

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/07/20190725095747.png)

### 选择合适的表述

**JSON**

- MappingJackson2HttpMessageConverter
- GsonHttpMessageConverter
- JsonbHttpMessageConverter

**XML**

- MappingJackson2XmlHttpMessageConverter
- Jaxb2RootElementHttpMessageConverter

**HTML**

**ProtoBuf**

- ProtobufHttpMessageConverter

## HATEOAS

**超媒体即应用状态引擎（hypermedia as the engine of application state）**

超媒体是什么? 

> 当你浏览Web网页时，从一个连接跳到一个页面，再从另一个连接跳到另外一个页面，就是利用了超媒体的概念: 把一个个把资源链接起来。

RESTful API最好做到Hypermedia,或HATEOAS，即返回结果中提供链接，连向其他API方法，使得用户不查文档，也知道下一步应该做什么。比如，当用户向api.example.com的根目录发出请求，会得到这样一个文档。

```json
{"link": {
  "rel":   "collection https://www.example.com/zoos",
  "href":  "https://api.example.com/zoos",
  "title": "List of zoos",
  "type":  "application/vnd.yourformat+json"
}}
```

上面代码表示，文档中有一个link属性，用户读取这个属性就知道下一步该调用什么API了。rel表示这个API与当前网址的关系（collection关系，并给出该collection的网址），href表示API的路径，title表示API的标题，type表示返回类型。
 Hypermedia API的设计被称为[HATEOAS](https://link.jianshu.com?t=http://en.wikipedia.org/wiki/HATEOAS)。

**SpringBoot构建符合Hypermedia规范的RESTful 服务，可以不用构建Controller层**



HATEOAS表述中的超链接会提供服务所需的各种REST借口信息，无需事先约定如何访问;而传统的服务需要事先约定服务的地址和格式

```xml
<!--引入依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

```java
@RepositoryRestResource(path = "/coffee")
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> list);
    Coffee findByName(String name);
}
```

```json
// 访问http://localhost:8080/
{
    "_links": {
        "coffees": {
            "href": "http://localhost:8080/coffee{?page,size,sort}",
            "templated": true
        },
        "coffeeOrders": {
            "href": "http://localhost:8080/coffeeOrders{?page,size,sort}",
            "templated": true
        },
        "profile": {
            "href": "http://localhost:8080/profile"
        }
    }
}
```

## 使用WebFlux代替Spring MVC

### 什么是WebFlux

- ⽤用于构建基于 Reactive 技术栈之上的 Web 应⽤用程序
- 基于 Reactive Streams API ，运⾏行行在⾮非阻塞服务器器上

为什么会有WebFlux

- 对于⾮非阻塞 Web 应⽤用的需要
- 函数式编程

WebFlux的性能

- 请求的耗时并不不会有很⼤大的改善
- 仅需少量量固定数量量的线程和较少的内存即可实现扩展

WebFlux的应用场景

- 已有 Spring MVC 应⽤用，运⾏行行正常，就别改了了
- 依赖了了⼤大量量阻塞式持久化 API 和⽹网络 API，建议使⽤用 Spring MVC
- 已经使⽤用了了⾮非阻塞技术栈，可以考虑使⽤用 WebFlux
- 想要使⽤用 Java 8 Lambda 结合轻量量级函数式框架，可以考虑 WebFlux

### 编程模型

- 基于注解的控制器
- 函数式Endpoints

**基于注解的控制器**

常用注解

- @Controller

- @RequestMapping

- @RequestBody / @ResponseBody

返回值

- Mono<T> / Flux<T>




