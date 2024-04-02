package com.learnhub.auth.common.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "权限角色DTO")
public class PrivilegeRoleDTO {
    @Schema(description = "权限id", name = "id")
    private Long id;
    @Schema(description = "请求路径", name = "antPath")
    private String antPath;
    @Schema(description = "是否内部接口权限", name = "internal")
    private Boolean internal;
    @Schema(description = "角色列表", name = "roles")
    private Set<Long> roles;
}
