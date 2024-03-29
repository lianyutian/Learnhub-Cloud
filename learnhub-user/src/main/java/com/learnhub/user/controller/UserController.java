package com.learnhub.user.controller;

import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Tag(name = "用户管理接口")
public class UserController {
    private final IUserService userService;

    /**
     * 登录
     *
     * @param loginDTO 登录表单
     * @param isStaff 是否是后台登录
     * @return 登录用户信息
     */
    @Operation(summary = "登录接口")
    @PostMapping("/queryLoginUser/{isStaff}")
    public LoginUserDTO queryLoginUser(
            @Valid @RequestBody LoginFormDTO loginDTO, @PathVariable("isStaff") boolean isStaff) {
        return userService.queryLoginUser(loginDTO, isStaff);
    }

}
