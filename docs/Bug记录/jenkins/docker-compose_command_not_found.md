# docker-compose: command not found

# 解决方法

修改`Execute shell`

将

```sh
$ docker-compose down --rmi all
```

修改为

```sh
$ /usr/local/bin/docker-compose down --rmi all
```

