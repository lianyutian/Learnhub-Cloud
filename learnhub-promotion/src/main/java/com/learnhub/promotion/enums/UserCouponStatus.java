package com.learnhub.promotion.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.learnhub.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:06
 */
@Getter
@AllArgsConstructor
public enum UserCouponStatus implements BaseEnum {
    /**
     * 用户优惠券状态
     */
    UNUSED(1, "未使用"),
    USED(2, "已使用"),
    EXPIRED(3, "已过期"),
    ;

    @JsonValue
    private final int value;
    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserCouponStatus of(Integer value) {
        if (value == null) {
            return null;
        }
        for (UserCouponStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }

    public static String desc(Integer value) {
        UserCouponStatus status = of(value);
        return status == null ? "" : status.desc;
    }
}
