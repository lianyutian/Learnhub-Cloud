package com.github.learnhub.user.controller;

import com.github.learnhub.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/27 15:07
 */
@RestController
@RequestMapping("user")
@AllArgsConstructor
@Tag(name = "用户模块")
public class UserController {
    private final IUserService userService;

    @Operation(summary = "hello", description = "hello1")
    @GetMapping("/hello")
    public String hello() {
        return userService.hello();
    }

}
