package com.learnhub.api.dto.user;

import com.learnhub.common.constants.RegexConstants;
import com.learnhub.common.validate.annotations.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/3 14:41
 */
@Data
@Schema(description = "用户详情", name = "UserDTO")
public class UserDTO {
    @Schema(description = "用户id", name = "id", example = "1")
    private Long id;
    @Schema(description = "手机", name = "cellPhone", example = "13890011009")
    @Pattern(regexp = RegexConstants.PHONE_PATTERN, message = "手机号格式错误")
    private String cellPhone;
    @Schema(description = "用户名称/昵称", name = "name", example = "李四")
    private String name;
    @Schema(description = "用户类型，1-其他员工,2-普通学员，3-老师", name = "type", example = "2")
    @EnumValid(enumeration = {1, 2, 3}, message = "用户类型错误")
    @NotNull
    private Integer type;
    @Schema(description = "角色id，老师和学生不用填", name = "roleId", example = "5")
    private Long roleId;
    @Schema(description = "头像", name = "icon", example = "default-user-icon.jpg")
    private String icon;
    @Schema(description = "岗位", name = "job", example = "讲师")
    private String job;
    @Schema(description = "个人介绍", name = "intro", example = "高级Java讲师")
    private String intro;
    @Schema(description = "形象照地址", name = "photo", example = "default-teacher-photo.jpg")
    private String photo;
    @Schema(description = "用户名", name = "username", example = "13800010004")
    private String username;
    @Schema(description = "邮箱", name = "email")
    @Email
    private String email;
    @Schema(description = "QQ号码", name = "qq")
    private String qq;
    @Schema(description = "省", name = "province")
    private String province;
    @Schema(description = "市", name = "city")
    private String city;
    @Schema(description = "区", name = "district")
    private String district;
    @Schema(description = "性别：0-男性，1-女性", name = "gender", example = "0")
    @EnumValid(enumeration = {0, 1}, message = "性别格式不正确")
    private Integer gender;
}
