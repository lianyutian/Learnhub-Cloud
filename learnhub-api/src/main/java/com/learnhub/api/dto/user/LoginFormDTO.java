package com.learnhub.api.dto.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 13:59
 */
@Data
@Schema(description = "登录表单实体", name = "LoginFormDTO")
public class LoginFormDTO implements Serializable {
    @Schema(description = "登录方式：1-密码登录; 2-验证码登录", name = "type", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer type;
    @Schema(description = "用户名", name = "username", example = "jack")
    private String username;
    @Schema(description = "手机号", name = "cellPhone", example = "13800010001")
    private String cellPhone;
    @Schema(description = "密码", name = "password", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String password;
    @Schema(description = "7天免密登录", name = "rememberMe", example = "true")
    private Boolean rememberMe;
}
