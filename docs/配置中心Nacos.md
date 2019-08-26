# 配置中心Nacos

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [启动配置管理](#启动配置管理)
* [支持自定义 namespace 的配置](#支持自定义-namespace-的配置)
* [支持自定义 Group 的配置](#支持自定义-group-的配置)
* [定义扩展的 Data Id 配置](#定义扩展的-data-id-配置)
* [配置的优先级](#配置的优先级)
* [完全关闭配置](#完全关闭配置)
* [实战演示](#实战演示)
	* [命名空间](#命名空间)
	* [配置列表](#配置列表)
	* [项目配置](#项目配置)

<!-- /code_chunk_output -->

## 启动配置管理

1. 引入依赖

   ```xml
   <dependencyManagement>
     <dependencies>
       <dependency>
         <groupId>com.alibaba.cloud</groupId>
         <artifactId>spring-cloud-alibaba-dependencies</artifactId>
         <version>2.1.0.RELEASE</version>
         <type>pom</type>
         <scope>import</scope>
       </dependency>
     </dependencies>
   </dependencyManagement>
   ```

   ```xml
   <dependency>
     <groupId>com.alibaba.cloud</groupId>
     <artifactId>spring-cloud-alibaba-nacos-config</artifactId>
     <version>2.1.0.RELEASE</version>
   </dependency>
   ```

2. 在`bootstrap.properties`或`bootstrap.yml`，配置Nacos server 的地址和应用名

   ```yml
   spring:
     application:
       name: @pom.artifactId@
     cloud:
       nacos:
         config:
           server-addr: 127.0.0.1:8848
           file-extension: yaml
   ```

   > 如果使用`@pom.artifactId@`的写法，则，`spring.application.name=@pom.artifactId@`必须配置在`application.yml`文件中，否则无法读取到pom文件的值

   之所以需要配置 `spring.application.name` ，是因为它是构成 Nacos 配置管理 `dataId`字段的一部分。

   在 Nacos Spring Cloud 中，`dataId` 的完整格式如下：

   ```plain
   ${prefix}-${spring.profile.active}.${file-extension}
   ```

   - `prefix` 默认为 `spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。

     ```properties
   spring.application.name=xxx
     spring.cloud.nacos.config.prefix=yyy
     ```

   - `spring.profile.active` 即为当前环境对应的 profile，详情可以参考 [Spring Boot文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html#boot-features-profiles)。

     > 注意：当 spring.profile.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 `${prefix}.${file-extension}`

     ```properties
     spring.profiles.active=product
     ```

   - `file-exetension` 为配置内容的数据格式，可以通过配置项 `spring.cloud.nacos.config.file-extension` 来配置。目前只支持 `properties` 和 `yaml` 类型。

3. 通过 Spring Cloud 原生注解 `@RefreshScope` 实现配置自动更新：

   ```java
   @RestController
   @RefreshScope
   public class NacosConfigController {
       @Value("${user.name:kk}")
       private String name;
       @Value("${user.age:11}")
       private Integer age;

       @GetMapping("/user")
       public String getUserInfo(){
           return String.format("%s is %s years old this year.", name,age);
       }
   }
   ```

## 支持自定义 namespace 的配置

用于进行租户粒度的配置隔离。不同的命名空间下，可以存在相同的 Group 或 Data ID 的配置。Namespace 的常用场景之一是不同环境的配置的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等。

在没有明确指定 `${spring.cloud.nacos.config.namespace}` 配置的情况下， 默认使用的是 Nacos 上 Public 这个namespae。如果需要使用自定义的命名空间，可以通过以下配置来实现：

```properties
spring.cloud.nacos.config.namespace=b3404bc0-d7dc-4855-b519-570ed34b62d7
```

> 该配置必须放在 bootstrap.properties 文件中。此外 `spring.cloud.nacos.config.namespace` 的值是 namespace 对应的 id，id 值可以在 Nacos 的控制台获取。并且在添加配置时注意不要选择其他的 namespae，否则将会导致读取不到正确的配置。

## 支持自定义 Group 的配置

在没有明确指定 `${spring.cloud.nacos.config.group}` 配置的情况下， 默认使用的是 DEFAULT_GROUP 。如果需要自定义自己的 Group，可以通过以下配置来实现：

```properties
spring.cloud.nacos.config.group=DEVELOP_GROUP
```

> 该配置必须放在 bootstrap.properties 文件中。并且在添加配置时 Group 的值一定要和 `spring.cloud.nacos.config.group` 的配置值一致。

## 定义扩展的 Data Id 配置

```
spring.application.name=opensource-service-microservices
spring.cloud.nacos.config.server-addr=127.0.0.1:8848

# config external configuration
# 1、Data Id 在默认的组 DEFAULT_GROUP,不支持配置的动态刷新
spring.cloud.nacos.config.ext-config[0].data-id=ext-config-common01.properties

# 2、Data Id 不在默认的组，不支持动态刷新
spring.cloud.nacos.config.ext-config[1].data-id=ext-config-common02.properties
spring.cloud.nacos.config.ext-config[1].group=GLOBALE_GROUP

# 3、Data Id 既不在默认的组，也支持动态刷新
spring.cloud.nacos.config.ext-config[2].data-id=ext-config-common03.properties
spring.cloud.nacos.config.ext-config[2].group=REFRESH_GROUP
spring.cloud.nacos.config.ext-config[2].refresh=true
```

可以看到:

- 通过 `spring.cloud.nacos.config.ext-config[n].data-id` 的配置方式来支持多个 Data Id 的配置。
- 通过 `spring.cloud.nacos.config.ext-config[n].group` 的配置方式自定义 Data Id 所在的组，不明确配置的话，默认是 DEFAULT_GROUP。
- 通过 `spring.cloud.nacos.config.ext-config[n].refresh` 的配置方式来控制该 Data Id 在配置变更时，是否支持应用中可动态刷新， 感知到最新的配置值。默认是不支持的。

> 多个 Data Id 同时配置时，他的优先级关系是 `spring.cloud.nacos.config.ext-config[n].data-id` 其中 n 的值越大，优先级越高。

> `spring.cloud.nacos.config.ext-config[n].data-id` 的值必须带文件扩展名，文件扩展名既可支持 properties，又可以支持 yaml/yml。 此时 `spring.cloud.nacos.config.file-extension` 的配置对自定义扩展配置的 Data Id 文件扩展名没有影响。

为了更加清晰的在多个应用间配置共享的 Data Id ，你可以通过以下的方式来配置：

```properties
spring.cloud.nacos.config.shared-dataids=bootstrap-common.properties,all-common.properties
spring.cloud.nacos.config.refreshable-dataids=bootstrap-common.properties
```

可以看到：

- 通过 `spring.cloud.nacos.config.shared-dataids` 来支持多个共享 Data Id 的配置，多个之间用逗号隔开。
- 通过 `spring.cloud.nacos.config.refreshable-dataids` 来支持哪些共享配置的 Data Id 在配置变化时，应用中是否可动态刷新， 感知到最新的配置值，多个 Data Id 之间用逗号隔开。如果没有明确配置，默认情况下所有共享配置的 Data Id 都不支持动态刷新。

> 通过 `spring.cloud.nacos.config.shared-dataids` 来支持多个共享配置的 Data Id 时， 多个共享配置间的一个优先级的关系我们约定：按照**配置出现的先后顺序**，即后面的优先级要高于前面。

> 通过 `spring.cloud.nacos.config.shared-dataids` 来配置时，Data Id 必须带文件扩展名，文件扩展名既可支持 properties，也可以支持 yaml/yml。 此时 `spring.cloud.nacos.config.file-extension` 的配置对自定义扩展配置的 Data Id 文件扩展名没有影响。

> spring.cloud.nacos.config.refreshable-dataids 给出哪些需要支持动态刷新时，Data Id 的值也必须明确给出文件扩展名。

## 配置的优先级

Spring Cloud Alibaba Nacos Config 目前提供了三种配置能力从 Nacos 拉取相关的配置。

- A: 通过 `spring.cloud.nacos.config.shared-dataids` 支持多个共享 Data Id 的配置
- B: 通过 `spring.cloud.nacos.config.ext-config[n].data-id` 的方式支持多个扩展 Data Id 的配置
- C: 通过内部相关规则(应用名、应用名+ Profile )自动生成相关的 Data Id 配置

当三种方式共同使用时，他们的一个优先级关系是:A < B < C



## 完全关闭配置

```properties
spring.cloud.nacos.config.enabled = false
```

## 实战演示

### 命名空间

1. 新建命名空间DEV，作为开发环境

   ![image-20190815093045898](assets/image-20190815093045898.png)

2. 命名空间的ID才是需要配置的内容

   ![image-20190815093148343](assets/image-20190815093148343.png)

### 配置列表

![image-20190815093806633](assets/image-20190815093806633.png)

1. microservices-nacos-config.yaml

   ```yaml
   user:
     name: gknoone-cloud-plus
     age: 123

   logging:
     # 配置Admin的日志文件输出位置
     file: /Users/baweibin/Downloads/gknoone-cloud-plus/log/microservices/microservices-nacos-config.log

   server:
     port: 8006
   ```
2. app-common-eureka.yaml

   ```yaml
   eureka:
     client:
       service-url:
         defaultZone: http://gknoone:gk123456@peer1:8111/eureka/,http://gknoone:gk123456@peer2:8112/eureka/
       healthcheck:
         # 开启健康检查
         enabled: true
     instance:
       # 将自己的IP注册到eureka上，若为false，则注册微服务所在操作系统的hostname到eureka上
       prefer-ip-address: true
       # 实例id，配置前192.168.13.111:microservices-user:8002，配置后microservices-user:192.168.13.111:8002
       # 默认${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id}:${service.port}
       instance-id: ${spring.application.name}:${spring.cloud.client.ipaddress}:${server.port}
       # 自定义实例跳转链接
       status-page-url: https://github.com/gknoone
       # 发送心跳给server端的频率 （开发环境开启，默认30秒）
       lease-renewal-interval-in-seconds: 5
       # server至上一次收到心跳之后，等待下一次心跳的超时时间，超时未收到心跳，移除instance （开发环境开启，默认90秒）
       lease-expiration-duration-in-seconds: 5
   ```

3. app-common-actuator.yaml

   ```yaml
   # 开启监控点
   management:
     endpoints:
       web:
         exposure:
           include: "*"
     endpoint:
       health:
         # 是否展示健康检查详情
         show-details: ALWAYS
   ```

4. app-common-feign.yaml

   ```yaml
   feign:
     hystrix:
       # 开启熔断
       enabled: true
   ```

### 项目配置

1. application.yml

   ```yaml
   spring:
     application:
       name: @pom.artifactId@
   ```

2. bootstrap.yml

   ```yaml
   spring:
     cloud:
       nacos:
         config:
           server-addr: 127.0.0.1:8848
           file-extension: yaml
           namespace: 1241625f-714b-440d-8d71-32e58d531f3a
           group: gknoone-cloud-plus
           # 共享的配置列表
           # shared-dataids: app-common-eureka-dev.yaml,app-common-actuator-dev.yaml,app-common-feign-dev.yaml
           # refreshable-dataids: app-common-eureka-dev.yaml,app-common-actuator-dev.yaml,app-common-feign-dev.yaml
           # 共享配置
           ext-config:
             - data-id: app-common-eureka.yaml
               group: gknoone-cloud-plus
               namespace: 1241625f-714b-440d-8d71-32e58d531f3a
               refresh: true
             - data-id: app-common-actuator.yaml
               group: gknoone-cloud-plus
               namespace: 1241625f-714b-440d-8d71-32e58d531f3a
               refresh: true
             - data-id: app-common-feign.yaml
               group: gknoone-cloud-plus
               namespace: 1241625f-714b-440d-8d71-32e58d531f3a
               refresh: true
   ```


3. 读取配置使用示例

   ```java
   @RestController
   @RefreshScope
   public class NacosConfigController {
       @Value("${user.name:kk}")
       private String name;
       @Value("${user.age:11}")
       private Integer age;

       @GetMapping("/user")
       public String getUserInfo(){
           return String.format("%s is %s years old this year.", name,age);
       }
   }
   ```
