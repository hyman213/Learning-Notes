## 常用命令

[TOC]

### 全局命令
```shell
keys *    [查看所有键]    keys命令会遍历所有键，它的时间复杂度是O（n）
dbsize    [键总数]        获取Redis内置的键总数变量，所以dbsize命令的时间复杂度是O（1）
exists key    [检查键是否存在]
del key    [删除键]
dump key     //序列化
expire key seconds    [添加过期时间]
expire key timestamp
ttl key    [键剩余过期时间]    >=0,剩余过期时间s; -1,键没设置过期时间; -2键不存在
type key    [键的数据结构类型]    string/list/none...
move key db     //当前数据库的key移动到给定数据库db中
persist key     //移除key的过期时间，key将持久保持
randomkey     //随机返回一个key
rename key newkey     //修改key的名称
incr num     //值增加
多数据库
select n     (n是数字，默认是0)

[迁移键]
migrate host port key|"" destination-db timeout [copy] [replace] [keys key [key
```

### 数据结构和内部编码

每种数据结构都有自己底层的内部编码实现，而且是多种实现，这样Redis会在合适的场景选择合适的内部编码

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/08/20190819111054.png)

Redis这样设计有两个好处：

第一，**可以改进内部编码**，而对外的数据结构和命令没有影响，这样一旦开发开发出优秀的内部编码，无需改动外部数据结构和命令。

第二，**多种内部编码实现可以在不同场景下发挥各自的优势**。例如ziplist比较节省内存，但是在列表元素比较多的情况下，性能会有所下降，这时候Redis会根据配置选项将列表类型的内部实现转换为linkedlist。

```shell
object encoding key    [查看key的内部编码]
type key [当前键的数据结构类型，string/hash/list/set/zset]
```

### String(字符串)

键都是字符串类型,字符串类型的值实际可以是字符串（简单的字符串、复杂的字符串（例如JSON、XML））、数字（整数、浮点数），甚至是二进制（图片、音频、视频），但是值最大不能超过512MB。

```shell
set key value [EX seconds] [PX milliseconds] [NX|XX]    [设置值]
·    ex seconds：为键设置秒级过期时间。setex
·    px milliseconds：为键设置毫秒级过期时间。
·    nx：键必须不存在，才可以设置成功，用于添加。setnx,setnx可以作为分布式锁的一种实现方案
·    xx：与nx相反，键必须存在，才可以设置成功，用于更新。
get key    [获取值]
mset key value [key value ...]    [批量设置值]
mget key [key ... ]                [批量获取值]
incr key    [计数自增]
decr key    [计数自减]
incrby key increment    [自增指定数字]
decrby key decrement    [自减指定数字]
incrbyfloat key increment    [自增浮点数]

append key value    [追加值]
strlen key    [字符串长度]
getset key value    [设置并返回原值]
setrange key offset value    [设置指定位置的字符,从0开始]
getrange key start end    [获取部分字符串,从0开始]


3种内部编码
- int：8个字节的长整型
- embstr: 小于等于39个字节的字符串
- raw：大于39个字节的字符串
```

### Hash(哈希)
Redis hash是一个string类型的field和value的映射表，hash特别适合用于存储对象。每个 hash 可以存储 2^32 -1 键值对（40多亿）

```shell
hset key field value    [设置值],亦hsetnx
hget key field    [获取值]
hdel key field    [删除field]
hlen key    [计算field个数]
hmget key [field ..]    [批量获取]
hmset key field value [field value ...]    [批量设置]
hexists key field    [判断field是否存在]
hkeys ksy    [获取所有field]
hvals key    [获取所有value]
hgetall key    [获取所有field-value]
hincrby hincrbyfloat    [自增]
hstrlen key field    [字符串长度]

hash 2种内部编码
- ziplist（压缩列表）：当哈希类型元素个数小于hash-max-ziplist-entries配置（默认512个）、同时所有值都小于hash-max-ziplist-value配置（默认64字节）时，Redis会使用ziplist作为哈希的内部实现，ziplist使用更加紧凑的结构实现多个元素的连续存储，所以在节省内存方面比hashtable更加优秀
- hashtable（哈希表）：当哈希类型无法满足ziplist的条件时，Redis会使用hashtable作为哈希的内部实现，因为此时ziplist的读写效率会下降，而hashtable的读写时间复杂度为O（1）
注意：当一个哈希的编码由ziplist变为hashtable的时候，即使在替换掉所有值，它一直都会是hashtable类型
```

哈希类型和关系型数据库有两点不同之处：

- 哈希类型是稀疏的，而关系型数据库是完全结构化的，例如哈希类型每个键可以有不同的field，而关系型数据库一旦添加新的列，所有行都要为其设置值（即使为NULL）
- 关系型数据库可以做复杂的关系查询，而Redis去模拟关系型复杂查询开发困难，维护成本高

### list列表

列表中的每个字符串称为元素（element），一个列表最多可以存储2^32-1个元素.插入（push）和弹出（pop）

```shell
rpush lpush linsert    [添加]
lrange lindex llen    [查],索引下标从左到右分别是0到N-1，但是从右到左分别是-1到-N
lpop rpop lrem ltrim    [删]
lset    [修改]
blpop brpop    [阻塞操作]

lpush key value [value]
rpush key value [value]
lrange key start stop


list 2种内部编码：
- ziplist（压缩列表）：当列表的元素个数小于list-max-ziplist-entries配置（默认512个），同时列表中每个元素的值都小于list-max-ziplist-value配置时（默认64字节），Redis会选用ziplist来作为列表的内部实现来减少内存的使用。
- linkedlist（链表）：当列表类型无法满足ziplist的条件时，Redis会使用linkedlist作为列表的内部实现。

lpush+lpop=Stack（栈）
lpush+rpop=Queue（队列）
lpsh+ltrim=Capped Collection（有限集合）
lpush+brpop=Message Queue（消息队列）
```

