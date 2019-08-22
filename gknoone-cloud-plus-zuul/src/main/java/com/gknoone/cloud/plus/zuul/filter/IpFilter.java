package com.gknoone.cloud.plus.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.gknoone.cloud.plus.common.core.response.ResponseCode;
import com.gknoone.cloud.plus.common.core.response.ResponseData;
import com.gknoone.cloud.plus.common.core.util.IpUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * IP过滤器
 *
 * @author gknoone
 * @date 2019-08-21 15:21
 */
public class IpFilter extends ZuulFilter {
    /**
     * 黑名单列表
     */
    private List<String> blackIpList = Collections.singletonList("127.0.0.111");

    public IpFilter() {
        super();
    }

    /**
     * 过滤器类型【pre/route/post/error】
     *
     * @return 过滤器类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 执行顺序，数字越小，优先级越高
     *
     * @return 优先级
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 可以改成读取配置文件的形式，达到动态配置
     *
     * @return 是否过滤
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object success = ctx.get("isSuccess");
        return success == null || Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        String ip = IpUtil.getIpAddr(ctx.getRequest());
        //在黑名单中禁用
        if (StringUtils.isNotBlank(ip) && blackIpList.contains(ip)) {
            //不需要将当期请求转发到后端的服务
            ctx.setSendZuulResponse(false);
            //需要禁止本地forward转发
            ctx.set("sendForwardFilter.ran", true);
            //true后续拦截器继续执行，false后续拦截器不再执行
            ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail("非法请求", ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JSON.toJSONString(data));
            ctx.getResponse().setContentType("application/json;charset=utf-8");
            //模拟异常
//            System.out.println(2 / 0);
            return null;
        }
        return null;
    }
}
