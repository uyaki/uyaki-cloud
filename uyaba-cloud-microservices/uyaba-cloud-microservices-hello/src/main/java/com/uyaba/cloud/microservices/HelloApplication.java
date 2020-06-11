package com.uyaba.cloud.microservices;

import brave.http.HttpAdapter;
import brave.http.HttpSampler;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.sleuth.instrument.web.ServerSampler;
import org.springframework.cloud.sleuth.instrument.web.SkipPatternProvider;
import org.springframework.context.annotation.Bean;

import java.util.regex.Pattern;

@EnableSwagger2Doc
@SpringBootApplication
@EnableFeignClients
public class HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }
    /**
     * 进行zipkin过滤，返回false时，不会发生到zipkin
     * @param provider SkipPatternProvider设置了很多过滤规则
     * @return HttpSampler
     */
    @Bean(name = ServerSampler.NAME)
    HttpSampler myHttpSampler(SkipPatternProvider provider){
        Pattern pattern = provider.skipPattern();
        return new HttpSampler() {
            @Override
            public <Req> Boolean trySample(HttpAdapter<Req, ?> httpAdapter, Req req) {
                String url = httpAdapter.path(req);
                boolean shouldSkip = pattern.matcher(url).matches();
                if(shouldSkip){
                    return false;
                }
                if(url.equals("/test/1")){
                    return false;
                }
                return null;
            }
        };
    }
}
