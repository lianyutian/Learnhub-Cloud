package com.learnhub.common.autoconfigure.mybatis.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键注解
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/3 15:47
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoId {
    /**
     * @return id类型（默认为雪花id）
     */
    IdType value() default IdType.SNOWFLAKE;

    /**
     * id类型
     */
    enum IdType {
        /**
         * UUID去掉“-”
         */
        UUID,
        /**
         * 雪花id
         */
        SNOWFLAKE
    }
}
