package com.learnhub.auth;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 21:05]
 */
@SpringBootApplication
@MapperScan("com.learnhub.auth.mapper")
@EnableFeignClients(basePackages = "com.learnhub.api.client")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
