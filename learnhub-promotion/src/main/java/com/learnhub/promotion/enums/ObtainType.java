package com.learnhub.promotion.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.learnhub.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:04
 */
@Getter
@AllArgsConstructor
public enum ObtainType implements BaseEnum {
    /**
     * 领取类型
     */
    PUBLIC(1, "手动领取"),
    ISSUE(2, "发放兑换码");

    @JsonValue
    private final int value;
    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ObtainType of(Integer value) {
        if (value == null) {
            return null;
        }
        for (ObtainType status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}

