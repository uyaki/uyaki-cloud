package com.gknoone.cloud.plus.provider.filter;

import brave.Span;
import brave.Tracer;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理请求和响应的组件
 * 自定义Filter，用于
 * 1.添加自定义的标记
 * 2.将请求ID添加到响应头
 * @author gknoone
 * @date 2019-08-16 09:45
 */
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER+1)
public class MyTracingFilter extends GenericFilterBean {
    private  final Tracer tracer;

    public MyTracingFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Span currentSpan = this.tracer.currentSpan();
        if(currentSpan == null){
            filterChain.doFilter(servletRequest,servletResponse);
            return ;
        }
        ((HttpServletResponse) servletResponse).addHeader("ZIPKIN-TRACE-ID",currentSpan.context().traceIdString());
        currentSpan.tag("custom","tag");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
