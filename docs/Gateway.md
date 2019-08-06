# Gateway

[TOC]

## 快速上手

1. 引入依赖

   ```xml
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   ```

2. 配置**application.yml** 

   ```yml
   server:
     port: 8222
   spring:
     application:
       name: @pom.artifactId@
     profiles:
       active: limit_route
   ---
   spring:
     cloud:
       gateway:
         routes:
           - id: test_route
             uri: http://localhost:8003
             predicates:
               - Path=/test/**
             filters:
               # 去掉predicates.path的一个路径，实际访问接口为http://localhost:8021/**
               - StripPrefix=1
     profiles: final_profiles
   ```

3. 访问`localhost:8222/test/test/hi`，日志如下

   ![image-20190805113258960](assets/image-20190805113258960.png)

## 整合Eureka路由

1. 引入依赖

   ```xml
   <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   ```

2. 添加配置

   ```yml
   eureka:
     client:
       service-url:
         defaultZone: http://gknoone:gk123456@peer1:8111/eureka/,http://gknoone:gk123456@peer2:8112/eureka/
   spring:
     cloud:
       gateway:
         routes:
           - id: hello_route
             # 整合eureka，从注册中心获取服务
             uri: lb://provider-hello
             predicates:
               - Path=/hello/**
             filters:
               # 去掉predicates.path的一个路径，实际访问接口为http://localhost:8021/**
               - StripPrefix=1
   ```

   - `uri`以**lb://** 开头后面接服务名（与eureka中的服务对应）
   - 访问`http://localhost:8222/hello/api/hello/sb`，成功

3. 整合默认路由，上面的uri配置可选择关闭

   ```yml
   spring：
   	cloud:
       gateway:
         discovery:
           locator:
             # 开启服务注册和发现的功能，
             # 并且spring cloud gateway自动根据服务发现为每一个服务创建了一个router
             # 这个router将以服务名开头的请求路径转发到对应的服务。
             # 如果关闭，可以采用uri：lb://{server-name}的形式进行转发
             enabled: true
             # 将请求路径上的服务名配置为小写（因为服务注册的时候，向注册中心注册时将服务名转成大写的了）
             lower-case-service-id: true
   ```

   - 访问`http://localhost:8222/provider-hello/api/hello/sb`，成功

## 路由断言工厂（predicates）

允许以某种方式修改传入的**HTTP请求**或输出的**HTTP响应**

