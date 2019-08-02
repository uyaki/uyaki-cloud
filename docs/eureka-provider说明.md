# README

[TOC]



## 目录结构

1. 生成命令
```bash
tree -N -I 'target|test|mvnw*|*.iml|*.md'  -L 20 > tree.md
```
2. 目录层级如下
```bash
.
├── gknoone-cloud-plus-common
│   ├── gknoone-cloud-plus-common-core
│   │   ├── src.main.java.com.gknoone.cloud.plus.common.core
│   │   │                                               ├── config
│   │   │                                               ├── support
│   │   │                                               │   └── BaseController.java
│   │   │                                               └── wrapper
│   │   │                                                   ├── PageWrapMapper.java
│   │   │                                                   ├── PageWrapper.java
│   │   │                                                   ├── WrapMapper.java
│   │   │                                                   └── Wrapper.java
│   │   └── pom.xml
│   ├── gknoone-cloud-plus-common-feign
│   │   ├── src.main.java.com.gknoone.cloud.plus.common.feign
│   │   │                                  						 └── FeignConfiguration.java
│   │   └── pom.xml
│   └── pom.xml
├── gknoone-cloud-plus-provider
│   ├── gknoone-cloud-plus-provider-hello
│   │   ├── src.main.java.com.gknoone.cloud.plus.provider
│   │   │       │                                ├── ProviderHelloApplication.java
│   │   │       │                                ├── server
│   │   │       │                                │	 └── impl
│   │   │       │                                └── web
│   │   │       │                                    ├── controller
│   │   │       │                                    └── rpc
│   │   │       │                                        ├── HelloFeignClient.java
│   │   │       │                                        └── HiFeignClient.java
│   │   │       └── resources
│   │   │          └── application.yml
│   │   └── pom.xml
│   ├── gknoone-cloud-plus-provider-test
│   │   ├── src.main.java.com.gknoone.cloud.plus.provider
│   │   │       │                                ├── ProviderTestApplication.java
│   │   │       │                                ├── server
│   │   │       │                                │	 └── impl
│   │   │       │                                └── web
│   │   │       │                                    └── controller
│   │   │       │                                    │   └── TestController.java
│   │   │       │                                    └── rpc
│   │   │       └── resources
│   │   │           └── application.yml
│   │   └── pom.xml
│   └── pom.xml
├── gknoone-cloud-plus-provider-api
│   ├── gknoone-cloud-plus-provider-hello-api
│   │   ├── src.main.java.com.gknoone.cloud.plus.provider
│   │   │                                        └── service
│   │   │                                            ├── hystrix
│   │   │                                            │   ├── HelloFeignApiHystrix.java
│   │   │                                            │   └── HiFactoryFeignApiHystrix.java
│   │   │                                            ├── HelloFeignApi.java
│   │   │                                            └── HiFactoryFeignApi.java
│   │   └── pom.xml
│   └── pom.xml
└── pom.xml
```

3. 说明

   - 所有的微服务都放在**src.main.java.com.gknoone.cloud.plus.provider**目录下，方便扫描。

     - 如果Feign接口定义跟启动类不在同目录下需要指定扫描的包名`@EnableFeignClients(basePackages="com.xxx.xxx")`

   - 采用openfeign继承的形式，实现统一管理

     - **gknoone-cloud-plus-provider-api**：
    - **service**：定义所有的FeignAPI接口
         - 路由统一格式`/api/***`
         - **hystrix**：实现API接口，实现`hystrix`的fallback处理方式
     - **gknoone-cloud-plus-provider**：微服务核心实现区
       - **web**：存放路由
         - **rpc**：远程过程调用（Remote Procedure Call），存放所有实现API接口的FeignClient
           - 需添加注解`@RestController`
         - **controller**： 存放所有对
       - **service**： 存放Service接口
         - **impl**：存放Service接口实现
   
   - 理论上，一个模块只存在一个API，即，一个API表示当前微服务提供的所有对外服务，但是有时候为了分包明确，需要建立多个API，此时，需要在服务提供方和调用方的`yml配置文件`中，声明
   
     ```yml
     spring:
       main:
         ## 允许定义同名的FeignClient,name
         allow-bean-definition-overriding: true
     ```

## 实现

