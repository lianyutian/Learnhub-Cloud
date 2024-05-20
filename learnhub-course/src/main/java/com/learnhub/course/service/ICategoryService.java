package com.learnhub.course.service;

import com.learnhub.course.domain.vo.SimpleCategoryVO;

import java.util.List;

/**
 * 课程分类Service
 *
 * @author lm
 * @since 2024-05-11 17:38:16
 * @version 1.0
 */
public interface ICategoryService {
    /**
     * 获取所有的课程分类信息，只包含id,名称,课程分类关系
     *
     * @param admin 是否是管理员
     * @return 课程分类信息
     */
    List<SimpleCategoryVO> queryAllCategorys(Boolean admin);

    /**
     * 校验课程分类是否符合要求,并按顺序返回一二三级课程分类id列表
     *
     * @param thirdCateId 课程分类id
     * @return 课程分类信息
     */
    List<Long> checkCategory(Long thirdCateId);
}
