---
title: 玩转Spring系列-NoSQL实践
date: 2019-03-25 21:31:13
category: "玩转Spring"
tags: Spring
---

## Docker辅助开发

### 认识Docker

### Docker常用命令

#### 镜像相关

* docker pull <image>
* docker search <image>


#### 容器相关

* docker run
* docker start/stop <容器名>
* docker ps <容器名>
* docker logs <容器名>

#### Docker run的常用选项
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
选项说明
* -d, 后台运行容器
* -e, 设置环境变量
* --expose / -p 宿主端口：容器端口
* --name, 指定容器名称
* --link, 链接不同容器
* -v 宿主目录：容器目录，挂载磁盘卷

#### 国内Docker镜像配置

官方Docker Hub(https://hub.docker.com/)
官方镜像
https://www.docker-cn.com/registry-mirror

阿里云镜像


## Spring中访问MongoDB

## Spring中访问Redis

```shell
# 获取镜像
docker pull redis
# 启动redis
docker run --name redis -d -p 6379:6379 redis
```




## Redis的哨兵与集群模式


## 了解Spring的缓存抽象


## Redis在Spring中的其他用法


## SpringBucks 实战项目进度小结


