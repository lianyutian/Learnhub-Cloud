package com.learnhub.user.enums;

import com.learnhub.common.enums.BaseEnum;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.user.constants.UserErrorInfo;
import lombok.Getter;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 15:06
 */
@Getter
public enum UserStatus implements BaseEnum {
    /**
     * 用户状态
     */
    FROZEN(0, "禁止使用"),
    NORMAL(1, "已激活"),
    ;
    final int value;
    final String desc;

    UserStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserStatus of(int value) {
        if (value == 0) {
            return FROZEN;
        }
        if (value == 1) {
            return NORMAL;
        }
        throw new BadRequestException(UserErrorInfo.Msg.INVALID_USER_STATUS);
    }
}
