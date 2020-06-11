package com.uyaba.cloud.common.core.jdbc.annotation;


import java.lang.annotation.*;

/**
 * 标示此字段为主键，需要动态生成分布式主键ID<br>
 * 如果要用数据库默认的自增就不要加此注解<br>
 * 加了此注解，字段必须是String或者Long
 * @author uyaba
 * @date 2019-10-12 18:24
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@interface AutoId {
}

