package com.learnhub.common.utils;

/**
 * 对原对象进行计算，设置到目标对象中
 *
 * @author liming
 * @version 1.0
 * @since 2024/3/12 14:59
 */
public interface Convert<R,T> {
    /**
     * 对原对象进行计算，设置到目标对象中
     *
     * @param origin 原始对象
     * @param target 目标对象
     */
    void convert(R origin, T target);
}
