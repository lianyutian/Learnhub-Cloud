package com.learnhub.learning.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.learnhub.common.enums.BaseEnum;
import lombok.Getter;

/**
 * 学习计划状态
 *
 * @author lm
 * @since 2024-05-13 16:35:36
 * @version 1.0
 */
@Getter
public enum PlanStatus implements BaseEnum {
    /**
     * 学习计划状态
     */
    NO_PLAN(0, "没有计划"),
    PLAN_RUNNING(1, "计划进行中"),
    ;
    @JsonValue
    final int value;
    final String desc;

    PlanStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PlanStatus of(Integer value){
        if (value == null) {
            return null;
        }
        for (PlanStatus status : values()) {
            if (status.equalsValue(value)) {
                return status;
            }
        }
        return null;
    }
}
