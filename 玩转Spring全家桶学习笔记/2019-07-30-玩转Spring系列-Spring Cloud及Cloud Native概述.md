# Spring Cloud及Cloud Native概述

## 微服务的理解

> 微服务就是一些协同工作的小而自治的服务

**优点**

- 异构型 - 语言、存储
- 弹性 - 一个组件不可用，不会导致级联故障
- 扩展 - 单体服务不易扩展，多个较小的服务可以按需扩展
- 易于部署

**缺点**

- 分布式系统的复杂性
- 部署、监控等诸多运维复杂性

## 理解云原生

> 云原生技术有利于各组织在公有云、私有云和混合云等新型动态环境中，构建和运行可弹性扩展的应用

**云原生应用要求**

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/07/20190730215043.png)

- DevOps - 开发与运维一同致力于交付高品质的软件
- 持续交付 - 软件的构建、测试和发布，更快、更频繁、更稳定
- 微服务 - 以一组小型服务的形式来部署应用
- 容器 - 提供比传统虚拟机更高的效率

## The Twelve-Factor App - 十二要素应用宣言

[12-Factor App简体中文](<https://12factor.net/zh_cn/>)

如今，软件通常会作为一种服务来交付，它们被称为网络应用程序，或软件即服务（SaaS）。12-Factor 为构建如下的 SaaS 应用提供了方法论：

- 使用**标准化**流程自动配置，从而使新的开发者花费最少的学习成本加入这个项目。
- 和操作系统之间尽可能的**划清界限**，在各个系统中提供**最大的可移植性**。
- 适合**部署**在现代的**云计算平台**，从而在服务器和系统管理方面节省资源。
- 将开发环境和生产环境的**差异降至最低**，并使用**持续交付**实施敏捷开发。
- 可以在工具、架构和开发流程不发生明显变化的前提下实现**扩展**。

这套理论适用于任意语言和后端服务（数据库、消息队列、缓存等）开发的应用程序。

**I. 基准代码-Codebase**

一份基准代码，多份部署

- 使用版本控制系统加以管理

- 基准代码和应用之间总是保持一一对应的关系
- 尽管每个应用只对应一份基准代码，但可以同时存在多份部署。

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/07/20190730222612.png)

**II. 依赖-Dependencies**

显式声明依赖关系

- **12-Factor规则下的应用程序不会隐式依赖系统级的类库** 。它一定通过 *依赖清单* ，确切地声明所有依赖项

**III. 配置-Config**

在环境中存储配置

- **代码和配置严格分离**
- 判断一个应用是否正确地将配置排除在代码之外，一个简单的方法是看该应用的基准代码是否可以立刻开源，而不用担心会暴露任何敏感的信息。

**IV. 后端服务-Backing services**

把后端服务当做附加资源

*后端服务*是指程序运行所需要的通过网络调用的各种服务，如数据库（[MySQL](http://dev.mysql.com/)，[CouchDB](http://couchdb.apache.org/)），消息/队列系统（[RabbitMQ](http://www.rabbitmq.com/)，[Beanstalkd](https://beanstalkd.github.io/)），SMTP 邮件发送服务（[Postfix](http://www.postfix.org/)），以及缓存系统（[Memcached](http://memcached.org/)）。

- **12-Factor 应用不会区别对待本地或第三方服务。**

- 部署可以按需加载或卸载资源。例如，如果应用的数据库服务由于硬件问题出现异常，管理员可以从最近的备份中恢复一个数据库，卸载当前的数据库，然后加载新的数据库 – 整个过程都不需要修改代码。

**V. 构建，发布，运行-Build, release, run**

严格分离构建和运行

 [Capistrano](https://github.com/capistrano/capistrano/wiki)

- **严格区分构建，发布，运行这三个步骤**
- 部署工具通常都提供了发布管理工具，最引人注目的功能是退回至较旧的发布版本。
- 每一个发布版本必须对应一个唯一的发布 ID

**VI. 进程-Processes**

以一个或多个无状态进程运行应用

- **12-Factor 应用的进程必须无状态且 无共享**

  粘性 session 是 12-Factor 极力反对的。Session 中的数据应该保存在诸如 [Memcached](http://memcached.org/) 或 [Redis](http://redis.io/) 这样的带有过期时间的缓存中。

**VII. 端口绑定-Port binding**

通过端口绑定提供服务

- **12-Factor 应用完全自我加载** 而不依赖于任何网络服务器就可以创建一个面向网络的服务
- 互联网应用 **通过端口绑定来提供服务** ，并监听发送至该端口的请求。

**VIII. 并发-Concurrency**

通过进程模型进行扩展

**IX. 易处理-Disposability**

快速启动和优雅终止可最大化健壮性

- **12-Factor 应用的 进程 是 易处理（disposable）的，意思是说它们可以瞬间开启或停止。**
- 进程应当追求 **最小启动时间** 
- 进程 **一旦接收 终止信号（SIGTERM） 就会优雅的终止**
- 进程还应当**在面对突然死亡时保持健壮**，

**X. 开发环境与线上环境等价-Dev/prod parity**

- **12-Factor 应用想要做到 持续部署 就必须缩小本地与线上差异**
- **12-Factor 应用的开发人员应该反对在不同环境间使用不同的后端服务**

**XI. 日志-Logs**

把日志当做事件流

- *日志* 使得应用程序运行的动作变得透明

**XII. 管理进程-Admin processes**

后台管理任务当作一次性进程运行

- 一次性管理进程应该和正常的 [常驻进程](https://12factor.net/zh_cn/processes) 使用同样的环境
- 所有进程类型应该使用同样的 [依赖隔离](https://12factor.net/zh_cn/dependencies) 技术



## Spring Cloud的组成部分

> Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems (e.g. configuration management, service discovery, circuit breakers, intelligent routing, micro-proxy, control bus, one-time tokens, global locks, leadership election, distributed sessions, cluster state)
>
> Spring Cloud为开发人员提供了快速构建分布式系统中一些常见模式的工具（例如配置管理，服务发现，断路器，智能路由，微代理，控制总线）

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/07/20190731083758.png)

**Spring Cloud的主要功能**

- 服务发现
- 服务网关
- 服务熔断
- 分布式消息
- 配置服务
- 分布式跟踪
- 服务安全
- 各种云平台支持

**Spring Cloud的版本号规则**

[Spring-cloud-dependencies版本列表](<https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies>)

- 版本命名

Spring Cloud是一个拥有诸多子项目的大型综合项目，原则上其子项目也都维护着自己的发布版本号。那么每一个Spring Cloud的版本都会包含不同的子项目版本，为了要管理每个版本的子项目清单，避免版本名与子项目的发布号混淆，所以没有采用版本号的方式，而是通过命名的方式。

这些版本名字采用了伦敦地铁站的名字，根据字母表的顺序来对应版本时间顺序，比如：最早的Release版本：Angel，第二个Release版本：Brixton，以此类推……

- 版本号

之前所提到的`Angel.SR6`，`Brixton.SR5`中的SR6、SR5就是版本号了。当一个版本的Spring Cloud项目的发布内容积累到临界点或者一个严重bug解决可用后，就会发布一个“service releases”版本，简称SRX版本，其中X是一个递增数字。







































