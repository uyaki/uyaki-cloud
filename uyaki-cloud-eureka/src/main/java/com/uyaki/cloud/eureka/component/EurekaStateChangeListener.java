package com.uyaki.cloud.eureka.component;

import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Eureka状态变化监听器
 * 在集群环境下，每个节点都会触发事件，这个时候需要控制下发通知的行为，不控制的话每个节点都会下发通知
 * @author uyaki
 * @date 2019-07-31 16:51
 */
@Component
public class EurekaStateChangeListener {
//    private Logger logger = LoggerFactory.getLogger("elk_logger");
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
//        logger.info(String.format("%s  %s  服务下线  ", event.getServerId(), event.getAppName()));
        System.out.println(String.format("%s  %s  服务下线  ", event.getServerId(), event.getAppName()));
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
//        logger.info(String.format("%s  服务进行注册  ", instanceInfo.getAppName()));
        System.out.println(String.format("%s  服务进行注册  ", instanceInfo.getAppName()));
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
//        System.out.println(String.format("%s  服务进行续约  ", event.getServerId()));
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
//        logger.info("注册中心启动");
        System.out.println("注册中心启动");
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
//        logger.info("Eureka Server启动");
        System.out.println("Eureka Server启动");
    }
}
