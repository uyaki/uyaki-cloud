# eureka-server搭建

<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [Eureka-Server](#eureka-server)
* [常用配置](#常用配置)
	* [关闭自我保护](#关闭自我保护)
	* [自定义Eureka的InstanceID](#自定义eureka的instanceid)
	* [自定义实例跳转链接](#自定义实例跳转链接)
	* [快速移除已经失效的服务信息（建议只在开发环境使用）](#快速移除已经失效的服务信息建议只在开发环境使用)
* [服务上下线监控（Eureka事件监听）](#服务上下线监控eureka事件监听)

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
#  server:
#    # 关闭自我保护
#    enable-self-preservation: false
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

## 常用配置
### 关闭自我保护
```properties
eureka:
  server:
    # 关闭自我保护
    enable-self-preservation: false
```
### 自定义Eureka的InstanceID
```yml
eureka:
  client:
    # 实例id，配置前192.168.13.111:provider-user:8002，配置后provider-user:192.168.13.111:8002
    # 默认${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id}:${server.port}
    instance-id: ${spring.application.name}:${spring.cloud.client.ipaddress}:${server.port}
```
### 自定义实例跳转链接
```yml
eureka:
  instance:
    status-page-url: https://github.com/gknoone
```
1. 点击如下位置，网页会跳转到`http://192.168.13.111:8002/actuator/info`
![](assets/markdown-img-paste-20190731162906886.png)
2. 修改配置后，网页跳转到[https://github.com/gknoone](https://github.com/gknoone)

### 快速移除已经失效的服务信息（建议只在开发环境使用）
1. eureka-server
```yml
server:
  # 关闭自我保护（开发环境下关闭）
  enable-self-preservation: false
  # 清理间隔（开发环境下修改，默认60000毫秒）
  eviction-interval-timer-in-ms: 5000
```
2. eureka-client
```yml
eureka:
  client:
    healthcheck:
      # 开启健康检查
      enabled: true
  instance:
    # 发送心跳给server端的频率 （开发环境开启，默认30秒）
    lease-renewal-interval-in-seconds: 5
    # server至上一次收到心跳之后，等待下一次心跳的超时时间，超时未收到心跳，移除instance （开发环境开启，默认90秒）
    lease-expiration-duration-in-seconds: 5
```
3. healthcheck开启需要引入依赖
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
## 服务上下线监控（Eureka事件监听）
目前支持事件：
- `EurekaInstanceCanceledEvent`服务下线事件
- `EurekaInstanceRegisteredEvent`服务注册事件
- `EurekaInstanceRenewedEvent`服务续约事件
- `EurekaRegistryAvailableEvent` Eureka注册中心启动事件
- `EurekaServerStartedEvent` Eureka启动事件
