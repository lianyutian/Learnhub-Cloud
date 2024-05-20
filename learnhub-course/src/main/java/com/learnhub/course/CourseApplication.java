package com.learnhub.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 课程服务启动类
 *
 * @author lm
 * @since 2024-05-11 17:30:50
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.learnhub.course.mapper")
@EnableFeignClients(basePackages = "com.learnhub.api.client")
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }
}
