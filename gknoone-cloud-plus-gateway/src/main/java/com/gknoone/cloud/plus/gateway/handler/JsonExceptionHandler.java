package com.gknoone.cloud.plus.gateway.handler;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一的JSON异常处理逻辑
 *
 * @author gknoone
 * @date 2019-08-06 17:42
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {
    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     *
     * @param request           request
     * @param includeStackTrace includeStackTrace
     * @return 异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.web.server.ResponseStatusException) {
            code = 404;
        }
        return response(code, this.buildMessage(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes errorAttributes
     * @return RouterFunction
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        //默认采用HTML显示（详细见源码），这里改用JSON显示；
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     *
     * @param errorAttributes errorAttributes
     * @return HttpStatus
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        //原始方法是根据status获取，不存在会报错，所以改成code
        int statusCode = (int) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 构建异常信息
     * @param request request
     * @param ex 异常
     * @return 异常信息
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        return String.format("Failed to handle request [%s %s]%s", request.methodName(), request.uri(), ex != null ? " : " + ex.getMessage() : "");
    }

    /**
     * 构建返回的JSON数据格式
     * @param status 状态码
     * @param errorMessage 异常信息
     * @return map
     */
    private static Map<String,Object> response(int status, String errorMessage){
        Map<String,Object> map = new HashMap<>();
        map.put("code",status);
        map.put("message",errorMessage);
        map.put("data",null);
        return map;
    }
}
