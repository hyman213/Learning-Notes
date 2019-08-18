# Docker命令大全

[TOC]



## 容器生命周期管理

### docker run

创建一个新的容器并运行一个命令

```shell
$ docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
OPTIONS说明：

-a stdin: 指定标准输入输出内容类型，可选 STDIN/STDOUT/STDERR 三项；

-d: 后台运行容器，并返回容器ID；

-i: 以交互模式运行容器，通常与 -t 同时使用；

-P: 随机端口映射，容器内部端口随机映射到主机的高端口

-p: 指定端口映射，格式为：主机(宿主)端口:容器端口

-t: 为容器重新分配一个伪输入终端，通常与 -i 同时使用；

--name="nginx-lb": 为容器指定一个名称；

--dns 8.8.8.8: 指定容器使用的DNS服务器，默认和宿主一致；

--dns-search example.com: 指定容器DNS搜索域名，默认和宿主一致；

-h "mars": 指定容器的hostname；

-e username="ritchie": 设置环境变量；

--env-file=[]: 从指定文件读入环境变量；

--cpuset="0-2" or --cpuset="0,1,2": 绑定容器到指定CPU运行；

-m :设置容器使用内存最大值；

--net="bridge": 指定容器的网络连接类型，支持 bridge/host/none/container: 四种类型；

--link=[]: 添加链接到另一个容器；

--expose=[]: 开放一个端口或一组端口；

--volume , -v:	绑定一个卷
```



### docker start/stop/restart

**docker start** :启动一个或多个已经被停止的容器

**docker stop** :停止一个运行中的容器

**docker restart** :重启容器

### docker kill

杀掉一个运行中的容器

```shell
$ docker kill [OPTIONS] CONTAINER [CONTAINER...]
OPTIONS说明：
-s :向容器发送一个信号
```

### docker rm

删除一个或多少容器

```shell
$ docker rm [OPTIONS] CONTAINER [CONTAINER...]

OPTIONS说明：

-f :通过SIGKILL信号强制删除一个运行中的容器

-l :移除容器间的网络连接，而非容器本身

-v :-v 删除与容器关联的卷
```

### docker pause/unpause

**docker pause** :暂停容器中所有的进程。

**docker unpause** :恢复容器中所有的进程。

### docker create

创建一个新容器但不启动它。语法同`docker run`

```shell
$ docker create [OPTIONS] IMAGE [COMMAND] [ARG...]

```

### docker exec

在运行的容器中执行命令

```shell
$ docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
OPTIONS说明：

-d :分离模式: 在后台运行

-i :即使没有附加也保持STDIN 打开

-t :分配一个伪终端
```



## 容器操作

### docker ps

列出容器

```shell
$ docker ps [OPTIONS]

# OPTIONS说明
-a :显示所有的容器，包括未运行的。

-f :根据条件过滤显示的内容。 docker ps --filter"name=test-nginx"

--format :指定返回值的模板文件。

-l :显示最近创建的容器。

-n :列出最近创建的n个容器。docker ps -n 5

--no-trunc :不截断输出。

-q :静默模式，只显示容器编号。 docker ps -a -q

-s :显示总的文件大小。
```

### docker inspect

获取容器/镜像的元数据

```shell
$ docker inspect [OPTIONS] NAME|ID [NAME|ID...]
OPTIONS说明：

-f :指定返回值的模板文件。

-s :显示总的文件大小。

--type :为指定类型返回JSON。
```

### docker top

**docker top :**查看容器中运行的进程信息，支持 ps 命令参数。

```shell
$ docker top [OPTIONS] CONTAINER [ps OPTIONS]
# 容器运行时不一定有/bin/bash终端来交互执行top命令，而且容器还不一定有top命令，可以使用docker top来实现查看container中正在运行的进程。

# 查看所有运行程序的进程信息
$ for i in  `docker ps |grep Up|awk '{print $1}'`;do echo \ &&docker top $i; done
```

### docker attach

**docker attach :**连接到正在运行中的容器。

