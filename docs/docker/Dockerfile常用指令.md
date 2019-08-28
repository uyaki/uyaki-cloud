# Dockerfile 常用指令
<!-- @import "[TOC]" {cmd="toc" depthFrom=2 depthTo=6 orderedList=false} -->
<!-- code_chunk_output -->

* [基本结构](#基本结构)
* [常用指令](#常用指令)
	* [- FROM](#-from)
	* [- MAINTAINER](#-maintainer)
	* [- LABEL](#-label)
	* [- RUN](#-run)
	* [- CMD](#-cmd)
	* [- EXPOSE](#-expose)
	* [- ENV](#-env)
	* [- ADD](#-add)
	* [- COPY](#-copy)
	* [- ENTRYPOINT](#-entrypoint)
	* [- USER](#-user)
	* [- WORKDIR](#-workdir)
	* [- VOLUME](#-volume)
	* [- ONBUILD](#-onbuild)
* [创建镜像](#创建镜像)
	* [docker build命令](#docker-build命令)

<!-- /code_chunk_output -->

## 基本结构

Dockerfile 由一行行命令语句组成，并且支持以 `#` 开头的注释行。

一般的，Dockerfile 分为四部分：

- 基础镜像信息；
- 维护者信息；
- 镜像操作指令；
- 容器启动时执行指令。

例如

```dockerfile
# This dockerfile uses the ubuntu image
# VERSION 2 - EDITION 1
# Author: docker_user
# Command format: Instruction [arguments / command] ..

# Base image to use, this must be set as the first line
FROM ubuntu

# Maintainer: docker_user <docker_user at email.com> (@docker_user)
MAINTAINER docker_user docker_user@email.com

# Commands to update the image
RUN echo "deb http://archive.ubuntu.com/ubuntu/ raring main universe" >> /etc/apt/sources.list
RUN apt-get update && apt-get install -y nginx
RUN echo "\ndaemon off;" >> /etc/nginx/nginx.conf

# Commands when creating a new container
CMD /usr/sbin/nginx
```

其中，一开始必须指明所基于的镜像名称，接下来推荐说明维护者信息。

后面则是镜像操作指令，例如 `RUN` 指令，`RUN` 指令将对镜像执行跟随的命令。每运行一条 `RUN` 指令，镜像添加新的一层，并提交。

最后是 `CMD` 指令，来指定运行容器时的操作命令。

下面是一个更复杂的例子

```dockerfile
# Nginx
#
# VERSION               0.0.1

FROM      ubuntu
MAINTAINER Victor Vieux <victor@docker.com>

RUN apt-get update && apt-get install -y inotify-tools nginx apache2 openssh-server

# Firefox over VNC
#
# VERSION               0.3

FROM ubuntu

# Install vnc, xvfb in order to create a 'fake' display and firefox
RUN apt-get update && apt-get install -y x11vnc xvfb firefox
RUN mkdir /.vnc
# Setup a password
RUN x11vnc -storepasswd 1234 ~/.vnc/passwd
# Autostart firefox (might not be the best way, but it does the trick)
RUN bash -c 'echo "firefox" >> /.bashrc'

EXPOSE 5900
CMD    ["x11vnc", "-forever", "-usepw", "-create"]

# Multiple images example
#
# VERSION               0.1

FROM ubuntu
RUN echo foo > bar
# Will output something like ===> 907ad6c2736f

FROM ubuntu
RUN echo moo > oink
# Will output something like ===> 695d7793cbe4

# You᾿ll now have two images, 907ad6c2736f with /bar, and 695d7793cbe4 with
# /oink.
```



## 常用指令

### - FROM指定基础镜像

FROM指定的镜像将自动拉取作为底层的镜像进行制作;

>  第一条指令必须为 `FROM` 指令。并且，如果在同一个Dockerfile中创建多个镜像时，可以使用多个 `FROM` 指令（每个镜像一次）

```dockerfile
FROM <image>
FROM <image>[:tag]
FROM <image>@<digest>

EXAMPLE:
FROM centos:latest
    # 使用Centos的最新发行镜像作为底层镜像
```

### - MAINTAINER（废弃）

提供制作人的信息，废弃不用了，现在使用LABEL

```dockerfile
MAINTAINER "LiarLee<Test@LiarLee.com>"
```

### - LABEL 为镜像添加元数据

LABEL是给镜像指定元数据的命令

```dockerfile
LABEL maintainer="LiarLee<Test@LiarLee.com>"
LABEL version="1.0"
LABEL description="This text xxx"
```

### - RUN 执行命令

格式为 

- `RUN <command>` 
-  `RUN ["executable", "param1", "param2"]`。

前者将在 shell 终端中运行命令，即 `/bin/sh -c`；后者则使用 `exec` 执行。指定使用其它终端可以通过第二种方式实现，例如 `RUN ["/bin/bash", "-c", "echo hello"]`。

每条 `RUN` 指令将在当前镜像基础上执行指定命令，并提交为新的镜像。当命令较长时可以使用 `\` 来换行。

### - CMD容器启动命令

支持三种格式

- `CMD ["executable","param1","param2"]` 使用 `exec` 执行，推荐方式；
- `CMD command param1 param2` 在 `/bin/sh` 中执行，提供给需要交互的应用；
- `CMD ["param1","param2"]` 提供给 `ENTRYPOINT` 的默认参数；

指定启动容器时执行的命令，每个 Dockerfile 只能有一条 `CMD` 命令。如果指定了多条命令，只有最后一条会被执行。

如果用户启动容器时候指定了运行的命令，则会覆盖掉 `CMD` 指定的命令。

```dockerfile
CMD echo "This is a test." | wc -
```



### - EXPOSEs声明暴露的端口

告诉 Docker 服务端容器暴露的端口号，供互联系统使用。在启动容器时需要通过 -P，Docker 主机会自动分配一个端口转发到指定的端口。

```dockerfile
EXPOSE <port>[<protocol>...]
指定的协议为tcp or udp , defaults option is TCP
可以一次指定多个端口
```

### - ENV设置环境变量

指定一个环境变量，会被后续 `RUN` 指令使用，并在容器运行时保持。

```dockerfile
ENV <key> <value1> <value2> <value3> <value4>
ENV <key>=<value> <key>=<value>...
```

```dockerfile
ENV PG_MAJOR 9.3
ENV PG_VERSION 9.3.4
RUN curl -SL http://example.com/postgres-$PG_VERSION.tar.xz | tar -xJC /usr/src/postgress && …
ENV PATH /usr/local/postgres-$PG_MAJOR/bin:$PATH
```

### - ARG设置构建参数

类似于ENV，区别：

ARG设置的是构建时的环境变量，在容器运行时是不会存在这些变量的

```dockerfile
ARG <name>[=<default value>]
```



### - ADD添加文件

该命令将复制指定的 `<src>` 到容器中的 `<dest>`。 其中 `<src>` 可以是Dockerfile所在目录的一个相对路径；也可以是一个 URL；还可以是一个 tar 文件（自动解压为目录）

- `<src>`必须在构建的上下文内，不能使用例如`ADD ../somethine /something`这样的命令，因为docker build命令首先会将上下文路径和其他子目录发送到docker deamon。
- 如果`<src>`是一个URL，同时 `<dest>` 不以斜杆结尾，`<dest>`将被视为文件，`<src>`对应的内容文件将被下载到`<dest>`
- 如果`<src>`是一个URL，同时 `<dest>` 以斜杆结尾，`<dest>`将被视为目录，`<src>`对应的内容文件将被下载到`<dest>`目录
- 如果`<src>`是一个目录，那么整个目录下的内容将会被复制，包括文件系统元数据
- 如果文件是可识别的压缩包格式，则docker会自动解压

```dockerfile
ADD <src> ... <dest>
ADD ["<src>" ... "<dest>"]

EXAMPLE:
ADD microservice-discovery-eureka-0.0.1-SNAPSHOT.jar app.jar
```



### - COPY 复制文件

复制本地主机的 `<src>`（为 Dockerfile 所在目录的相对路径）到容器中的 `<dest>`。

>  当使用本地目录为源目录时，推荐使用 `COPY`。

与`ADD` 区别:

- 不支持URL
- 不支持压缩包

```dockerfile
COPY <src> ... <dest>
COPY ["<src>" ... "<dest>"]
\<src> -- 相对路径
\<dest> -- 绝对路径

- 指定的src目录，会将目录下的所有文件复制到目的地，但是不会将src复制。
- 如果使用了多个src，或者src使用了通配，目的必须是个目录
- 如果dest不存在会被自动创建

COPY /etc/passwd /etc/passwd
    # 与cp命令相似，复制本地目标文件到容器文件系统中
COPY /usr/local/src/nginx/* /usr/local/src/nginx/
    # 如果目标目录不存在需要先行创建
```

### - ENTRYPOINT 入口点

两种格式：

- `ENTRYPOINT ["executable", "param1", "param2"]`
- `ENTRYPOINT command param1 param2`（shell中执行）。

配置容器启动后执行的命令，并且不可被 `docker run` 提供的参数覆盖。

每个 Dockerfile 中只能有一个 `ENTRYPOINT`，当指定多个时，只有最后一个起效。

### - USER 设置用户

格式为 `USER daemon`。

指定运行容器时的用户名或 UID，后续的 `RUN` 也会使用指定用户。

当服务不需要管理员权限时，可以通过该命令指定运行用户。并且可以在之前创建所需要的用户，例如：`RUN groupadd -r postgres && useradd -r -g postgres postgres`。要临时获取管理员权限可以使用 `gosu`，而不推荐 `sudo`。

### - WORKDIR 指定工作目录

类似于`cd` 命令，为后续的 `RUN`、`CMD`、`ENTRYPOINT` 指令配置工作目录。

可以使用多个 `WORKDIR` 指令，后续命令如果参数是相对路径，则会基于之前命令指定的路径。

```
WORKDIR <dirpath>
指定当前的工作目录

# EXAMPLE-1:
WORKDIR /usr/local/src/nginx

# EXAMPLE-2:
WORKDIR /a
WORKDIR b
WORKDIR c
RUN pwd
# 最终路径为 /a/b/c
```

### - VOLUME 指定挂载点

> 创建一个可以从本地主机或其他容器挂载的挂载点，一般用来存放数据库和需要保持的数据等。

```
VOLUME \<mountpoint>
VOLUME ["\<mountpoint>"]
VOLUME指定挂载的卷
只能设置容器中的卷目录，不能制定宿主机的目录，只能使用Docker自动管理的卷
```

### - ONBUILD 

格式为 `ONBUILD [INSTRUCTION]`。

配置当所创建的镜像作为其它新创建镜像的基础镜像时，所执行的操作指令。

例如，Dockerfile 使用如下的内容创建了镜像 `image-A`。

```dockerfile
[...]
ONBUILD ADD . /app/src
ONBUILD RUN /usr/local/bin/python-build --dir /app/src
[...]
```

如果基于 image-A 创建新的镜像时，新的Dockerfile中使用 `FROM image-A`指定基础镜像时，会自动执行`ONBUILD` 指令内容，等价于在后面添加了两条指令。

```dockerfile
FROM image-A

#Automatically run the following
ADD . /app/src
RUN /usr/local/bin/python-build --dir /app/src
```

使用 `ONBUILD` 指令的镜像，推荐在标签中注明，例如 `ruby:1.9-onbuild`。

## 创建镜像

### docker build命令

```shell
$ docker build -h
Flag shorthand -h has been deprecated, please use --help
Usage:	docker build [OPTIONS] PATH | URL | -
Build an image from a Dockerfile
Options:
      --add-host list           Add a custom host-to-IP mapping (host:ip)
      --build-arg list          Set build-time variables
      --cache-from strings      Images to consider as cache sources
      --cgroup-parent string    Optional parent cgroup for the container
      --compress                Compress the build context using gzip
      --cpu-period int          Limit the CPU CFS (Completely Fair Scheduler) period
      --cpu-quota int           Limit the CPU CFS (Completely Fair Scheduler) quota
  -c, --cpu-shares int          CPU shares (relative weight)
      --cpuset-cpus string      CPUs in which to allow execution (0-3, 0,1)
      --cpuset-mems string      MEMs in which to allow execution (0-3, 0,1)
      --disable-content-trust   Skip image verification (default true)
  -f, --file string             Name of the Dockerfile (Default is 'PATH/Dockerfile')
      --force-rm                Always remove intermediate containers
      --iidfile string          Write the image ID to the file
      --isolation string        Container isolation technology
      --label list              Set metadata for an image
  -m, --memory bytes            Memory limit
      --memory-swap bytes       Swap limit equal to memory plus swap: '-1' to enable
                                unlimited swap
      --network string          Set the networking mode for the RUN instructions
                                during build (default "default")
      --no-cache                Do not use cache when building the image
      --pull                    Always attempt to pull a newer version of the image
  -q, --quiet                   Suppress the build output and print image ID on success
      --rm                      Remove intermediate containers after a successful
                                build (default true)
      --security-opt strings    Security options
      --shm-size bytes          Size of /dev/shm
  -t, --tag list                Name and optionally a tag in the 'name:tag' format
      --target string           Set the target build stage to build.
      --ulimit ulimit           Ulimit options (default [])
```

制作一个容器，COPY本地的HTML文件到容器文件系统中

```shell
$ docker build -t busybox-httpd:v0.1-1 ./
Sending build context to Docker daemon  3.072kB
Step 1/3 : FROM busybox:latest
 ---> d8233ab899d4
Step 2/3 : MAINTAINER "HAYDEN<HAYDEN@lee.com>"
 ---> Running in d581bd9c9aba
Removing intermediate container d581bd9c9aba
 ---> 8137f8096ce4
Step 3/3 : COPY index.html /data/web/html/
 ---> bced33a9e4a4
Successfully built bced33a9e4a4
Successfully tagged busybox-httpd:v0.1-1
```



查看已经制作完成的镜像

```shell
$ docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
busybox-httpd       v0.1-1              bced33a9e4a4        17 seconds ago      1.2MB
nginx               latest              881bd08c0b08        3 weeks ago         109MB
busybox             latest              d8233ab899d4        6 weeks ago         1.2MB
```



运行容器查看结果

```shell
$ docker run --name t1 --rm busybox-httpd:v0.1-1 cat /data/web/html/index.html
<h1>Busybox httpd server.</h1>
```
