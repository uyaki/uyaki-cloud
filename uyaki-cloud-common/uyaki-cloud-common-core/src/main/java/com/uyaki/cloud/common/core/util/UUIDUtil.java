package com.uyaki.cloud.common.core.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author noone
 * @date 2019-04-23 11:35
 */
public class UUIDUtil {
    /**
     * 获取UUID（去除"-"）
     * @return UUID字符串
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }
}
