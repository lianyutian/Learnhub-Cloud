package com.learnhub.course.service;

import com.learnhub.api.dto.course.CatalogueDTO;

import java.util.List;

/**
 * 目录服务类
 *
 * @author lm
 * @since 2024-05-14 10:30:03
 * @version 1.0
 */
public interface ICourseCatalogueService {
    /**
     * 根据课程id查询课程目录
     *
     * @param courseId      课程id
     * @param withPractice 是否带练习
     * @return 目录列表
     */
    List<CatalogueDTO> queryCourseCatalogues(Long courseId, boolean withPractice);

}
