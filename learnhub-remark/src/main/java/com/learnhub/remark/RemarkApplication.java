package com.learnhub.remark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/8 20:25]
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.learnhub.remark.mapper")
@EnableScheduling
public class RemarkApplication {
    public static void main(String[] args) {
        SpringApplication.run(RemarkApplication.class, args);
    }
}
