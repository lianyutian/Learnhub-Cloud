package com.learnhub.common.validate;

/**
 * 实现后在接口访问时如果接口实现了这个接口会被自动自行接口check进行校验
 *
 * @author lm
 * @since 2024-05-15 17:32:47
 * @version 1.0
 */
public interface Checker<T> {
    /**
     * 用于实现validation不能校验的数据逻辑
     */
    default void check(){

    }

    default void check(T data){
    }
}
