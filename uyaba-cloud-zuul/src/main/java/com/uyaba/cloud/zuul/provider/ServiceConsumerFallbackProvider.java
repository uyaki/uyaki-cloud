package com.uyaba.cloud.zuul.provider;

import com.alibaba.fastjson.JSON;
import com.uyaba.cloud.common.core.response.ResponseCode;
import com.uyaba.cloud.common.core.response.ResponseData;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 回退函数
 *
 * @author uyaba
 * @date 2019-08-22 08:55
 */
@Component
public class ServiceConsumerFallbackProvider implements FallbackProvider {
    private Logger logger = LoggerFactory.getLogger(ServiceConsumerFallbackProvider.class);

    /**
     * 对所有服务进行回退操作
     * @return 服务名（与Eureka中的一致）
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            /**
             * 返回响应的状态码
             * @return 状态码
             * @throws IOException exception
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             * 返回响应的状态文本
             * @return 状态文本
             * @throws IOException exception
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 返回回退的内容
             * @return 回退内容
             * @throws IOException exception
             */
            @Override
            public InputStream getBody() throws IOException {
                if (cause != null){
                    logger.error("",cause.getCause());
                }
                RequestContext ctx = RequestContext.getCurrentContext();
                ResponseData data = ResponseData.fail(" 服务器内部错误 ", ResponseCode.SERVER_ERROR_CODE.getCode());
                return new ByteArrayInputStream(JSON.toJSONString(data).getBytes());
            }

            /**
             * 返回响应的请求头信息
             * @return headers
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mediaType = new MediaType("application","json", Charset.forName("UTF-8"));
                headers.setContentType(mediaType);
                return headers;
            }
        };
    }
}
