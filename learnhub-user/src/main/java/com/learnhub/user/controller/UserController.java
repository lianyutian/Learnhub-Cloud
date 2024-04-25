package com.learnhub.user.controller;

import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.service.IUserDetailService;
import com.learnhub.user.service.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final IUserDetailService userDetailService;

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
    public Long saveUser(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(null);
        return userService.saveUser(userDTO);
    }

    /**
     * 根据id批量查询用户信息
     *
     * @param ids 用户id集合
     * @return 用户集合
     */
    @Hidden
    @GetMapping("/list")
    public List<UserDTO> queryUserDetailByIds(
            @Parameter(description = "用户id的列表") @RequestParam("ids") List<Long> ids) {
        if (CollUtils.isEmpty(ids)) {
            return CollUtils.emptyList();
        }
        // 1.查询列表
        List<UserDetail> list = userDetailService.queryUserDetailByIds(ids);
        // 2.转换
        return BeanUtils.copyList(list, UserDTO.class, (d, u) -> u.setType(d.getType().getValue()));
    }

    @Operation(summary = "根据用户名查询用户信息")
    @PostMapping("/queryUserDetailByName")
    public UserDTO queryUserDetailByName(String username) {
        return userDetailService.queryUserByName(username);
    }

}
