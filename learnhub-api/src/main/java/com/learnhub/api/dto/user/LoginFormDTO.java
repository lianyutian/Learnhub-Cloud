package com.learnhub.api.dto.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 13:59
 */
@Data
@Schema(name = "登录表单实体")
public class LoginFormDTO {
    @Schema(name = "登录方式：1-密码登录; 2-验证码登录", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer type;
    @Schema(name = "用户名", example = "jack")
    private String username;
    @Schema(name = "手机号", example = "13800010001")
    private String cellPhone;
    @Schema(name = "密码", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String password;
    @Schema(name = "7天免密登录", example = "true")
    private Boolean rememberMe;
}
