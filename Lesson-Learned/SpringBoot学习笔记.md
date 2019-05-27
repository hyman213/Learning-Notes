



## LessonLearned

### 项目部署

#### 两个springboot项目war包部署到一个tomcat，其中一个启动失败

方案一：在application.properties配置文件中添加配置：

```xml
spring.jmx.enabled=false
```

方案二：在两个项目各自配置文件中添加配置：

```xml
spring.jmx.default-domain=project1
spring.jmx.default-domain=project2
```

保证两个domain是不一样的。