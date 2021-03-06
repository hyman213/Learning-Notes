---
title: Git学习笔记
date: 2019-05-07 21:37:56
tags: Git
category: Tools
toc_number: false
---



Git是分布式版本控制系统，Git中每个人的电脑就是一个完整的版本库，当多人协作时，只需把各自的修改推送给对方。

<!--more-->

## 1. Git是什么

Git是分布式版本控制系统
官网:[https://git-scm.com/](https://git-scm.com/)

### 1-1. 工作原理 / 流程

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502000850.png)

* Workspace: 工作区
* Index / Stage: 暂存区
* Repository: 仓库区（或本地仓库）
* Remote: 远程仓库

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502002525.png)

### 1-2. SVN与Git的主要区别

SVN-集中式版本控制系统，版本库放在中央服务器。以中央服务器的版本为基准，干活前需要先同步中央服务器得到最新的版本，自己本地的更新也需要推送到中央服务器。

Git是分布式版本控制系统，Git中每个人的电脑就是一个完整的版本库，当多人协作时，只需把各自的修改推送给对方。

## 2. Git安装使用

### 2-1. Git安装

参照Git官网下载安装,安装完成后鼠标右键菜单多了2个选项

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502145216.png)

### 2-2. Git初始化用户信息

设置用户全局配置：

```shell
git config --global user.name "[name]"
Sets the name you want to attached to your transactions

git config --global user.email "[email address]"
Sets the email you want to attached to your transactions

git config --global color.ui auto
Enables helpful colorization of command line output
```

查看用户信息配置

```shell
git config user.name
git config user.email
```

### 2-3. GitHub配置SSH公钥

```shell
ssh-keygen -t rsa -C '[email address using in github]'
```

* 在Git Bash输入以上命令后，回车后再按三个回车(默认存储位置，密码为空，确认密码)。

* 将上一步生成的公钥路径：C:\Users\你的用户名\\.ssh。id_rsa是私钥，不能泄露出去，id_rsa.pub是公钥，可以放心地告诉任何人。

* 将生成的公钥(id_rsa.pub)添加到GitHub:选择头像-->Settings-->SSH and GPG keys

### 2-4. [Git GUI Clients](https://git-scm.com/downloads/guis)

* GitHub Desktop
* TortoiseGit
* Git Extensions
* ...

## 3. Git管理代码仓库

### 3-1. 创建版本库

Repository可以理解为一个目录，这个目录里面的所有文件都可以被Git管理起来。

```shell
$ mkdir testgit
$ cd testgit/
$ git init
Initialized empty Git repository in D:/WorkSpaces/testgit/.git/
```

执行上面的操作，gittest就成为了一个Git仓库。在testgit目录下自动新增了一个.git文件夹

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502150628.png)

新建readme.txt文件并提交

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502151123.png)

继续修改readme.txt文件后，提交修改

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502152011.png)

发现每次都会有warning: LF will be replaced by CRLF in readme.txt.提示。

原因是不同操作系统中换行符的标识不同

* CRLF->Windows-style

* LF->Unix Style

* CR->Mac Style

可通过如下命令进行转换设置

```shell
git config --global core.autocrlf true/false/inout
```

* true，添加文件到git仓库时，git将其视为文本文件。他将把crlf变成lf
* false，line-endings将不做转换操作。文本文件保持原来的样子。
* input，添加文件git仓库石，git把crlf编程lf。当有人Check代码时还是lf方式。因此在window操作系统下，不要使用这个设置

### 3-2 版本回退

继续修改readme.txt文件，新增"33333333"并提交

查看修改的历史记录

```shell
git log
git log --pretty=oneline
git reflog
```

git log 与git reflog的区别

1) git log: commit 的版本日志 包含提交的版本 操作者 日期 (方便查看commit的版本,但是版本回退后,使用git log 看不到回退版本号之后的版本记录) 

2) git reflog: 使用git 命令进行操作的日志  包括当前步骤所在哪个版本(一个commit 产生一个版本, 指定版本回退只能回退到该commit) 以及操作的具体内容

版本回退后,仍然可以看到所有的版本记录 方便查看每个操作步骤所在的版本,可以根据版本号自由前进后退

版本回退操作

```shell
git reset --hard HEAD^
git reset --hard HEAD^^
git reset --hard HEAD~100
git reset --hard '版本号'
```

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502155810.png)

### 3-3. 撤销和删除文件

#### 3-3-1. 撤销修改

```shell
# 丢弃工作区的修改。--很重要，没有--的话命令就变成创建分支了
git checkout -- file
```

有2种情况：

* 文件修改后，还没有放到暂存区，使用撤销修改就回到和版本库一模一样的状态
* 另外一种是文件的修改已经放入暂存区了，接着又作了修改，撤销修改就回到添加暂存区后的状态

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502162025.png)

#### 3-3-2. 删除文件

删除文件后再commit到git，如果删除并提交到暂存区后，可执行checkout操作恢复删除的文件

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502162924.png)



## 4. 远程仓库

### 4-1. 注册GitHub，并在本地创建SSH添加到GitHub 

操作参照步骤2-3

### 4-2. 添加远程库

#### 4-2-1. 本地先有仓库，然后关联到远程库

* 1. 在GitHub上创建仓库

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502171256.png)

