---
title: 玩转Spring系列-初识Spring
date: 2019-02-23 12:29:10
category: "玩转Spring"
tags: Spring
---

# 初识Spring

## 课程介绍

* 初识Spring
    - Spring主要成员
    - Spring技术趋势
    - 编写第一个Spring程序
* 数据操作
    - JDBC必知必会
    - O/R Mapping
    - NoSQL实践
    - 数据访问进阶
* Web开发
    - Spring MVC
    - Web开发进阶
    - 访问Web资源
* SpringBoot
    - 自动配置原理及实现
    - 起步依赖原理及定制
    - 配置文件加载机制
    - 获取运行状态
    - 配置运行容器
    - 可执行Jar背后的密码
* Spring Cloud
    - 云原生和微服务
    - 服务注册、发现、熔断与配置
    - Spring Cloud Stream
    - 服务链路追踪

## Spring家族主要成员

诞生于2002年，成型于2003年，最早作者Rod Johnson。
目前已发展到Spring 5.x版本, 支持JDK8-11

### Spring始于框架，但不限于框架

[Spring 主要项目](http://spring.io/projects)

![image](玩转Spring系列-初识Spring/Spring0101.png)

* Spring Boot: 构建(Build)
* Spring Cloud: 协调(Coordinate)
* Spring Cloud Data Flow: 连接(Connect)

### Spring Framework: 用于构建企业级应用的轻量级一站式解决方案
![image](玩转Spring系列-初识Spring/Spring0102.png)

### Spring Boot: 快速构建基于Spring的应用程序，进可开箱即用，退可按需改动
![image](玩转Spring系列-初识Spring/Spring0103.png)

### Spring Cloud: 简化分布式系统的开发
![image](玩转Spring系列-初识Spring/Spring0104.png)

## Spring技术趋势

通过 Release Note看技术趋势

意外收获:
JasperReport是一个强大、灵活的报表生成工具，能够展示丰富的页面内容，并将之转换成PDF，HTML，或者XML格式。该库完全由Java写成，可以用于在各种Java应用程序，包括J2EE，Web应用程序中生成动态内容。

Portlet是基于Java的Web组件，由Portlet容器管理，并由容器处理请求，生产动态内容。Portals使用Portlets作为可插拔用户接口组件，提供信息系统的表示层。作为利用Servlets进行Web应用编程的下一步，Portlets实现了Web应用的模块化和用户中心化。

Velocity是一个基于Java的模板引擎。它允许任何人使用简单而强大的模板语言来引用Java代码中定义的对象。

WebFlux: Spring WebFlux是基于响应式流的，因此可以用来建立异步的、非阻塞的、事件驱动的服务。它采用Reactor作为首选的响应式流的实现库，不过也提供了对RxJava的支持

## 编写第一个Spring程序

* 借助IDE
* [Spring 生成工具页面](https://start.spring.io/)

Terminal运行/请求

```shell
打包:
mvn clean package -Dmaven.test.skip

请求:
curl http://localhost:8080/hello
```