### Path路由断言工厂

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: path_route
          uri: https://github.com
          predicates:
            - Path=/gknoone/**
```

访问`localhost:8222/gknoone/gknoone-cloud-plus`会跳转到[https://github.com/gknoone/gknoone-cloud-plus](https://github.com/gknoone/gknoone-cloud-plus)

### Query路由断言工厂

> 接收两个参数，一个必须的参数和一个可选的正则表达式

```yml
spring:
  cloud:
  	gateway:
  		routes:
  			- id: query_route
  				uri: 
  				predicates: 
  					- Query=foo, ba.
```

如上，匹配一个查询参数为foo，值为符合正则`ba.`的路由

### Method路由断言工厂

> 接收一个参数，即HTTP方法

```yml
spring:
  cloud:
  	gateway:
  		routes:
  			- id: method_route
  				uri: 
  				predicates: 
  					- Method=GET
```

### Header路由断言工厂

> 接收两个参数，请求头名称和正则表达式

```yml
spring:
  cloud:
  	gateway:
  		routes:
  			- id: header_route
  				uri: 
  				predicates: 
  					- Header=X-Request-Id，\d+
```

### 自定义路由断言工厂

- 引用注解`@Component`

- 继承`AbstractRoutePredicateFactory`类，重写`apply`方法
- `apply`方法中，可以通过`exchang.getRequest()`拿到**ServerHttpRequest**对象，从而获取到
	- 请求参数
	- 请求方法
	- 请求头等
- `apply`方法的参数是自定义的配置类信息，在使用时候配置参数，在`apply`方法中直接获取使用
- 类命名以`RoutePredicateFactory`结尾，如：`MyDiyRoutePredicateFactory`，使用的时候`MyDiy`就是路由断言工厂的名称

```java
@Component
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {
    public CheckAuthRoutePredicateFactory() {
        super(Config.class);
    }

    private final static String AUTH_NAME = "gknoone";

    @Getter
    @Setter
    static class Config{
        private String name;
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            System.err.println(String.format("进入了CheckAuthRoutePredicateFactory\t%s", config.getName()));
            return AUTH_NAME.equals(config.getName());
        };
    }
}
```

对应yml配置如下：

```yml
spring:
  profiles: final_profiles
  cloud:
    gateway:
      routes:
        - id: check_auth_route
          # 访问 localhost:8222/hot
          uri: https://www.zhihu.com
          predicates:
          - name: CheckAuth
            args:
              name: gknoone
```

## 过滤器工厂

### AddRequestHearder过滤器工厂

```yml
spring:
	cloud:
		gateway:
			routes:
				- id: add_request_header_route
				  uri: https://www.zhihu.com
				  filters:
				  	- AddRequestHeader=X-Request-Foo,Bar	
```

### RemoveRequestHeader过滤器工厂

```yml
spring:
	cloud:
		gateway:
			routes:
				- id: remove_request_header_route
				  uri: https://www.zhihu.com
				  filters:
				  	- RemoveRequestHeader=X-Request-Foo,Bar	
```

### SetStatus过滤器工厂

> 设置请求的响应码

```yml
spring:
	cloud:
		gateway:
			routes:
				- id: set_status_route
				  uri: https://www.zhihu.com
				  filters:
				  	- SetStatus=401
```

### RedirectTo过滤器工厂

```yml
spring:
	cloud:
		gateway:
			routes:
				- id: redirect_to_route
				  uri: https://www.zhihu.com
				  filters:
				  	- RedirectTo=302,http://baidu.com	
```

### 自定义过滤器工厂

- 引用注解`@Component`

- 继承`AbstractGatewayFilterFactory`类，重写`apply`方法
- 类命名以`GatewayFilterFactory`结尾，如：`MyDiyGatewayFilterFactory`，使用的时候`MyDiy`就是过滤器工厂的名称

```java
@Component
public class CheckAuth2GatewayFilterFactory extends AbstractGatewayFilterFactory<CheckAuth2GatewayFilterFactory.Config> {
    public CheckAuth2GatewayFilterFactory() {
        super(Config.class);
    }
    @Getter
    @Setter
    static class Config{
        private String name;
    }
    @Override
    public GatewayFilter apply(CheckAuth2GatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            System.err.println(String.format("进入CheckAuth2GatewayFilterFactory\t%s", config.getName()));
            ServerHttpRequest request = exchange.getRequest().mutate().build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
```

对应yml配置如下

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: check_auth_route
          # 访问 localhost:8222/hot
          uri: https://zhihu.com/
          predicates:
          - name: CheckAuth
            args:
              name: gknoone
          filters:
          - name: CheckAuth2
            args:
              name: gknoone2
```

### 自定义KeyValue过滤器工厂

- 不需要定义配置类Config
- 引用注解`@Component`

- 继承`AbstractNameValueGatewayFilterFactory`类，重写`apply`方法
- 类命名以`GatewayFilterFactory`结尾，如：`MyDiyGatewayFilterFactory`，使用的时候`MyDiy`就是过滤器工厂的名称

```java
@Component
public class CheckAuth3GatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            System.err.println(String.format("进入CheckAuth3GatewayFilterFactory\t%s\t%s", config.getName(),config.getValue()));
            ServerHttpRequest request = exchange.getRequest().mutate().build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
```

对应yml配置如下

```yml
spring:
  cloud:
    gateway:
      routes:
        - id: check_auth_route
          # 访问 localhost:8222/hot
          uri: https://zhihu.com/
          predicates:
          - name: CheckAuth
            args:
              name: gknoone
          filters:
          - CheckAuth3=gknoone,niubi
```

## 全局过滤器

- 作用于所有路由，不需要单独配置
- 应用举例
  - 权限认证
  - IP访问配置
- 接口定义类`org.springframework.cloud.gateway.filter.GlobalFilter`

1. `@Configuration`的形式

   ```java
   @Configuration
   public class ExampleConfiguration {
       private Logger logger = LoggerFactory.getLogger(this.getClass());
       @Bean
       @Order(-1)
       public GlobalFilter a(){
           return (exchange, chain) -> {
               logger.info("first pre filter");
               return chain.filter(exchange).then(Mono.fromRunnable(()->{
                   logger.info("third post filter");
               }));
           };
       }
       @Bean
       @Order(0)
       public GlobalFilter b(){
           return (exchange, chain) -> {
               logger.info("second pre filter");
               return chain.filter(exchange).then(Mono.fromRunnable(()->{
                   logger.info("second post filter");
               }));
           };
       }
       @Bean
       @Order(1)
       public GlobalFilter c(){
           return (exchange, chain) -> {
               logger.info("third pre filter");
               return chain.filter(exchange).then(Mono.fromRunnable(()->{
                   logger.info("first post filter");
               }));
           };
       }
   }
   ```

   > `@Order`数字越小，优先级越高

   ![image-20190806090611771](assets/image-20190806090611771.png)

2. `@Component`的形式

   ```java
   /**
    * IP全局过滤器
    *
    * @author gknoone
    * @date 2019-08-06 09:09
    */
   @Component
   public class IpGlobalFilter implements GlobalFilter, Ordered {
       private static final String LOCAL_IP = "127.0.0.1";
   
       @Override
       public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
           HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
           if (LOCAL_IP.equals(getIp(httpHeaders))) {
               ServerHttpResponse response = exchange.getResponse();
               ResponseDate responseDate = new ResponseDate();
               responseDate.setCode(401);
               responseDate.setMessage("非法请求");
               byte[] data = JSON.toJSONBytes(responseDate);
               DataBuffer dataBuffer = response.bufferFactory().wrap(data);
               response.setStatusCode(HttpStatus.UNAUTHORIZED);
               response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
               return response.writeWith(Mono.just(dataBuffer));
           }
           return chain.filter(exchange);
       }
   
       private String getIp(HttpHeaders httpHeaders) {
           return "127.0.0.1";
       }
   
       @Data
       private class ResponseDate {
           int code;
           String message;
       }
   
       /**
        * 数字越小，优先级越高
        *
        * @return order
        */
       @Override
       public int getOrder() {
           return 0;
       }
   }
   ```

## 限流

- 引入依赖Redis
  ``` xml
  <!--引入限流需要基于Redis实现-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
  </dependency>
  ```

  ```yml
  spring:
  	redis:
  	host: localhost
  	port: 6379
  	database: 0
  ```
  
- 实现KeyResolver类

- 在启动类中注册Bean

- 在配置**application.yml**中配置

### Ip限流

- 实现keyResolver

  ```java
  public class IpKeyResolver implements KeyResolver {
      @Override
      public Mono<String> resolve(ServerWebExchange exchange) {
          return Mono.just(getIpAddr(exchange.getRequest()));
      }
  
      /**
       * 获取真实IP
       * @param request 请求
       * @return ip
       */
      private static String getIpAddr(ServerHttpRequest request){
          HttpHeaders httpHeaders  = request.getHeaders();
          List<String> ips = httpHeaders.get("X-Forwarded-For");
          String ip = "127.0.0.1";
          if(ips!= null && ips.size()>0){
              ip = ips.get(0);
          }
          return ip;
      }
  }
  ```

- 在启动类中注册Bean

  ```java
  @Bean
  public IpKeyResolver ipKeyResolver(){
    return new IpKeyResolver();
  }
  ```

- 在配置文件中添加配置

  ```yml
  spring:
    cloud:
    gateway:
        routes:
          - id: resolver_route
            uri: lb://provider-test
            predicates:
              - Path=/test/**
            filters:
              - StripPrefix=1
              # 名称必须是RequestRateLimiter
              - name: RequestRateLimiter
                args:
                  # 用于限流的键的解析器的 Bean 对象的名字。它使用 SpEL 表达式根据#{@beanName}从 Spring 容器中获取 Bean 对象。
                  key-resolver: "#{@ipKeyResolver}"
                  # 令牌桶每秒填充平均速率（每秒处理多少个请求）
                  redis-rate-limiter.replenishRate: 2
                  # 令牌桶总容量（运行一秒内完成的最大请求数量）
                  redis-rate-limiter.burstCapacity: 3
  ```

访问100次`localhost:8222/test/test/hi`，查看Redis如下：

![image-20190806150619768](assets/image-20190806150619768.png)

![image-20190806151530389](assets/image-20190806151530389.png)

### 用户限流

```java
public class UserKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("userId")));
    }
}
```

### 接口限流

```java
public class ApiKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getPath().value());
    }
}
```

