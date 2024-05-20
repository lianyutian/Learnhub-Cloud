package com.learnhub.course.domain.po;

import lombok.Data;

import java.util.List;

/**
 * 课程分类层级
 *
 * @author lm
 * @since 2024-05-13 14:14:28
 * @version 1.0
 */
@Data
public class CategoryHierarchy {
    /**
     * 一级分类id
     */
    private Long firstCateId;
    /**
     * 二级分类id
     */
    private Long secondCateId;
    /**
     * 三级分类id
     */
    private Long thirdCateId;

    public void setId(List<Long> categoryIdList) {
        if (firstCateId != null) {
            categoryIdList.add(firstCateId);
        }
        if (secondCateId != null) {
            categoryIdList.add(secondCateId);
        }
        if (thirdCateId != null) {
            categoryIdList.add(thirdCateId);
        }
    }
}
