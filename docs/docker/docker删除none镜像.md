# docker删除none镜像

方式一：

```sh
# 停止docker
docker stop $(docker ps -a | grep "Exited" | awk '{print $1 }')
# 删除docker
docker rm $(docker ps -a | grep "Exited" | awk '{print $1 }')
# 删除images
docker rmi $(docker images | grep "none" | awk '{print $3}')
```

方式二：

```sh
docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker stop
docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm
docker images|grep none|awk '{print $3 }'|xargs docker rmi
```

方式三：

```sh
docker rmi $(docker images -f "dangling=true" -q)
```

