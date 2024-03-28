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
    private Boolean enable = false;
    private List<String> includeLoginPaths;
    private List<String> excludeLoginPaths;
}
