package com.learnhub.promotion.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.learnhub.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 16:52
 */
@Getter
@AllArgsConstructor
public enum ExchangeCodeStatus implements BaseEnum {
    /**
     * 兑换码状态
     */
    UNUSED(1, "待兑换"),
    USED(2, "已兑换"),
    EXPIRED(3, "兑换活动已结束");

    @JsonValue
    private final int value;
    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ExchangeCodeStatus of(Integer value) {
        if (value == null) {
            return null;
        }
        for (ExchangeCodeStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }

    public static String desc(Integer value) {
        ExchangeCodeStatus status = of(value);
        return status == null ? "" : status.desc;
    }
}
