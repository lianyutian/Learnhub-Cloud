package com.learnhub.common.domain.dto;

import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 14:23
 */
@Data
public class LoginUserDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 是否记录
     */
    private Boolean rememberMe;
}
