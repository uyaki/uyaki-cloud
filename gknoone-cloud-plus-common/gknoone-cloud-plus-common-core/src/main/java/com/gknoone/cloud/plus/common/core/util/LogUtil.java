package com.gknoone.cloud.plus.common.core.util;

import com.gknoone.cloud.plus.common.core.enums.LogEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author noone
 * @date 2019-04-08 11:28
 */
public class LogUtil {
    /**
     * 获取业务日志logger
     *
     * @return 业务日志logger
     */
    public static Logger getBusinessLogger() {
        return LoggerFactory.getLogger(LogEnum.BUSINESS.category());
    }

    /**
     * 获取平台日志logger
     *
     * @return 平台日志logger
     */
    public static Logger getPlatformLogger() {
        return LoggerFactory.getLogger(LogEnum.PLATFORM.category());
    }

    /**
     * 获取数据库日志logger
     *
     * @return 数据库日志logger
     */
    public static Logger getDBLogger() {
        return LoggerFactory.getLogger(LogEnum.DB.category());
    }

    /**
     * 获取异常日志logger
     *
     * @return 异常日志logger
     */
    public static Logger getExceptionLogger() {
        return LoggerFactory.getLogger(LogEnum.EXCEPTION.category());
    }
}