```shell
$ docker attach [OPTIONS] CONTAINER
```

### docker events

**docker events :** 从服务器获取实时事件

```shell
$ docker events [OPTIONS]

OPTIONS说明：

-f ：根据条件过滤事件；

--since ：从指定的时间戳后显示所有事件;

--until ：流水时间显示到指定的时间为止；

# docker events  --since="1467302400"
```

### docker logs

**docker logs :** 获取容器的日志

```shell
docker logs [OPTIONS] CONTAINER

OPTIONS说明：

-f : 跟踪日志输出

--since :显示某个开始时间的所有日志

-t : 显示时间戳

--tail :仅列出最新N条容器日志
```

### docker wait

阻塞运行直到容器停止，然后打印出它的退出代码。

```shell
$ docker wait [OPTIONS] CONTAINER [CONTAINER...]
```

### docker export

将文件系统作为一个tar归档文件导出到STDOUT

```shell
$ docker export [OPTIONS] CONTAINER
OPTIONS说明：

-o :将输入内容写到文件

# docker export -o mysql-`date +%Y%m%d`.tar a404c6c174a2
```

### docker port

列出指定的容器的端口映射，或者查找将PRIVATE_PORT NAT到面向公众的端口

```shell
$ docker port [OPTIONS] CONTAINER [PRIVATE_PORT[/PROTO]]
```

## 容器rootfs命令

### docker commit

从容器创建一个新的镜像

```shell
$ docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]]
OPTIONS说明：

-a :提交的镜像作者；
-c :使用Dockerfile指令来创建镜像；
-m :提交时的说明文字；
-p :在commit时，将容器暂停。

# 将容器a404c6c174a2 保存为新的镜像,并添加提交人信息和说明信息。
# docker commit -a "runoob.com" -m "my apache" a404c6c174a2  mymysql:v1
```

### docker cp

用于容器与主机之间的数据拷贝

```shell
docker cp [OPTIONS] CONTAINER:SRC_PATH DEST_PATH|-
docker cp [OPTIONS] SRC_PATH|- CONTAINER:DEST_PATH
# OPTIONS说明：
-L :保持源目标中的链接

# 将主机/www/runoob目录拷贝到容器96f7f14e99ab的/www目录下
$ docker cp /www/runoob 96f7f14e99ab:/www/
```

### docker diff

检查容器里文件结构的更改

```shell
docker diff [OPTIONS] CONTAINER
```



## 镜像仓库

### docker login

**docker login :** 登陆到一个Docker镜像仓库，如果未指定镜像仓库地址，默认为官方仓库 Docker Hub

**docker logout :** 登出一个Docker镜像仓库，如果未指定镜像仓库地址，默认为官方仓库 Docker Hub

```shell
$ docker login [OPTIONS] [SERVER]
$ docker logout [OPTIONS] [SERVER]

# PTIONS说明：
-u :登陆的用户名
-p :登陆的密码

# docker login -u 用户名 -p 密码
# docker logout
```

### docker pull

从镜像仓库中拉取或者更新指定镜像

```shell
$ docker pull [OPTIONS] NAME[:TAG|@DIGEST]
# OPTIONS说明：
-a :拉取所有 tagged 镜像
--disable-content-trust :忽略镜像的校验,默认开启
```

### docker push

将本地的镜像上传到镜像仓库,要先登陆到镜像仓库

```shell
$ docker push [OPTIONS] NAME[:TAG]
# OPTIONS说明：
--disable-content-trust :忽略镜像的校验,默认开启

# 上传本地镜像myapache:v1到镜像仓库中。
# docker push myapache:v1
```

### docker search

从Docker Hub查找镜像

```shell
$ docker search [OPTIONS] TERM
# OPTIONS说明：
--automated :只列出 automated build类型的镜像；
--no-trunc :显示完整的镜像描述；
-s :列出收藏数不小于指定值的镜像。

# 从Docker Hub查找所有镜像名包含java，并且收藏数大于10的镜像
# docker search -s 10 java
```



