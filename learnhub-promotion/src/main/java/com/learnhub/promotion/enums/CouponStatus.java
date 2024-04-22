package com.learnhub.promotion.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.learnhub.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券状态
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/22 16:47
 */
@Getter
@AllArgsConstructor
public enum CouponStatus implements BaseEnum {
    /**
     * 优惠券状态
     */
    DRAFT(1, "待发放"),
    UN_ISSUE(2, "未开始"),
    ISSUING(3, "发放中"),
    FINISHED(4, "发放结束"),
    PAUSE(5, "暂停");

    @JsonValue
    private final int value;
    private final String desc;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CouponStatus of(Integer value) {
        if (value == null) {
            return null;
        }
        for (CouponStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }

    public static String desc(Integer value) {
        CouponStatus status = of(value);
        return status == null ? "" : status.desc;
    }
}
