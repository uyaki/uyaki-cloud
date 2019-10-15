package com.gknoone.cloud.plus.microservices.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;


/**
 * @author gknoone
 * @date 2019-10-15 14:13
 */
@Component
@Slf4j
public class NettyServer {
    /**
     * boss 线程组用于处理连接工作
     */
    private EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * work 线程组用于数据处理
     */
    private EventLoopGroup worker = new NioEventLoopGroup();
    /**
     * 定义服务器端监听的端口
     */
    @Value("${netty.port}")
    private Integer port;

    @PostConstruct
    public void start() throws Exception {
        //新建一个bootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                //指定channel
                .channel(NioServerSocketChannel.class)
                //使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                //服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                .option(ChannelOption.SO_BACKLOG, 1024)
                //设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new NettyServerHandlerInitializer());
        //异步的绑定服务器,sync()一直等到绑定完成.
        ChannelFuture future = bootstrap.bind().sync();
        System.out.println(NettyServer.class.getName() + " started and listen on '" + future.channel().localAddress());
        if (future.isSuccess()) {
            log.info("启动 Netty Server");
        }
    }
    @PreDestroy
    public void destory() throws InterruptedException {
        boss.shutdownGracefully().sync();
        worker.shutdownGracefully().sync();
        log.info("关闭Netty");
    }

}
