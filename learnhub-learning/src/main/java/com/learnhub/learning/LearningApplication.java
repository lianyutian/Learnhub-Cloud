package com.learnhub.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 9:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.learnhub.learning.mapper")
@EnableFeignClients(basePackages = "com.learnhub.api.client")
public class LearningApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class);
    }
}
