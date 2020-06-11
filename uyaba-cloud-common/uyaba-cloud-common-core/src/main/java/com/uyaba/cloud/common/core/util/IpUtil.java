package com.uyaba.cloud.common.core.util;

import com.uyaba.cloud.common.core.constant.HttpContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 *
 * @author uyaba
 * @date 2019-08-21 15:27
 */
public class IpUtil {
    private static Logger logger = LoggerFactory.getLogger(IpUtil.class);

    public static String getIpAddr(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader(HttpContants.X_FORWARDED_FOR);
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || HttpContants.UNKNOWN.equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || HttpContants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HttpContants.PROXY_CLIENT_IP);
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || HttpContants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HttpContants.WL_PROXY_CLIENT_IP);
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || HttpContants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HttpContants.HTTP_CLIENT_IP);
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || HttpContants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader(HttpContants.HTTP_X_FORWARDED_FOR);
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || HttpContants.UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > HttpContants.IP_LENGTH) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!(HttpContants.UNKNOWN.equalsIgnoreCase(s))) {
                    ip = s;
                    break;
                }
            }
        }
        return ip;
    }

}
