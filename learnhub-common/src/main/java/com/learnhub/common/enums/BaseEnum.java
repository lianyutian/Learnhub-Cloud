package com.learnhub.common.enums;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/13 17:55
 */
public interface BaseEnum {
    /**
     * 获取值
     *
     * @return value
     */
    int getValue();

    /**
     * 获取描述
     *
     * @return desc
     */
    String getDesc();

    /**
     * equals
     *
     * @param value value
     * @return boolean
     */
    default boolean equalsValue(Integer value){
        if (value == null) {
            return false;
        }
        return getValue() == value;
    }
}
