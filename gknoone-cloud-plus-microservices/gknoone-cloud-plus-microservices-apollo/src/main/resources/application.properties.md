# 身份信息
app.id=microservices-apollo
# Meta Server(Config Server)
apollo.meta=http://localhost:8080
# 项目启动bootstrap阶段，向spring 容器注入配置信息
#apollo.bootstrap.enable=true
# 注入命名空间
#apollo.bootstrap.namespace=application

server.port=8005

spring.application.name=@pom.artifactId@
spring.cloud.inetutils.ignored-interfaces[0]=docker0
spring.cloud.inetutils.ignored-interfaces[1]=veth.*
## 允许定义同名的FeignClient,name
spring.main.allow-bean-definition-overriding=true

eureka.client.service-url.defaultZone=http://gknoone:gk123456@peer1:8111/eureka/,http://gknoone:gk123456@peer2:8112/eureka/
# 开启健康检查
eureka.client.healthcheck.enabled=true



# 将自己的IP注册到eureka上，若为false，则注册微服务所在操作系统的hostname到eureka上
eureka.instance.prefer-ip-address= true
# 实例id，配置前192.168.13.111:microservices-user:8002，配置后microservices-user:192.168.13.111:8002
# 默认${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id}:${service.port}
eureka.instance.instance-id=${spring.application.name}:${spring.cloud.client.ipaddress}:${server.port}
# 自定义实例跳转链接
eureka.instance.status-page-url=https://github.com/gknoone
# 发送心跳给server端的频率 （开发环境开启，默认30秒）
eureka.instance.lease-renewal-interval-in-seconds=5
# server至上一次收到心跳之后，等待下一次心跳的超时时间，超时未收到心跳，移除instance （开发环境开启，默认90秒）
eureka.instance.lease-expiration-duration-in-seconds= 5

# 开启监控点
management.endpoints.jmx.exposure.include=*
# 是否展示健康检查详情
management.endpoint.health.show-details=always


# 配置Admin的日志文件输出位置
logging.file=/Users/baweibin/Downloads/gknoone-cloud-plus/log/provider/provider-apollo.log

# 开启熔断
feign.hystrix.enabled=true
