package com.learnhub.promotion.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.learnhub.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠类型
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/22 16:51
 */
@Getter
@AllArgsConstructor
public enum DiscountType implements BaseEnum {
    /**
     * 优惠类型
     */
    PER_PRICE_DISCOUNT(1, "每满减"),
    RATE_DISCOUNT(2, "折扣"),
    NO_THRESHOLD(3, "无门槛"),
    PRICE_DISCOUNT(4, "满减");

    @JsonValue
    private final int value;
    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DiscountType of(Integer value) {
        if (value == null) {
            return null;
        }
        for (DiscountType status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
