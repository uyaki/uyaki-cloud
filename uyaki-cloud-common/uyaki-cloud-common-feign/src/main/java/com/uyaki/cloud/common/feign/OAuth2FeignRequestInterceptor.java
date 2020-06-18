package com.uyaki.cloud.common.feign;

import lombok.extern.slf4j.Slf4j;

/**
 * request拦截器
 *
 * @author uyaki
 * @date 2019-08-01 15:20
 */
@Slf4j
public class OAuth2FeignRequestInterceptor {}
//implements RequestInterceptor {
//
//    private static final String BEARER_TOKEN_TYPE = "Bearer";
//
//    private final OAuth2RestTemplate oAuth2RestTemplate;
//
//    public OAuth2FeignRequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
//        Assert.notNull(oAuth2RestTemplate, "Context can not be null");
//        this.oAuth2RestTemplate = oAuth2RestTemplate;
//    }
//
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        log.debug("Constructing Header {} for Token {}", HttpHeaders.AUTHORIZATION, BEARER_TOKEN_TYPE);
//        //每次请求自动设置Oauth2的AccessToken 头信息
//        requestTemplate.header(HttpHeaders.AUTHORIZATION,
//                String.format("%s %s",
//                        BEARER_TOKEN_TYPE,
//                        oAuth2RestTemplate.getAccessToken().toString()));
//    }
//}
