package com.learnhub.api.config;

import com.learnhub.api.client.remark.fallback.RemarkClientFallback;
import com.learnhub.api.client.user.fallback.UserClientFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 22:00]
 */
@Configuration
public class FallbackConfig {
    @Bean
    public UserClientFallback userClientFallback(){
        return new UserClientFallback();
    }

    @Bean
    public RemarkClientFallback remarkClientFallback(){
        return new RemarkClientFallback();
    }
}
