# eureka-server搭建

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [Eureka-Server](#eureka-server)

<!-- /code_chunk_output -->

## Eureka-Server
1. 引入依赖
```xml
<!--需要在启动类添加@EnableEurekaServer注解-->
<dependencies>
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   </dependency>
   <!--结合YML开启用户认证-->
   <!-- Spring Cloud 2.0 以上的security默认启用了csrf检验，要在eurekaServer端配置security的csrf检验为false-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
</dependencies>

<dependencyManagement>
   <dependencies>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-dependencies</artifactId>
           <version>${spring-cloud.version}</version>
           <type>pom</type>
           <scope>import</scope>
       </dependency>
   </dependencies>
</dependencyManagement>
```
2. 配置`application.yml`
```yml
spring:
  application:
    name: @pom.artifactId@
  security:
    user:
      name: gknoone
      password: gk123456
```
3. 配置`bootstrap.yml`
```yml
eureka:
  client:
    service-url:
      defaultZone: http://gknoone:gk123456@peer1:8111/eureka/,http://gknoone:gk123456@peer2:8112/eureka/
    healthcheck:
      enabled: true
---
spring:
  profiles: peer1
server:
  port: 8111
eureka:
  instance:
    hostname: peer1
---
spring:
  profiles: peer2
server:
  port: 8112
eureka:
  instance:
    hostname: peer2
```
4. 添加配置类(Spring Cloud2.0以上需要)
```java
/**
 * Security配置类
 * @author gknoone
 * @date 2019-07-31 09:29
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Spring Cloud 2.0 以上的security默认启用了csrf检验，要在eurekaServer端配置security的csrf检验为false
        http.csrf().disable();
        //注意：为了可以使用 http://${user}:${password}@${host}:${port}/eureka/ 这种方式登录,所以必须是httpBasic
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
```
5. 启动类
```java
/**
 * 启动类
 * 使用 @EnableEurekaServer 开启EurekaServer
 * @author gknoone
 * @date 2019-07-31 09:01:32
 */
@SpringBootApplication
@EnableEurekaServer
public class GknooneCloudPlusEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GknooneCloudPlusEurekaApplication.class, args);
    }

}
```
6. 启动peer1和peer2
7. 浏览器访问[http://localhost:8111/](http://localhost:8111/)，看到如下界面，说明成功
![](assets/markdown-img-paste-20190731104050878.png)
