package com.learnhub.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 13:40
 */
@Data
@Schema(description = "用户详情", name = "UserDetailVO")
public class UserDetailVO {
    @Schema(description = "用户id", example = "1", name = "id")
    private Long id;
    @Schema(description = "名字", example = "张三", name = "name")
    private String name;
    @Schema(description = "头像", example = "default-icon.jpg", name = "icon")
    private String icon;
    @Schema(description = "手机号", example = "13800010004", name = "cellPhone")
    private String cellPhone;
    @Schema(description = "用户名", example = "13800010004", name = "username")
    private String username;
    @Schema(description = "邮箱", name = "email")
    private String email;
    @Schema(description = "QQ号码", name = "qq")
    private String qq;
    @Schema(description = "个人介绍", name = "intro")
    private String intro;
    @Schema(description = "省", name = "province")
    private String province;
    @Schema(description = "市", name = "city")
    private String city;
    @Schema(description = "区", name = "district")
    private String district;
    @Schema(description = "性别：0-男性，1-女性", example = "0", name = "gender")
    private Integer gender;
    @Schema(description = "注册时间", example = "2022-07-12", name = "createTime")
    private LocalDateTime createTime;
    @Schema(description = "角色名称", example = "教师", name = "roleName")
    private String roleName;

}
