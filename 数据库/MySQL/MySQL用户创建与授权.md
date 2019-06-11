# MySQL用户管理-创建与授权

## 创建/删除用户

**语法**

```sql
-- 创建用户
CREATE USER 'username'@'host' IDENTIFIED BY 'password';
-- 删除用户
DROP USER 'username'@'host';
-- 查看用户信息
SELECT * FROM mysql.USER WHERE USER = 'username';
```

**说明**

- `username`, 创建的用户名
- `host`, 指定允许登录的主机，本地可以使用`localhost`, 允许任意主机登录可以使用通配符`%`
- `password`， 用户登录的密码

**示例**

```sql
-- 创建
CREATE USER 'develop'@'%' IDENTIFIED BY '123456';
CREATE USER 'develop'@'192.168.1.101' IDENTIFIED BY '123456';
-- 删除
DROP USER 'develop'@'%';
```

## 用户授权

**语法**

```sql
-- 授权
GRANT privileges ON databasename.tablename TO 'username'@'host';
-- 如果想让授权的用户，也可以将这些权限 grant 给其他用户，需要选项 “grant option“
GRANT privileges ON databasename.tablename TO 'username'@'host' WITH GRANT OPTION;
-- 撤销授权
REVOKE privilege ON databasename.tablename FROM 'username'@'host';
-- 查看权限
SHOW GRANTS; -- 当前用户
SHOW GRANTS FOR root@localhost; -- 指定用户
```

**说明**

- `privileges`, 用户的操作权限，如`SELECT`，`INSERT`，`UPDATE`等，如果要授予所的权限则使用`ALL`
- `databasename`， 数据库名
- `tablename`， 表名， 如果要授予该用户对所有数据库和表的相应操作权限则可用`*`表示，如`*.*`
- `grant`, `revoke` 用户权限后，该用户只有重新连接 MySQL 数据库，权限才能生效。如果是命令行, 则需要执行`flush privileges;`

**示例**

```sql
-- grant 普通数据用户，查询、插入、更新、删除 数据库中所有表数据的权利
grant select, insert, update, delete on testdb.* to common_user@'%'

-- grant 数据库开发人员 创建、修改、删除 MySQL 数据表结构权限。
grant create on testdb.* to developer@'192.168.0.%';
grant alter on testdb.* to developer@'192.168.0.%';
grant drop on testdb.* to developer@'192.168.0.%';

-- grant 操作 MySQL 外键权限
grant references on testdb.* to developer@'192.168.0.%';
-- grant 操作 MySQL 索引权限
grant index on testdb.* to developer@'192.168.0.%';
-- grant 操作 MySQL 视图、查看视图源代码 权限。
grant create view on testdb.* to developer@'192.168.0.%';
grant show view on testdb.* to developer@'192.168.0.%';

-- grant 操作 MySQL 存储过程、函数 权限。
grant create routine on testdb.* to developer@'192.168.0.%'; -- now, can show procedure status
grant alter routine on testdb.* to developer@'192.168.0.%'; -- now, you can drop a procedure
grant execute on testdb.* to developer@'192.168.0.%';

-- grant 普通 DBA 管理某个 MySQL 数据库的权限。其中，关键字 “privileges” 可以省略。
grant all privileges on testdb to dba@'localhost'

-- grant 高级 DBA 管理 MySQL 中所有数据库的权限
grant all on *.* to dba@'localhost'

-- 撤销权限（revoke 跟 grant 的语法差不多，只需要把关键字 “to” 换成 “from” 即可）
revoke all on *.* from dba@localhost;

```

## 设置与更改用户密码

**语法**

```sql
SET PASSWORD FOR 'username'@'host' = PASSWORD('newpassword');
-- 当前用户
SET PASSWORD = PASSWORD("newpassword");

-- 命令行
UPDATE mysql.user SET password=password('新密码') WHERE User="test" AND Host="localhost";
FLUSH PRIVILEGES;
```

**说明**

- `newpassword`， 设置的新密码

**示例**

```sql
SET PASSWORD FOR 'pig'@'%' = PASSWORD("123456");
```









