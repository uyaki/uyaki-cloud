package com.uyaba.cloud.zuul.filter;


import com.uyaba.cloud.common.core.util.IpUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 请求信息打印
 * @author uyaba
 * @date 2019-08-22 09:50
 */
public class ResquestMsgFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 99;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object success = ctx.get("isSuccess");
        return success == null || Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String ip = IpUtil.getIpAddr(ctx.getRequest());
        System.err.println(String.format("REQUEST:: %s %s:%s", request.getScheme(), ip, request.getRemotePort()));
        StringBuilder params = new StringBuilder("?");
        //获取URL参数
        Enumeration<String> names = request.getParameterNames();
        if ("GET".equals(request.getMethod())) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                params.append(name);
                params.append("=");
                params.append(request.getParameter("name"));
                params.append("&");
            }
        }
        if (params.length() > 0) {
            params.delete(params.length() - 1, params.length());
        }
        System.err.println(String.format("REQUEST URL:: > %s %s %s", request.getRequestURI(), params, request.getProtocol()));
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String value = request.getHeader(name);
            System.err.println(String.format("REQUEST HEADERS:: > %s:%s", name, value));
        }
        final RequestContext finalRequestContext = RequestContext.getCurrentContext();
        //获取请求体参数
        if (!finalRequestContext.isChunkedRequestBody()) {
            ServletInputStream inputStream;
            try {
                inputStream = finalRequestContext.getRequest().getInputStream();
                String body;
                if (inputStream != null) {
                    body = IOUtils.toString(inputStream);
                    System.err.println("REQUEST BODY:: > " + body);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
