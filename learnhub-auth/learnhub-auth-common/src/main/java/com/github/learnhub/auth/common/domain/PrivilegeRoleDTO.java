package com.github.learnhub.auth.common.domain;

import lombok.Data;

import java.util.Set;

/**
 * 权限角色DTO
 *
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/27 19:40]
 */
@Data
public class PrivilegeRoleDTO {
    private Long id;
    private String antPath;
    private Boolean internal;
    private Set<Long> roles;
}
