# 运行中的SpringBoot

## 认识Spring Boot的各类Actuator Endpoint

**Actuator**

目的：监控并管理应用程序

访问方式：HTTP / JMX(JDK自带`jconsole.exe`)

依赖： `spring-boot-starter-actuator`

**[一些常用的Endpoint](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/production-ready-endpoints.html#production-ready-endpoints-enabling-endpoints)**

| ID                 | Description                                                  | Enabled by default |
| ------------------ | ------------------------------------------------------------ | ------------------ |
| `auditevents`      | Exposes audit events information for the current application. | Yes                |
| `beans`            | Displays a complete list of all the Spring beans in your application. | Yes                |
| `caches`           | Exposes available caches.                                    | Yes                |
| `conditions`       | Shows the conditions that were evaluated on configuration and auto-configuration classes and the reasons why they did or did not match. | Yes                |
| `configprops`      | Displays a collated list of all `@ConfigurationProperties`.  | Yes                |
| `env`              | Exposes properties from Spring’s `ConfigurableEnvironment`.  | Yes                |
| `flyway`           | Shows any Flyway database migrations that have been applied. | Yes                |
| `health`           | Shows application health information.                        | Yes                |
| `httptrace`        | Displays HTTP trace information (by default, the last 100 HTTP request-response exchanges). | Yes                |
| `info`             | Displays arbitrary application info.                         | Yes                |
| `integrationgraph` | Shows the Spring Integration graph.                          | Yes                |
| `loggers`          | Shows and modifies the configuration of loggers in the application. | Yes                |
| `liquibase`        | Shows any Liquibase database migrations that have been applied. | Yes                |
| `metrics`          | Shows ‘metrics’ information for the current application.     | Yes                |
| `mappings`         | Displays a collated list of all `@RequestMapping` paths.     | Yes                |
| `scheduledtasks`   | Displays the scheduled tasks in your application.            | Yes                |
| `sessions`         | Allows retrieval and deletion of user sessions from a Spring Session-backed session store. Not available when using Spring Session’s support for reactive web applications. | Yes                |
| `shutdown`         | Lets the application be gracefully shutdown.                 | No                 |
| `threaddump`       | Performs a thread dump.                                      | Yes                |

**Exposing Endpoints**

| ID                                                 | JMX  | Web  |
| -------------------------------------------------- | ---- | ---- |
| `auditevents`                                      | Yes  | No   |
| `beans`  容器中的Bean列表                          | Yes  | No   |
| `caches` 应用中的缓存                              | Yes  | No   |
| `conditions` 配置条件的计算情况                    | Yes  | No   |
| `configprops` 显示`@ConfigurationProperties`的信息 | Yes  | No   |
| `env` 显示ConfigurableEnvironment中的属性          | Yes  | No   |
| `flyway`                                           | Yes  | No   |
| `health` 健康信息                                  | Yes  | Yes  |
| `heapdump`                                         | N/A  | No   |
| `httptrace` HTTP Trace信息                         | Yes  | No   |
| `info` 设置好的应用信息                            | Yes  | Yes  |
| `integrationgraph`                                 | Yes  | No   |
| `jolokia`                                          | N/A  | No   |
| `logfile`                                          | N/A  | No   |
| `loggers`                                          | Yes  | No   |
| `liquibase`                                        | Yes  | No   |
| `metrics`                                          | Yes  | No   |
| `mappings`                                         | Yes  | No   |
| `prometheus`                                       | N/A  | No   |
| `scheduledtasks`                                   | Yes  | No   |
| `sessions`                                         | Yes  | No   |
| `shutdown`                                         | Yes  | No   |
| `threaddump`                                       | Yes  | No   |

```xml
默认的配置
management.endpoints.jmx.exposure.exclude=*
management.endpoints.jmx.exposure.include=*
management.endpoints.web.exposure.exclude=*
management.endpoints.web.exposure.include=info,health

// to stop exposing all endpoints over JMX and only expose the health and info endpoints, use the following property
management.endpoints.jmx.exposure.include=health,info
// can be used to select all endpoints. For example, to expose everything over HTTP except the env and beans endpoints, use the following properties:
management.endpoints.web.exposure.include=*
management.endpoints.web.exposure.exclude=env,beans
```



**应用**

引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

// 如果需要HTTP访问
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

配置

```xml
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

// yml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```





## Spring Boot自带的Health Indicator

| Name                                                         | Description                                               |
| ------------------------------------------------------------ | --------------------------------------------------------- |
| [`CassandraHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/cassandra/CassandraHealthIndicator.java) | Checks that a Cassandra database is up.                   |
| [`CouchbaseHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/couchbase/CouchbaseHealthIndicator.java) | Checks that a Couchbase cluster is up.                    |
| [`DiskSpaceHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/system/DiskSpaceHealthIndicator.java) | Checks for low disk space.                                |
| [`DataSourceHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/jdbc/DataSourceHealthIndicator.java) | Checks that a connection to `DataSource` can be obtained. |
| [`ElasticsearchHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/elasticsearch/ElasticsearchHealthIndicator.java) | Checks that an Elasticsearch cluster is up.               |
| [`InfluxDbHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/influx/InfluxDbHealthIndicator.java) | Checks that an InfluxDB server is up.                     |
| [`JmsHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/jms/JmsHealthIndicator.java) | Checks that a JMS broker is up.                           |
| [`MailHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/mail/MailHealthIndicator.java) | Checks that a mail server is up.                          |
| [`MongoHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/mongo/MongoHealthIndicator.java) | Checks that a Mongo database is up.                       |
| [`Neo4jHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/neo4j/Neo4jHealthIndicator.java) | Checks that a Neo4j server is up.                         |
| [`RabbitHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/amqp/RabbitHealthIndicator.java) | Checks that a Rabbit server is up.                        |
| [`RedisHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/redis/RedisHealthIndicator.java) | Checks that a Redis server is up.                         |
| [`SolrHealthIndicator`](https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/solr/SolrHealthIndicator.java) | Checks that a Solr server is up.                          |

**数据库健康检查**

`org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator`

```java
@Override
protected void doHealthCheck(Health.Builder builder) throws Exception {
    if (this.dataSource == null) {
        builder.up().withDetail("database", "unknown");
    }
    else {
        doDataSourceHealthCheck(builder);
    }
}


private void doDataSourceHealthCheck(Health.Builder builder) throws Exception {
    String product = getProduct();
    builder.up().withDetail("database", product);
    String validationQuery = getValidationQuery(product);
    if (StringUtils.hasText(validationQuery)) {
        // Avoid calling getObject as it breaks MySQL on Java 7
        List<Object> results = this.jdbcTemplate.query(validationQuery,
                                                       new SingleColumnRowMapper());
        Object result = DataAccessUtils.requiredSingleResult(results);
        builder.withDetail("hello", result);
    }
}
```



**磁盘空间检查**

`org.springframework.boot.actuate.system.DiskSpaceHealthIndicator`

```java
@Override
protected void doHealthCheck(Health.Builder builder) throws Exception {
    long diskFreeInBytes = this.path.getUsableSpace();
    if (diskFreeInBytes >= this.threshold) {
        builder.up();
    }
    else {
        logger.warn(String.format(
            "Free disk space below threshold. "
            + "Available: %d bytes (threshold: %d bytes)",
            diskFreeInBytes, this.threshold));
        builder.down();
    }
    builder.withDetail("total", this.path.getTotalSpace())
        .withDetail("free", diskFreeInBytes)
        .withDetail("threshold", this.threshold);
}
```



## 自定义Health Indicator

- 实现HealthIndicator接口
- 根据自定义检查逻辑返回对应Health状态
  - Health中包含状态和详细描述信息

```java
import geektime.spring.springbucks.waiter.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CoffeeIndicator implements HealthIndicator {
    @Autowired
    private CoffeeService coffeeService;

    @Override
    public Health health() {
        long count = coffeeService.getCoffeeCount();
        Health health;
        if (count > 0) {
            health = Health.up()
                    .withDetail("count", count)
                    .withDetail("message", "We have enough coffee.")
                    .build();
        } else {
            health = Health.down()
                    .withDetail("count", 0)
                    .withDetail("message", "We are out of coffee.")
                    .build();
        }
        return health;
    }
}
```



## Micrometer - 获取运行数据































