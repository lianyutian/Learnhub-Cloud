package com.learnhub.user.domain.po;

import com.learnhub.common.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 19:58]
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDetail {
    /**
     * 关联用户id
     */
    private Long id;

    /**
     * 用户类型：1-员工, 2-普通学员，3-老师
     */
    private UserType type;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别：0-男性，1-女性
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 岗位
     */
    private String job;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 个人介绍
     */
    private String intro;

    /**
     * 形象照地址
     */
    private String photo;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    private Long creater;

    /**
     * 更新者id
     */
    private Long updater;

    /**
     * 部门id
     */
    private Long depId;
}
