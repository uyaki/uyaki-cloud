package com.uyaba.cloud.common.core.util;

import java.lang.reflect.Field;

/**
 * 反射工具类
 *
 * @author noone
 * @date 2019-07-04 9:38
 */
public class ReflectUtil {
    public static Object getFieldValueByFieldName(String filedName, Object object) {
        Object val = null;
        try {
            Field field = object.getClass().getDeclaredField(filedName);
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            val = field.get(object);
            field.setAccessible(accessible);
            return val;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }
}