## 本地镜像管理

### docker images

列出本地镜像

```shell
$ docker images [OPTIONS] [REPOSITORY[:TAG]]

# OPTIONS说明：
-a :列出本地所有的镜像（含中间映像层，默认情况下，过滤掉中间映像层）；
--digests :显示镜像的摘要信息；
-f :显示满足条件的镜像；
--format :指定返回值的模板文件；
--no-trunc :显示完整的镜像信息；
-q :只显示镜像ID。
```

### docker rmi

```shell
$ docker rmi [OPTIONS] IMAGE [IMAGE...]
# OPTIONS说明
-f :强制删除；
--no-prune :不移除该镜像的过程镜像，默认移除
```

### docker tag

标记本地镜像，将其归入某一仓库

```shell
$ docker tag [OPTIONS] IMAGE[:TAG] [REGISTRYHOST/][USERNAME/]NAME[:TAG]

# 将镜像ubuntu:15.10标记为 runoob/ubuntu:v3 镜像
# docker tag ubuntu:15.10 runoob/ubuntu:v3
```

### docker build

使用 Dockerfile 创建镜像

```shell
$ docker build [OPTIONS] PATH | URL | -

# OPTIONS说明
--build-arg=[] :设置镜像创建时的变量；
--cpu-shares :设置 cpu 使用权重；
--cpu-period :限制 CPU CFS周期；
--cpu-quota :限制 CPU CFS配额；
--cpuset-cpus :指定使用的CPU id；
--cpuset-mems :指定使用的内存 id；
--disable-content-trust :忽略校验，默认开启；
-f :指定要使用的Dockerfile路径；
--force-rm :设置镜像过程中删除中间容器；
--isolation :使用容器隔离技术；
--label=[] :设置镜像使用的元数据；
-m :设置内存最大值；
--memory-swap :设置Swap的最大值为内存+swap，"-1"表示不限swap；
--no-cache :创建镜像的过程不使用缓存；
--pull :尝试去更新镜像的新版本；
--quiet, -q :安静模式，成功后只输出镜像 ID；
--rm :设置镜像成功后删除中间容器；
--shm-size :设置/dev/shm的大小，默认值是64M；
--ulimit :Ulimit配置。
--tag, -t: 镜像的名字及标签，通常 name:tag 或者 name 格式；可以在一次构建中为一个镜像设置多个标签。
--network: 默认 default。在构建期间设置RUN指令的网络模式

# 使用当前目录的 Dockerfile 创建镜像
$ docker build -t runoob/ubuntu:v1 . 
# 使用URL github.com/creack/docker-firefox 的 Dockerfile 创建镜像
$ docker build github.com/creack/docker-firefox
# 也可以通过 -f Dockerfile 文件的位置：
$ docker build -f /path/to/a/Dockerfile .
```

### docker history

查看指定镜像的创建历史

```shell
$ docker history [OPTIONS] IMAGE
# OPTIONS说明
-H :以可读的格式打印镜像大小和日期，默认为true；
--no-trunc :显示完整的提交记录；
-q :仅列出提交记录ID。
```

### docker save

将指定镜像保存成 tar 归档文件

```shell
$ docker save [OPTIONS] IMAGE [IMAGE...]
# OPTIONS 说明
-o :输出到的文件。
```

### docker load

导入使用docker save命令导出的镜像

```shell
$ docker load [OPTIONS]
# OPTIONS 说明
-i :指定导出的文件。
-q :精简输出信息。
```

### docker import

从归档文件中创建镜像

```shell
$ docker import [OPTIONS] file|URL|- [REPOSITORY[:TAG]]
# OPTIONS说明
-c :应用docker 指令创建镜像；
-m :提交时的说明文字；
```

## info|version

### docker info

显示 Docker 系统信息，包括镜像和容器数

```shell
$ docker info [OPTIONS]
```

### docker version

显示 Docker 版本信息

```shell
$ docker version [OPTIONS]
```























