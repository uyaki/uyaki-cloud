# Spring Cloud Admin

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [Admin Server](#admin-server)
* [admin-client配置](#admin-client配置)
	* [不使用eureka](#不使用eureka)
	* [使用eureka](#使用eureka)
	* [公共配置](#公共配置)

<!-- /code_chunk_output -->

## Admin Server

1. 创建模块`gknoone-cloud-plus-admin`

2. 引入依赖

   ```xml
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
     <!--排除tomcat客户端，引入jetty客户端-->
     <exclusions>
       <exclusion>
         <artifactId>spring-boot-starter-tomcat</artifactId>
         <groupId>org.springframework.boot</groupId>
       </exclusion>
     </exclusions>
   </dependency>
   <!--排除tomcat客户端，引入jetty客户端-->
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-jetty</artifactId>
   </dependency>
   <!--引入admin-starter-server，配合注解@EnableAdminServer使用-->
   <dependency>
     <groupId>de.codecentric</groupId>
     <artifactId>spring-boot-admin-starter-server</artifactId>
     <version>2.1.6</version>
   </dependency>
   <!--开启安全认证-->
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   <!--集成eureka-->
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   ```

   > 注意，在父模块以及引入了**actuator**监控，无需重复引入

   ```xml
   <!-- actuator监控 -->
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   ```

3. 使用`@EnableAdminServer`注解启动类

   ```java
   /**
    * @author noone
    * @date 2019-08-12 16:16:15
    */
   @EnableAdminServer
   @SpringBootApplication
   public class AdminApplication {

       public static void main(String[] args) {
           SpringApplication.run(AdminApplication.class, args);
       }

   }
   ```

4. 配置**application.yml**

   ```yml
   server:
     port: 8333
   spring:
     application:
       name: @pom.artifactId@
     boot:
       admin:
         ui:
           # 自定义显示title
           title: gknoone-cloud-plus-Admin-Server
     # 配置认证信息，client端需要对应配置（ 如果配置到了eureka，则不需要）
     # spring.boot.admin.client.url=http://localhost:8333
     # spring.boot.admin.client.username=gknoone
     # spring.boot.admin.client.password=gk123456
     security:
       user:
         name: gknoone
         password: gk123456

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

5. 配置Security

   ```java
   /**
    * 认证配置
    *
    * @author gknoone
    * @date 2019-08-12 08:49
    */
   @Configuration
   public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
       private final String adminContextPath;

       public SecurityPermitAllConfig(AdminServerProperties adminServerProperties) {
           this.adminContextPath = adminServerProperties.getContextPath();
       }

       @Override
       protected void configure(HttpSecurity http) throws Exception {
           SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
           successHandler.setTargetUrlParameter("redirectTo");
           //静态资源和登录页面可以不认证
           http.authorizeRequests().antMatchers(adminContextPath + "/assets/**").permitAll()
                   .antMatchers(adminContextPath + "/login").permitAll()
                   .antMatchers(adminContextPath + "/actuator/**").permitAll()
                   //其他请求必须认证
                   .anyRequest().authenticated()
                   //自定义登录和退出
                   .and().formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
                   .and().logout().logoutUrl(adminContextPath + "/logout")
                   //启用HTTP-Basic，用于Spring Boot Admin Client 注册
                   .and().httpBasic()
                   .and().csrf().disable();
       }
   }
   ```

6. 访问[localhost:8111](localhost:8111)，可以看到，admin服务已经被注册到eureka-server

   ![image-20190812164529468](assets/image-20190812164529468.png)

7. 访问[localhost:8333](localhost:8333)，输入配置好的账号密码

   ![image-20190812164656883](assets/image-20190812164656883.png)

8. 点击具体的服务

   ![image-20190812165222413](assets/image-20190812165222413.png)

   可以看到如下界面

   ![image-20190812165304478](assets/image-20190812165304478.png)

## admin-client配置

### 不使用eureka

1. 引入依赖

   ```xml
   <dependency>
     <groupId>de.codecentric</groupId>
     <artifactId>spring-boot-admin-starter-client</artifactId>
   </dependency>
   ```

2. 配置文件

   ```yml
   spring:
     boot:
       admin:
         client:
         	url: http://localhost:8333
           username: gknoone
           password: gk123456
   ```

### 使用eureka

> 无需引入依赖和配置

### 公共配置

1. 引入依赖

   ```xml
   <!-- actuator监控 -->
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   ```

2. 配置文件

```yml
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
logging:
  # 配置Admin的日志文件输出位置
  file: /Users/baweibin/Downloads/gknoone-cloud-plus/log/microservices/microservices-hello.log
```

![image-20190812170504252](assets/image-20190812170504252.png)
