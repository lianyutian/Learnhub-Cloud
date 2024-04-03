package com.learnhub.user.controller;

import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.user.service.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Hidden
    @Operation(summary = "查询登录用户信息")
    @PostMapping("/queryLoginUser/{isStaff}")
    @Parameter(name = "isStaff", description = "是否是后台登录")
    public LoginUserDTO queryLoginUser(
            @Valid @RequestBody LoginFormDTO loginDTO, @PathVariable("isStaff") boolean isStaff) {
        return userService.queryLoginUser(loginDTO, isStaff);
    }

    @Operation(summary = "新增用户，一般是员工或教师")
    @PostMapping("/saveUser")
    public Long saveUser(@Valid @RequestBody UserDTO userDTO){
        userDTO.setId(null);
        return userService.saveUser(userDTO);
    }

}
