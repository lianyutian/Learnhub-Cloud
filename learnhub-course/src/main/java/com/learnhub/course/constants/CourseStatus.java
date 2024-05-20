package com.learnhub.course.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 课程状态
 *
 * @author lm
 * @since 2024-05-16 13:47:11
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum CourseStatus {
    /**
     * 课程状态
     */
    NO_UP_SHELF(1, "待上架"),
    SHELF(2, "已上架"),
    DOWN_SHELF(3, "下架"),
    FINISHED(4, "已完结");

    private final Integer status;
    private final String desc;

    public static String desc(Integer status) {
        for (CourseStatus courseStatus : values()) {
            if (courseStatus.getStatus() == status) {
                return courseStatus.getDesc();
            }
        }
        return null;
    }

    public boolean equals(Integer status){
        return this.status.intValue() == status;
    }
}