* 2. 在本地仓库目录下运行命令

```shell
git remote add origin https://github.com/hyman213/testgit.git
# 第一次推送
git push -u origin master
# 之后的推送
git push origin master
```

我们第一次推送master分支时，加上了 –u参数，Git不但会把本地的master分支内容推送的远程新的master分支，还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时就可以简化命令。



![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502171627.png)

此时本地仓库的内容就上传到了远程库

再次修改本地库的readme.txt文件，测试上传更新到远程库

```shell
$ vi readme.txt
$ git status
$ git add readme.txt
$ git commit -m '增加了55555555和66666666'
$ git push origin master
```

刷新GitHub上内容，内容与本地库一致，本地内容成功推送到可远程库

#### 4-2-2. 先有远程仓库

* 1. 在GitHub上创建仓库testgit2

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502172525.png)

* 2. 在本地clone远程库的内容, 注意clone下来内容会在当前路径生成新文件夹testgit2

```shell
$ git clone https://github.com/hyman213/testgit2.git
```

* 3. 测试在本地增加readme.txt文件，并上传推送到远程库

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502173248.png)



## 5. 分支-branch

> 每次提交，Git都把它们串成一条时间线，这条时间线就是一个分支。截止到目前，只有一条时间线，在Git里，这个分支叫主分支，即master分支。HEAD严格来说不是指向提交，而是指向master，master才是指向提交的，所以，HEAD指向的就是当前分支



用到的命令有

```shell
# 创建分支dev
git branch dev
# 切换到dev分支
git checkout dev
# 上面两个命令合并
git checkout -b dev
# 查看分支(列出所有分支,当前分支会添加一个星号)
git branch
# 合并分支(将dev分支上的更新合并到当前分支)
git merge dev
# 删除分支
git branch -d dev
```



### 5-1. 创建分支



![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502223958.png)

切换到dev分支，然后在dev分支上进行修改。

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502225423.png)

切换到master分支，查看readme.txt文件。内容没有修改

### 5-2. 合并/删除分支

将dev分支上的修改合并到master分支上

![1556808994739](C:\Users\idobe\AppData\Roaming\Typora\typora-user-images\1556808994739.png)

通常合并分支时，git一般使用"Fast forward"模式，在这种模式下，删除分支后，会丢掉分支信息，现在我们来使用带参数 –no-ff来禁用”Fast forward”模式

禁用”Fast forward”模式合并分支

```shell
git merge --no-ff -m "merge with mo-ff" dev
```

### 5-3. 解决分支冲突

新建分支branch1,在branch1分支和master分支分别修改readme.txt文件并提交。然后将branch1分支的修改合并到master分支。

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190502231618.png)

查看冲突并解决冲突后，再次提交文件

![1556810416998](C:\Users\idobe\AppData\Roaming\Typora\typora-user-images\1556810416998.png)

### 5-4. 分支管理

首先master主分支应该是非常稳定的，也就是用来发布新版本，一般情况下不允许在上面干活，干活一般情况下在新建的dev分支上干活，干完后，比如上要发布，或者说dev分支代码稳定后可以合并到主分支master上来

> bug分支: 在开发中，会经常碰到bug问题，那么有了bug就需要修复，在Git中，分支是很强大的，每个bug都可以通过一个临时分支来修复，修复完成后，合并分支，然后将临时的分支删除掉。

在dev分支修改readme.txt的内容，然后紧急有个bug需要修复, 需要新增分支在master基础上进行修复。需要先将dev分支上的修改隐藏

```shell
# 将当前分支的修改隐藏
git stash
# 列出隐藏的修改
git stash list
# 恢复隐藏的修改
git stash apply
# 删除stash
git stash drop
# 恢复的同时删除
git stash pop
```

## 6. 多人协作

### 6-1. 查看远程库信息

```shell
git remote
# 详细信息
git remote -v
```

### 6-2. 推送分支

推送分支就是把该分支上所有本地提交到远程库中，推送时，要指定本地分支

```shell
# 推送master分支
git push origin master
# 推送dev分支
git push origin dev
```

修改dev分支的内容，并将dev分支推送到远程库，结果远程库会新增dev分支

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190503000207.png)



gitHub上新增分支dev

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/20190503000251.png)



master分支是主分支，因此要时刻与远程同步。

一些修复bug分支不需要推送到远程去，可以先合并到主分支上，然后把主分支master推送到远程去。

### 6-3. 抓取分支

将远程的origin的dev分支同步到本地

```shell
git checkout -b dev origin/dev
```

现在就可以在dev分支上做开发了，开发完成后把dev分支推送到远程库。

```shell
git push origin dev
```

抓取远程仓库

```shell
git pull
# 指定den分支与远程origin/dev分支的链接
git branch --set-upstream-to dev origin/dev
```



多人协作工作模式一般是这样的：

- 首先，可以试图用git push origin branch-name推送自己的修改.
- 如果推送失败，则因为远程分支比你的本地更新早，需要先用git pull试图合并。
- 如果合并有冲突，则需要解决冲突，并在本地提交。再用git push origin branch-name推送。



## 7. GitHub-Git Cheat Sheet

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/05/20190507214432.png)

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/05/20190507214558.png)



[通过游戏的方式学习Git](https://learngitbranching.js.org/)

