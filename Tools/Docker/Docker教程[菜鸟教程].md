# Docker教程

## Docker简介

Docker 是一个开源的应用容器引擎，基于 Go 语言 并遵从Apache2.0协议开源。

Docker 可以让开发者打包他们的应用以及依赖包到一个轻量级、可移植的容器中，然后发布到任何流行的 Linux 机器上，也可以实现虚拟化。

容器是完全使用沙箱机制，相互之间不会有任何接口（类似 iPhone 的 app）,更重要的是容器性能开销极低。

Docker 从 17.03 版本之后分为 CE（Community Edition: 社区版） 和 EE（Enterprise Edition: 企业版）

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/08/20190815172258.png)

**应用场景**

- Web 应用的自动化打包和发布。
- 自动化测试和持续集成、发布。
- 在服务型环境中部署和调整数据库或其他的后台应用。
- 从头编译或者扩展现有的 OpenShift 或 Cloud Foundry 平台来搭建自己的 PaaS 环境。

**Docker优点**

- 简化程序

Docker 让开发者可以打包他们的应用以及依赖包到一个可移植的容器中，然后发布到任何流行的 Linux 机器上，便可以实现虚拟化。Docker改变了虚拟化的方式，使开发者可以直接将自己的成果放入Docker中进行管理。

- 避免选择恐惧症

Docker 镜像中包含了运行环境和配置，所以 Docker 可以简化部署多种应用实例工作。

- 节省开支

一方面，云计算时代到来，使开发者不必为了追求效果而配置高额的硬件，Docker 改变了高性能必然高价格的思维定势。Docker 与云的结合，让云空间得到更充分的利用。不仅解决了硬件管理的问题，也改变了虚拟化的方式。

**Docker 架构**

Docker 使用客户端-服务器 (C/S) 架构模式，使用远程API来管理和创建Docker容器。

Docker 容器通过 Docker 镜像来创建。

容器与镜像的关系类似于面向对象编程中的对象与类。

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/08/20190815173207.png)

