package com.learnhub.user.domain.po;

import com.learnhub.common.enums.UserType;
import com.learnhub.user.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 15:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String cellPhone;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户状态：0-禁用，1-正常
     */
    private UserStatus status;

    /**
     * 用户类型：1-其他员工, 2-普通学员，3-老师
     */
    private UserType type;

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
     * 修改者id
     */
    private Long updater;
}
