package com.gknoone.cloud.plus.microservices.apollo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author gknoone
 * @date 2019-08-13 14:32
 */
@Data
@Configuration
public class UserConfig {
    @Value("${username:sb}")
    private String username;
}