| Docker 镜像(Images)    | Docker 镜像是用于创建 Docker 容器的模板。                    |
| ---------------------- | ------------------------------------------------------------ |
| Docker 容器(Container) | 容器是独立运行的一个或一组应用。                             |
| Docker 客户端(Client)  | Docker 客户端通过命令行或者其他工具使用 Docker API (<https://docs.docker.com/reference/api/docker_remote_api>) 与 Docker 的守护进程通信。 |
| Docker 主机(Host)      | 一个物理或者虚拟的机器用于执行 Docker 守护进程和容器。       |
| Docker 仓库(Registry)  | Docker 仓库用来保存镜像，可以理解为代码控制中的代码仓库。Docker Hub([https://hub.docker.com](https://hub.docker.com/)) 提供了庞大的镜像集合供使用。 |
| Docker Machine         | Docker Machine是一个简化Docker安装的命令行工具，通过一个简单的命令行即可在相应的平台上安装Docker，比如VirtualBox、 Digital Ocean、Microsoft Azure。 |

## Docker安装

### Windows安装



### CentOS安装

目前，CentOS 仅发行版本中的内核支持 Docker。

Docker 运行在 CentOS 7 上，要求系统为64位、系统内核版本为 3.10 以上。

Docker 运行在 CentOS-6.5 或更高的版本的 CentOS 上，要求系统为64位、系统内核版本为 2.6.32-431 或者更高版本。

查看内核版本命令

```shell
uname -r
3.10.0-957.1.3.el7.x86_64
```

**使用yum安装**

从 2017 年 3 月开始 docker 在原来的基础上分为两个分支版本: Docker CE 和 Docker EE。

Docker CE 即社区免费版，Docker EE 即企业版，强调安全，但需付费使用。

移除旧版本

```shell
$ yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine
```

安装必要的系统工具

```shell
yum install -y yum-utils device-mapper-persistent-data lvm2
```

添加软件源信息

```shell
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

更新yun缓存

```shell
yum makecache fast
```

安装Docker-ce

```shell
yum -y install docker-ce
```

启动Docker后台服务

```shell
systemctl start docker
```

测试运行hello-world

```shell
docker run hello-world
```

**使用脚本安装**

1、使用root或sudo权限登录CentOS

2、确保yum包更新到最新

```shell
sudo yum update
```

3、执行Docker安装脚本

```shell
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh
```

4、启动Docker进程

```shell
sudo systemctl start docker
```

5、验证是否安装成功，并在容器中执行hello-world

```shell
sudo docker run hello-world
docker ps
```

**镜像加速**

新版的 Docker 使用 /etc/docker/daemon.json（Linux） 或者 %programdata%\docker\config\daemon.json（Windows） 来配置 Daemon。

请在该配置文件中加入（没有该文件的话，请先建一个）：

```json
{
  "registry-mirrors": ["http://hub-mirror.c.163.com"]
}
```

**删除镜像**

```shell
sudo yum remove docker-ce
sudo rm -rf /var/lib/docker
```



## Docker 使用

### Hello World

**使用`docker run`命令在容器里运行一个应用程序**

```shell
# Docker 以 ubuntu15.10 镜像创建一个新容器，然后在容器里执行 bin/echo "Hello world"，然后输出结果。
docker run ubuntu:15.10 /bin/echo "Hello world"
```

**运行交互式容器**

```shell
docker run -i -t ubuntu:15.10 /bin/bash

# 参数解析
-t:在新容器内指定一个伪终端或终端。
-i:允许你对容器内的标准输入 (STDIN) 进行交互

执行以上命令后，我们已进入一个 ubuntu15.10系统的容器，如果需要退出，使用exit命令即可
```

**启动容器(后台模式)**

```shell
# 创建一个以进程方式运行的容器
docker run -d ubuntu:15.10 /bin/sh -c "while true; do echo hello world; sleep 1; done"

[root@instance-74slrqsw docker]# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS               NAMES
5604a0618b8c        ubuntu:15.10        "/bin/sh -c 'while t…"   6 seconds ago       Up 5 seconds                            fervent_proskuriakova
# 显示的长字符串叫容器ID, 容器唯一标识，
# 通过docker ps查看运行中的容器
CONTAINER ID:容器ID
NAMES:自动分配的容器名称

docker logs 容器ID/名称
```

**停止容器**

```shell
docker stop 容器ID/名称
```

### Docker 容器

```shell
# 查看所有docker命令
docker
# 查看指定命令的用法
docker command --help
```

运行web容器

```shell
# 我们将在docker容器中运行一个 Python Flask 应用来运行一个web应用。
docker pull training/webapp  # 载入镜像
docker run -d -P training/webapp python app.py

# 参数说明
-d 容器在后台运行
-P 将容器内部使用的网络端口映射到我们使用的主机上
```

查看web容器

```shell
docker ps
# 端口信息
如，0.0.0.0:32769->5000/tcp，表示Docker 开放了 5000 端口（默认 Python Flask 端口）映射到主机端口 32769 上。

# 我们可以通过-p参数设置不一样的端口, 容器内部的 5000 端口映射到我们本地主机的 5000 端口上。
docker run -d -p 5000:5000 training/webapp python app.py

# 查看容器的端口映射
docker port ID/名字
```

查看Web应用程序日志

```shell
docker logs -f ID/名字

# 查看进程
docker top ID/名字
```

停止、重启、移除容器

```shell
# 停止
docker stop ID/名字
# 启动
docker start ID/名字
# 重启
docker restart ID/名字
# 移除，移除时必须是停止状态
docker rm ID/名字
```

### Docker镜像

当运行容器时，使用的镜像如果在本地中不存在，docker 就会自动从 docker 镜像仓库中下载，默认是从 Docker Hub 公共镜像源下载。

**列出镜像列表**

```shell
[root@instance docker]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
hello-world         latest              fce289e99eb9        7 months ago        1.84kB
ubuntu              15.10               9b9cb95443b5        3 years ago         137MB
training/webapp     latest              6fae60ef3446        4 years ago         349MB
```

各个选项说明:

- **REPOSITORY：**表示镜像的仓库源
- **TAG：**镜像的标签
- **IMAGE ID：**镜像ID
- **CREATED：**镜像创建时间
- **SIZE：**镜像大小

同一仓库源可以有多个 TAG，代表这个仓库源的不同个版本，如ubuntu仓库源里，有15.10、14.04等多个不同的版本，我们使用 REPOSITORY:TAG 来定义不同的镜像。如果不指定一个镜像的版本标签，docker 将默认使用 ubuntu:latest 镜像。

**其他命令**

```shell
# 获取镜像
docker pull REPOSITORY:TAG
例：docker pull ubuntu:13.10

# 查找镜像
docker search REPOSITORY
例：docker search mysql

NAME:镜像仓库源的名称
DESCRIPTION:镜像的描述
OFFICIAL:是否docker官方发布

# 拖取镜像
docker pull REPOSITORY

# 使用镜像
docker run REPOSITORY

# 创建镜像
当我们从docker镜像仓库中下载的镜像不能满足我们的需求时，我们可以通过以下两种方式对镜像进行更改。
1.从已经创建的容器中更新镜像，并且提交这个镜像
2.使用 Dockerfile 指令来创建一个新的镜像

# 提交容器副本
docker commit -m="has update" -a="runoob" e218edb10161 runoob/ubuntu:v2
sha256:70bf1840fd7c0d2d8ef0a42a817eb29f854c1af8f7c59fc03ac7bdee9545aff8
参数说明：
-m 提交的描述信息
-a 作者
e218edb10161 容器ID
runoob/ubuntu:v2 镜像名

# 构建镜像
runoob@runoob:~$ docker build -t runoob/centos:6.7 .
Sending build context to Docker daemon 17.92 kB
Step 1 : FROM centos:6.7
 ---&gt; d95b5ca17cc3
Step 2 : MAINTAINER Fisher "fisher@sudops.com"
 ---&gt; Using cache
 ---&gt; 0c92299c6f03
Step 3 : RUN /bin/echo 'root:123456' |chpasswd
 ---&gt; Using cache
 ---&gt; 0397ce2fbd0a
Step 4 : RUN useradd runoob
......

参数说明：
-t 指定要创建的目标镜像名
. Dockerfile文件所在目录，可以指定Dockerfile的绝对路径

# Dockerfile示例,创建一个 Dockerfile 文件，其中包含一组指令来告诉 Docker 如何构建我们的镜像
FROM    centos:6.7
MAINTAINER      Fisher "fisher@sudops.com"

RUN     /bin/echo 'root:123456' |chpasswd
RUN     useradd runoob
RUN     /bin/echo 'runoob:123456' |chpasswd
RUN     /bin/echo -e "LANG=\"en_US.UTF-8\"" >/etc/default/local
EXPOSE  22
EXPOSE  80
CMD     /usr/sbin/sshd -D

说明：
1.每一个指令都会在镜像上创建一个新的层，每一个指令的前缀都必须是大写的。
2.第一条FROM，指定使用哪个镜像源
3.RUN 指令告诉docker 在镜像内执行命令，安装了什么。。。

# 设置镜像标签 - 使用 docker tag 命令，为镜像添加一个新的标签
docker tag 860c279d2fec runoob/centos:dev
用户名称、镜像源名(repository name)和新的标签名(tag)。

# 删除镜像
docker rmi [OPTIONS] IMAGE[IMAGE...]
-f --force=false 强制删除镜像(无需询问)
--no-prune=false 保留被删除镜像中未打标签的父镜像
```



### Docker容器连接

**网络端口映射**

```shell
docker run -d -P 6fae60ef3446 python app.py
docker run -d -p 5000:5000 6fae60ef3446 python app.py

-P:是容器内部端口随机映射到主机的高端口
-p:是容器内部端口绑定到指定的主机端口

如果要绑定 UDP 端口，可以在端口后面加上 /udp
docker run -d -p 127.0.0.1:5000:5000/udp training/webapp python app.py

# 查看端口绑定情况
docker port training/webapp 5000
0.0.0.0:5000
```

**Docker容器连接**

端口映射并不是唯一把 docker 连接到另一个容器的方法。

docker 有一个连接系统允许将多个容器连接在一起，共享连接信息。

docker 连接会创建一个父子关系，其中父容器可以看到子容器的信息。

```shell
# 容器命名，当我们创建一个容器的时候，docker 会自动对它进行命名。另外，我们也可以使用 --name 标识来命名容器
docker run -d -P --name runoob training/webapp python app.py
```

## Docker实例

### 安装Nginx

**安装**

```shell
# 查找
docker search nginx
# 拉取
docker pull nginx
# 查看本地镜像
docker images
# 启动nginx容器: --name指定容器名，-p本地8081端口映射到容器内部80端口,-d容器后台运行。容器启动后，即可通过ip+port(8081)即可请求Nginx
docker run --name hyman-nginx -p 8081:80 -d nginx

注：
建议启动容器时，指定容器的名字，以便后面可直接通过【docker start 容器名】启动容器
```

**部署**

```shell
# 创建目录nginx，用于存放nginx相关数据 
mkdir -p /usr/local/nginx/page /usr/local/nginx/logs /usr/local/nginx/conf

- page：将映射为 nginx 容器配置的虚拟目录
- logs：将映射为 nginx 容器的日志目录
- conf：将映射为 nginx 容器的配置文件

# 拷贝容器内 Nginx 默认配置文件到本地当前目录下的 conf 目录
docker cp hyman-nginx:/etc/nginx/nginx.conf /usr/local/nginx/conf/

# 部署nginx
docker run -d -p 8082:80 --name nginx-web -v /usr/local/nginx/page/html:/usr/share/nginx/html -v /usr/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /usr/local/nginx/logs:/var/log/nginx nginx

参数说明
-p 8082:80： 将容器的 80 端口映射到主机的 8082 端口
--name nginx-web 容器名
-v /usr/local/nginx/page/html:/usr/share/nginx/html 将我们自己创建的 page/html目录挂载到容器的 /usr/share/nginx/html
-v /usr/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf 将我们自己创建的 nginx.conf 挂载到容器的 /etc/nginx/nginx.conf
-v /usr/local/nginx/logs:/var/log/nginx 将我们自己创建的 logs 挂载到容器的 /var/log/nginx

# 测试Nginx
cd /usr/local/nginx/page/html
# 创建index.html后再访问nginx，就可以看到index的内容
vi index.html

# 重新载入nginx可以使用以下命令发送 HUP 信号到容器
$ docker kill -s HUP container-name
# 重启nginx容器
docker restart nginx-web
```

### PHP

**安装**

```shell
docker search php
docker pull php:5.6-fpm
docker images
# 运行docker
docker run --name php-fpm -v /usr/local/nginx/page/php:/php -d php:5.6-fpm
# 参数说明
--name php-fpm 容器名php-fpm
-v /usr/local/nginx/page/php:/php 将主机中项目的目录php挂载到容器的 /php
```

**Nginx+PHP部署**

```shell

$ mkdir conf.d
$ vi php.conf
server {
listen       80;
server_name  localhost;

location / {
root   /usr/share/nginx/html;
index  index.html index.htm index.php;
}

error_page   500 502 503 504  /50x.html;
location = /50x.html {
root   /usr/share/nginx/html;
}

location ~ \.php$ {
fastcgi_pass   php:9000;
fastcgi_index  index.php;
fastcgi_param  SCRIPT_FILENAME  /php/$fastcgi_script_name;
include        fastcgi_params;
}
}

# 参数说明
php:9000 表示 php-fpm 服务的 URL
/php/ 是php-fpm中php文件的存储路径，映射到本地的/usr/local/nginx/page/php

# 启动nginx
docker run --name php-nginx -p 8083:80 -d -v /usr/local/nginx/page/php:/usr/share/nginx/html:ro -v /usr/local/nginx/conf/conf.d:/etc/nginx/conf.d:ro --link php-fpm:php nginx

-p 8083:80: 端口映射，把 nginx 中的 80 映射到本地的 8083 端口。
/usr/local/nginx/page/php:/usr/share/nginx/html:ro 本地与容器内存储文件映射
/usr/local/nginx/conf/conf.d:/etc/nginx/conf.d:ro 本地与容器内配置文件映射
php-fpm:php 把 php-fpm 的网络并入 nginx，并通过修改 nginx 的 /etc/hosts，把域名 php 映射成 127.0.0.1，让 nginx 通过 php:9000 访问 php-fpm。

# 接下来我们在/usr/local/nginx/page/php 目录下创建 index.php
<?php
echo phpinfo();
?>

# 浏览器通过IP+PORT(8083)请求, 就可以看到index.php的内容
```



### MySQL

```shell
# 下载5.6版本mysql
docker pull mysql:5.6

# 如果没有创建，也没有关系，运行成功后会自动创建
mkdir -p ~/mysql/data ~/mysql/logs ~/mysql/conf
data目录将映射为mysql容器配置的数据文件存放路径
logs目录将映射为mysql容器的日志目录
conf目录里的配置文件将映射为mysql容器的配置文件
# 运行mysql
$ cd /usr/local/mysql
$ docker run -p 3306:3306 --name mysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=Hyman213# -d mysql:5.6

[参数说明]
-p 3306:3306：将容器的 3306 端口映射到主机的 3306 端口。
-v $PWD/conf:/etc/mysql/conf.d：将主机当前目录下的 conf/my.cnf 挂载到容器的 /etc/mysql/my.cnf
-v $PWD/logs:/logs：将主机当前目录下的 logs 目录挂载到容器的 /logs
-v $PWD/data:/var/lib/mysql ：将主机当前目录下的data目录挂载到容器的 /var/lib/mysql 
-e MYSQL_ROOT_PASSWORD=Hyman213#：初始化 root 用户的密码

注意：
1.安装中发现启动成功后，一段时间会自动关闭。可能是内存不足的问题，需要将其他容器暂时关闭
2.如果安装失败，再重新启动容器之前，最好将mysql文件夹中的conf,logs,data删除


# 进入容器，并添加远程登录用户
$ docker exec -it mysql /bin/bash
$ mysql -u root -p
mysql> CREATE USER 'hyman'@'%' IDENTIFIED BY 'Hyman213#';
mysql> GRANT ALL PRIVILEGES ON *.* TO 'hyman'@'%';

```



**MYSQL 8最新版**

```shell
# docker 中下载 mysql
docker pull mysql
# 启动
docker run --name mysql8 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=Hyman213# -d mysql
# 进入容器
docker exec -it mysql8 bash
# 登录MYSQL
mysql -uroot -p
# 添加远程登录用户
CREATE USER 'hyman'@'%' IDENTIFIED WITH mysql_native_password BY 'Hyman213#';
GRANT ALL PRIVILEGES ON *.* TO 'hyman'@'%';
```

### Tomcat

```shell
# 拉取Tomcat
$ docker pull tomcat
$ docker images
# 运行容器
$ docker run -i -t tomcat /bin/bash
# 查看Tomcat版本信息
$ ./bin/version.sh

# 运行Tomcat容器
docker run --name tomcat -p 8091:8080 -v $PWD/test:/usr/local/tomcat/webapps/test -d tomcat

-p 8091:8080：将容器的8080端口映射到主机的8091端口
-v $PWD/test:/usr/local/tomcat/webapps/test：将主机中当前目录下的test挂载到容器的/test

# 浏览器通过IP+PORT(8091)请求, 就可以看到Tomcat欢迎页
```





















