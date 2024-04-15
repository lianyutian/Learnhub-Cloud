package com.learnhub.learning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/27 20:16]
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("知学云用户系统API")
                        .contact(new Contact().name("lm"))
                        .version("1.0")
                        .description("知学云学习中心服务模块API")
                        .license(new License().name("Apache 2.0"))
                );
    }
}
