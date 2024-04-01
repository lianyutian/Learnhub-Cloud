package com.learnhub.authsdk.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/29 16:45
 */
@Data
@Component
@ConfigurationProperties("learnhub.auth")
public class AuthProperties {

    /**
     * 鉴权排除路径
     */
    private Set<String> excludePath;
}