### set集合

集合中不允许有重复元素，并且集合中的元素是无序的，不能通过索引下标获取元素。Redis除了支持集合内的增删改查，同时还支持多个集合取交集、并集、差集

```shell
sadd key element [element ...]    [添加]
srem key element [element ...]    [删除]
scard key    [计算元素个数]
sismember key element    [判断元素是否在集合中]
srandmember key [count]    [随机从集合返回指定个数元素]
spop key    [从集合随机弹出元素]
    注：srandmember和spop都是随机从集合选出元素，两者不同的是spop命令执行后，元素会从集合中删除，而srandmember不会。
smembers key    [获取所有元素]
集合间操作：
sinter key [key ...]    [集合间交集]
suinon key [key ...]    [集合间并集]
sdiff key [key ...]    [集合间差集]
将交集、并集、差集的结果保存：
sinterstore destination key [key ...] 
suionstore  destination key [key ...] 
sdiffstore  destination key [key ...]

注意：同一member添加了两次，但根据集合内元素的唯一性，第二次插入的元素将被忽略.返回0

2种内部编码：
- intset（整数集合）：当集合中的元素都是整数且元素个数小于set-max-intset-entries配置（默认512个）时，Redis会选用intset来作为集合的内部实现，从而减少内存的使用
- hashtable（哈希表）：当集合类型无法满足intset的条件时，Redis会使用hashtable作为集合的内部实现。
```

### zset有序集合
不能有重复成员的特性，有序集合中的元素可以排序。每个元素都会关联一个double类型的分数。zset的成员是唯一的,但分数(score)却可以重复。

```shell
zadd key score member [score member ...]    [添加]
    zadd命令添加了nx、xx、ch、incr四个选项：
    ·    nx：member必须不存在，才可以设置成功，用于添加。
    ·    xx：member必须存在，才可以设置成功，用于更新。
    ·    ch：返回此次操作后，有序集合元素和分数发生变化的个数
    ·    incr：对score做增加，相当于后面介绍的zincrby。
zcard key    [计算成员个数]
zscore key member    [计算某个成员的分数]
zrank key member     [计算成员的排名，从低到高]
zrevrank key member
zrem key member [member ...]    [删除成员]
zincrby key increment member    [增加成员的分数]
zrange    key start end [withscores]     [返回指定排名范围内的成员]
zrevrange key start end [withscores]
zrangebyscore    key min max [withscores] [limit offset count]     [返回指定分数范围的成员]
zrevrangebyscore key max min [withscores] [limit offset count]
zcount key min max    [返回指定分数范围成员个数]
zremrangebyrank key start end    [删除指定排名内的升序元素]
zremrangebyscore key min max    [删除指定分数范围的成员]

集合间操作：
zinterstore destination numkeys key [key ...] [weights weight [weight ...]] [aggregate sum|min|max]
- destination：交集计算结果保存到这个键。
- numkeys：需要做交集计算键的个数。
- key[key...]：需要做交集计算的键。
- weights weight[weight...]：每个键的权重，在做交集计算时，每个键中的每个member会将自己分数乘以这个权重，每个键的权重默认是1。
- aggregate sum|min|max：计算成员交集后，分值可以按照sum（和）、min（最小值）、max（最大值）做汇总，默认值是sum。

zunionstore destination numkeys key [key ...] [weights weight [weight ...]] [aggregate sum|min|max]

举例：
zadd key score member [score member]
ZRANGE scorelis 0 100
ZRANGEBYSCORE scorelis 0 100

127.0.0.1:6379> zadd scorelis 89 coco
(integer) 1
127.0.0.1:6379> zadd scorelis 98 his
(integer) 1
127.0.0.1:6379> zadd scorelis 100 lucy
(integer) 1
127.0.0.1:6379> zadd scorelis 60 tom
(integer) 1
127.0.0.1:6379> ZRANGE scorelis 0 100
1) "tom"
2) "coco"
3) "his"
4) "lucy"

2种内部编码
- ziplist（压缩列表）：当有序集合的元素个数小于zset-max-ziplist-entries配置（默认128个），同时每个元素的值都小于zset-max-ziplist-value配置（默认64字节）时，Redis会用ziplist来作为有序集合的内部实现，ziplist可以有效减少内存的使用
- skiplist（跳跃表）：当ziplist条件不满足时，有序集合会使用skiplist作为内部实现，因为此时ziplist的读写效率会下降。
```

### HyperLogLog

```shell
Commands：
PFADD key element [element ...]     添加
PFCOUNT key [key ...]               计算基数估算值
PFMERGE destkey sourcekey [sourcekey ...]     将多个HyperLogLog合并为一个
```

### 发布订阅

```shell
Commands:
publish channael massage     将消息发送到指定频道
subscribe channel [channel]     订阅给定的一个或多个频道的信息
unsubscribe channel [channel]     退订
pubsub subcommand [argumnet..]     查看订阅与发布系统状态
```

### 事务

一个事务从开始到执行会经历以下三个阶段：
- 开始事务。
- 命令入队。
- 执行事务

```shell
Commands:
multi     标记事务块的开始     begin
exex     执行所有事务块内的命令     commit
discard     取消事务     refer to rollback in RDBMS
unwatch     取消watch命令对所有key的监视
watch key [key ..]     监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断
```

### 管理脚本




### 连接

```shell
# 验证密码是否正确
AUTH password
# 打印字符串
ECHO message
# 服务是否运行
PING
# 关闭当前连接
QUIT
# 切换到指定数据库
SELECT index
```