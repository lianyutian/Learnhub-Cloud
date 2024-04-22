package com.learnhub.promotion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 16:25
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.learnhub.promotion.mapper")
@EnableScheduling
public class PromotionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PromotionApplication.class, args);
    }
}
