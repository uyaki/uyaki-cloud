package com.uyaki.cloud.common.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring上下文工具
 *
 * @author noone
 * @date 2019-04-06 17:06
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    @Autowired
    private static ApplicationContext applicationContext;

    /**
     * 设置应用上下文
     * @param applicationContext 应用上下文
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取应用上下文
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取Bean
     * @param name name
     * @return Bean对象
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException{
        return applicationContext.getBean(name);
    }
}
