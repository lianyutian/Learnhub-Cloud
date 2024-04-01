package com.learnhub.auth.domain.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 20:57]
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginRecord {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户id
     */
    private String cellPhone;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登出时间
     */
    private LocalDateTime logoutTime;

    /**
     * 登录日期
     */
    private LocalDate loginDate;

    /**
     * 登录时长，单位是秒
     */
    private Long duration;

    /**
     * ip地址
     */
    private String ipv4;

}
