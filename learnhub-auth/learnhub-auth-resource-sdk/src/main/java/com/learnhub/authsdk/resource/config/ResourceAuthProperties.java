package com.learnhub.authsdk.resource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:36]
 */
@Data
@ConfigurationProperties(prefix = "learnhub.auth.resource")
public class ResourceAuthProperties {
    /**
     * 是否开启登录拦截功能，如果开启则需要指定拦截路径，默认拦截所有
     */
    private Boolean enable = false;

    /**
     * 要拦截的路径，例如：/user/**
     */
    private List<String> includeLoginPaths;

    /**
     * 不拦截的路径，例如：/user/**
     */
    private List<String> excludeLoginPaths;
}
