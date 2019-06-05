# IDEA使用技巧总结

## 1. 快捷键

* Alt+Shift+R    (Refactor | Rename)
* Ctrl+Alt+T    surrounded with try catch
* Alt + Insert  （generate get and set）
* Ctrl + T    查看类的子类





## 2. 必备设置

### 2-1. 快捷键设置

File -> Setting -> Keymap







## 3. 开发设置

### 3-1. SpringBoot热部署设置

* 1) File/Setting/Build,Execution,Deployment/Compiler, 勾选Build project automatically

* 2) 组合键：Shift+ALT+Ctrl+/ ，选择“Registry”，回车，找到“complier.automake.allow.when.app.running” 勾选

* 3) pom.xml文件配置

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

  

## 4. 使用插件

### 4-1. ignore插件

**安装**

File-->Settings-->Plugins

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/06/20190603202534.png)



**使用**

点中项目右键，新建`.gitignore`文件

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/06/20190603203356.png)

### 4-2. Lombok Plugin





### 4-3. MyBatisX

















