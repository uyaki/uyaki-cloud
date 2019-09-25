# gknoone-cloud-plus

- 搭建eureka作为服务注册和发现中心
- 搭建alibaba nacos作为配置中心
- 搭建zuul作为网关中心，进行路由映射，对外提供统一的REST API接口，自定义过滤器和实现负载均衡
- 搭建admin作为监控中心，为Actuator的endpoint提供简洁的可视化WEB UI
- 搭建Sleuth+zipkin+ribbitmq+elasticsearch进行服务跟踪
- 使用Jenkins+docker对项目进行持续集成，自动部署
- 使用openfeign进行微服务间的API调用
