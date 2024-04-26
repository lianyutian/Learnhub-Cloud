package com.learnhub.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 16:15
 */
@Data
@Schema(description = "后台管理用户")
public class StaffVO {
    @Schema(description = "主键", example = "1", name = "id")
    private Long id;
    @Schema(description = "头像", example = "default-user-icon.jpg", name = "icon")
    private String icon;
    @Schema(description = "手机号", example = "13800010002", name = "cellPhone")
    private String cellPhone;
    @Schema(description = "员工姓名", example = "user_138foo0002", name = "name")
    private String name;
    @Schema(description = "角色id", example = "5", name = "roleId")
    private Long roleId;
    @Schema(description = "角色名称", example = "5", name = "roleName")
    private String roleName;
    @Schema(description = "注册时间", example = "2022-07-22", name = "createTime")
    private LocalDateTime createTime;
    @Schema(description = "账号状态", example = "0", name = "status")
    private Integer status;
}