1. 引入依赖

   - gknoone-cloud-plus-provider

   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
   </dependency>
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>common-util</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>common-core</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```

   - gknoone-cloud-plus-provider-hello

   ```xml
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>provider-hello-api</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```

   - gknoone-cloud-plus-provider-test

   ```xml
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>provider-hello-api</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```

   - Gknoone-cloud-plus-provider-api

   ```xml 
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
   </dependency>
   <!--通用工具-->
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>common-util</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   <!--通用核心代码-->
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>common-core</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   <!--通用feign配置-->
   <dependency>
     <groupId>com.gknoone.cloud.plus</groupId>
     <artifactId>common-feign</artifactId>
     <version>0.0.1-SNAPSHOT</version>
   </dependency>
   ```

   - gknoone-cloud-plus-common-feign

   ```xml
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   ```

2. Feign 统一配置

   ```java
   /**
    * feign配置
    *
    * @author gknoone
    * @date 2019-08-01 15:19
    */
   @Configuration
   public class FeignConfiguration {
       /**
        * 日志级别
        * none：不输出日志
        * basic：只输出请求方法的URL和响应的状态码一级接口的执行时间
        * headers：将basic信息和请求头信息输出
        * full：输出完整的请求信息
        * @return full
        */
       @Bean
       Logger.Level feignLoggerLevel(){
           return Logger.Level.FULL;
       }
   }
   ```

3. eureka-client配置：

   - **gknoone-cloud-plus-provider-test**（作为调用方需开启熔断）

     ```yml
     server:
       port: 8003
     spring:
       application:
         name: @pom.artifactId@
       main:
         ## 允许定义同名的FeignClient,name
         allow-bean-definition-overriding: true
     
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
         # 实例id，配置前192.168.13.111:provider-user:8002，配置后provider-user:192.168.13.111:8002
         # 默认${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id}:${server.port}
         instance-id: ${spring.application.name}:${spring.cloud.client.ipaddress}:${server.port}
         # 自定义实例跳转链接
         status-page-url: https://github.com/gknoone
         # 发送心跳给server端的频率 （开发环境开启，默认30秒）
         lease-renewal-interval-in-seconds: 5
         # server至上一次收到心跳之后，等待下一次心跳的超时时间，超时未收到心跳，移除instance （开发环境开启，默认90秒）
         lease-expiration-duration-in-seconds: 5
     
     logging:
       level:
         # 配合FeignClient的Configuration自定义的Logger.LEVEL使用
         com.gknoone.cloud.plus.provider.service: DEBUG
     
     feign:
       hystrix:
         # 开启熔断
         enabled: true
     ```
     
   - **gknoone-cloud-plus-produce-hello**

     ```yml
     server:
       port: 8002
     spring:
       application:
         name: @pom.artifactId@
       main:
         ## 允许定义同名的FeignClient,name
         allow-bean-definition-overriding: true
     
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
         # 实例id，配置前192.168.13.111:provider-user:8002，配置后provider-user:192.168.13.111:8002
         # 默认${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id}:${server.port}
         instance-id: ${spring.application.name}:${spring.cloud.client.ipaddress}:${server.port}
         # 自定义实例跳转链接
         status-page-url: https://github.com/gknoone
         # 发送心跳给server端的频率 （开发环境开启，默认30秒）
         lease-renewal-interval-in-seconds: 5
         # server至上一次收到心跳之后，等待下一次心跳的超时时间，超时未收到心跳，移除instance （开发环境开启，默认90秒）
         lease-expiration-duration-in-seconds: 5
     ```

     

4. 定义API与熔断实现

   - 方式一：简单，但无法抛出异常（配置项为`fallback`）

     ```java
     /**
      * @author gknoone
      * @date 2019-07-31 17:58
      */
     @FeignClient(value ="provider-hello",configuration = FeignConfiguration.class, fallback = HelloFeignApiHystrix.class)
     public interface HelloFeignApi {
         /**
          * sayHello
          * @param somebody sb
          * @return  the wrapper
          */
         @GetMapping(value = "/api/hello/{somebody}")
         Wrapper<String> sayHello(@PathVariable String somebody);
     }
     ```

     ```java
     /**
      * fallback模式可以正常熔断，但是无法抛出异常
      * @author gknoone
      * @date 2019-08-02 08:45
      */
     @Component
     public class HelloFeignApiHystrix implements HelloFeignApi {
         @Override
         public Wrapper<String> sayHello(String somebody) {
             return WrapMapper.error(somebody+"熔断了");
         }
     }
     ```

     

   - 方式二：可抛出异常（配置项为`fallbackFactory`）

     ```java
     /**
      * 演示fallback工厂Api
      *
      * @author gknoone
      * @date 2019-08-02 08:54
      */
     @FeignClient(value = "provider-hello",configuration = FeignConfiguration.class, fallbackFactory = HiFactoryFeignApiHystrix.class)
     public interface HiFactoryFeignApi {
         /**
          * say hi
          * @param somebody sb
          * @return wrapper
          */
         @GetMapping("/api/hi/{somebody}")
         Wrapper<String> sayHi(@PathVariable String somebody);
     }
     ```

     ```java
     /**
      * fallbackFactory模式可以正常熔断且抛出异常
      *
      * @author gknoone
      * @date 2019-08-02 09:35
      */
     @Component
     public class HiFactoryFeignApiHystrix implements FallbackFactory<HiFactoryFeignApi> {
     
         private Logger logger = LoggerFactory.getLogger(this.getClass());
     
         @Override
         public HiFactoryFeignApi create(Throwable throwable) {
             logger.error("fallback reason: ",throwable);
             return somebody -> WrapMapper.error("hi"+somebody+"熔断了");
         }
     }
     ```

5. 实现API，提供服务

```java
/**
 * @author gknoone
 * @date 2019-07-31 18:52
 */
@RestController
public class HelloFeignClient extends BaseController implements HelloFeignApi {
    @Override
    public Wrapper<String> sayHello(@PathVariable String somebody) {
        return WrapMapper.ok("hello"+somebody);
    }
}
```

5. 调用API，调用服务

```java
/**
 * @author gknoone
 * @date 2019-07-31 18:06
 */
@RestController
@RequestMapping(value = "/test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    @Resource
    private HelloFeignApi helloFeignApi;
    @Resource
    private HiFactoryFeignApi hiFactoryFeignApi;

    @GetMapping(value = "/hello")
    public Wrapper<String> sayHello(){
        return helloFeignApi.sayHello("test");
    }
    @GetMapping(value = "/hi")
    public Wrapper<String> sayHi(){
        return hiFactoryFeignApi.sayHi("test");
    }
}
```

