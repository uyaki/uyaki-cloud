# docker集群与动态伸缩



1. Docker-compose.yml

   ```yml
   version: "2"
   services:
     peer1:      # 默认情况下，其他服务可以使用服务名称连接到该服务。因此，对于peer2的节点，它需要连接http://peer1:8111/eureka/，因此需要配置该服务的名称是peer1。
       image: gknoone-cloud-plus/eureka:0.0.1-SNAPSHOT
       ports:
         - "8111:8111"
       environment:
         - spring.profiles.active=peer1
     peer2:
       image: gknoone-cloud-plus/eureka:0.0.1-SNAPSHOT
       ports:
         - "8112:8112"
       environment:
         - spring.profiles.active=peer2
     zuul:
       image: gknoone-cloud-plus/zuul:0.0.1-SNAPSHOT
   #    ports:
   #      - "8444:8444"
     admin:
       image: gknoone-cloud-plus/admin:0.0.1-SNAPSHOT
   #    ports:
   #      - "8333:8333"
     microservices-hello:
       image: gknoone-cloud-plus/microservices-hello:0.0.1-SNAPSHOT
   #    ports:
   #      - "8002:8002"
     microservices-test:
       image: gknoone-cloud-plus/microservices-test:0.0.1-SNAPSHOT
   #    ports:
   #      - "8003:8003"
   ```

2. 启动

   ```sh
   docker-compose up
   ```

3. 查看docker 运行情况

   ```sh
   docker inspect --format='{{.Config.Image}} {{.Name}} {{.State.Status}} {{.NetworkSettings.IPAddress}} ' `sudo docker ps -a -q`
   ```

4. 扩容

   ```sh
   docker-compose scale microservices-hello=3 microservices-test=3 zuul=3 admin=3
   ```

   