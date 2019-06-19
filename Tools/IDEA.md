# IDEA

## 安装破解

参考:
[IntelliJ IDEA Ultimate 2018.5 中文激活特别版](https://www.jb51.net/softs/598467.html)

注册码获取[http://idea.lanyus.com](http://idea.lanyus.com)

## 快捷键


| 编号 |              功能              |      快捷键       |
| :--: | :----------------------------: | :---------------: |
|  1   |            自动补全            |     Alt+Enter     |
|  2  |            全文搜索            | Shift（快速双击） |
|  3  | 代码生成（get/set/overried..） |    Alt+insert     |
|  4  |            surrounded with try catch            |    Ctrl+Alt+T     |
|  5  |             重命名             |     Alt+Shift+R      |
|  6  |            查看类的子类            |       Ctrl + T     |




## 常用设置

* 快捷键设置
  1. File -> Setting -> Keymap（习惯使用Eclipse）
     1. 格式化代码 - ReformatCode - Ctrl + Shift + F || Ctrl + Alt + L。选中文件、文件夹右键ReFormat Code
* 字体
  1. 编辑区字体： File-->Settings-->Editor-->Font
  2. 控制台字体： File-->Settings-->Editor-->Color Scheme-->Console Font
* 编码
  1.  File-->Settings-->Editor-->File Encodings
* maven
  1. File-->Settings-->Build,Execution,Deployment-->Build Tools-->Maven
* 插件
  1. File-->Settings-->Plugins

参考：

[字体、编码设置](https://blog.csdn.net/frankcheng5143/article/details/50779149)
[常用设置](https://blog.csdn.net/yelove1990/article/details/51541327)
[快捷键](https://blog.csdn.net/shijiebei2009/article/details/44725733)





## 开发设置

### SpringBoot热部署设置

1. File/Setting/Build,Execution,Deployment/Compiler, 勾选Build project automatically

2. 组合键：Shift+ALT+Ctrl+/ ，选择“Registry”，回车，找到“complier.automake.allow.when.app.running” 勾选

3. pom.xml文件配置


  ```xml
  <!--热部署插件-->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <optional>true</optional>
  </dependency>
  
  <build>
      <plugins>
          <plugin>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
              <!--热部署配置必须-->
              <configuration>
                  <fork>true</fork>
              </configuration>
          </plugin>
      </plugins>
  </build>
  ```

  

### @Autowired报错

IDEA差错功能过于强大,关闭检查Editor-Inspector-Spring-Spring Core-Code-AutoWiring for Bean class



## 使用插件

### ignore插件

**安装**

File-->Settings-->Plugins

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/06/20190603202534.png)



**使用**

点中项目右键，新建`.gitignore`文件

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/06/20190603203356.png)

### Lombok Plugin

**安装**

打开IDEA的Setting –> 选择Plugins选项 –> 选择Browse repositories –> 搜索lombok –> 点击安装 –> 安装完成重启IDEA –> 安装成功

**使用**

项目使用lombok插件时需引入依赖

```xml
<dependency> 
    <groupId>org.projectlombok</groupId> 
    <artifactId>lombok</artifactId> 
    <version>${lombok.version}</version> 
    <scope>provided</scope> 
</dependency>
```

更多使用技巧参考

### MyBatisX

















