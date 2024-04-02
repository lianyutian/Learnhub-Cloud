package com.learnhub.auth.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 13:55
 */
@Data
@Schema(description = "API权限", name = "PrivilegeDTO")
public class PrivilegeDTO {
    @Schema(description = "权限id", name = "id", example = "1")
    private Long id;
    @Schema(description = "权限所属菜单id", name = "menuId", example = "1")
    private Long menuId;
    @Schema(description = "权限说明", name = "intro", example = "新增员工")
    @NotNull(message = "权限说明不能为空")
    private String intro;
    @Schema(description = "API请求方式", name = "method", example = "GET")
    @Pattern(regexp = "^GET|POST|PUT|DELETE$", message = "请求方式必须是大写")
    private String method;
    @Schema(description = "API请求路径", name = "uri", example = "/account/staff")
    @NotNull(message = "uri不能为空")
    private String uri;
    @Schema(description = "是否是内部权限", name = "internal")
    private Boolean internal;
}
