# Parameter 0 of method modifyRequestBodyGatewayFilterFactory in org.springframework.cloud.gateway.config.GatewayAutoConfiguration required a bean of type 'org.springframework.http.codec.ServerCodecConfigurer' that could not be found

```yaml
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of method modifyRequestBodyGatewayFilterFactory in org.springframework.cloud.gateway.config.GatewayAutoConfiguration required a bean of type 'org.springframework.http.codec.ServerCodecConfigurer' that could not be found.


Action:

Consider defining a bean of type 'org.springframework.http.codec.ServerCodecConfigurer' in your configuration.
```

## 原因

SpringCloudGateway还不支持spring-boot-starter-web，所以需要先把依赖排除

## 解决

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
  </dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
      <exclusion>
        <artifactId>*</artifactId>
        <groupId>*</groupId>
      </exclusion>
    </exclusions>
  </dependency>
</dependencies>
```
