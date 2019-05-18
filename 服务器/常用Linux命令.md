# 常用Linux命令

## 一、系统类

### 1. crontab命令

#### 1-1. 语法
crontab命令被用来提交和管理用户的需要周期性执行的任务，与windows下的计划任务类似

> 语法:

```bash
crontab(选项)(参数)

选项：
-e 编辑当前用户的cron服务
-l 列出当前用户的cron服务
-r 删除当前用户的cron服务
-u<用户名称> 指定要设定的用户名称,root账户下
```

#### 1-2. crond服务

```bash
# 查看crontab服务状态
service crond status
# 手动启动crontab服务
service crond start
```

#### 1-3. 系统任务调度

系统周期性所要执行的工作，比如写缓存数据到硬盘、日志清理等。在`/etc`目录下有一个crontab文件，这个就是系统任务调度的配置文件`/etc/crontab`

#### 1-4. 用户任务调度

```bash
## Example of job definition:
.---------------- minute (0 - 59)
|  .------------- hour (0 - 23)
|  |  .---------- day of month (1 - 31)
|  |  |  .------- month (1 - 12) OR jan,feb,mar,apr ...
|  |  |  |  .---- day of week (0 - 6) (Sunday=0 or 7) OR sun,mon,tue,wed,thu,fri,sat
|  |  |  |  |
*  *  *  *  *  command to be executed

几个特殊符号：
星号（*）：代表所有可能的值，例如month字段如果是星号，则表示在满足其它字段的制约条件后每月都执行该命令操作。
逗号（,）：可以用逗号隔开的值指定一个列表范围，例如，“1,2,5,7,8,9”
中杠（-）：可以用整数之间的中杠表示一个整数范围，例如“2-6”表示“2,3,4,5,6”
正斜线（/）：可以用正斜线指定时间的间隔频率，例如“0-23/2”表示每两小时执行一次。同时正斜线可以和星号一起使用，例如*/10，如果用在minute字段，表示每十分钟执行一次。

```

#### 1-5. 示例

```bash
# 每1分钟执行一次command
* * * * * command
# 每小时的第3和第15分钟执行
3,15 * * * * command
# 在上午8点到11点的第3和第15分钟执行
3,15 8-11 * * * command
# 每小时执行一次
* */1 * * * command
# 每天晚上11点到早上7点，每小时执行一次
* 23-7/1 * * * command
```

### 2. xargs命令







### 3. find命令

































