package com.learnhub.course.service;

import com.learnhub.course.domain.dto.CourseBaseInfoSaveDTO;
import com.learnhub.course.domain.vo.CourseSaveVO;
import com.learnhub.course.domain.vo.NameExistVO;

/**
 * 草稿课程服务类
 *
 * @author lm
 * @since 2024-05-15 17:19:54
 * @version 1.0
 */
public interface ICourseDraftService {
    /**
     * 校验课程名称是否已经存在
     *
     * @param name 课程名称
     * @param id 课程id
     * @return 课程名称是否存在
     */
    NameExistVO checkName(String name, Long id);

    /**
     * 保存课程基本信息
     *
     * @param courseBaseInfoSaveDTO 课程基本信息保存DTO
     * @return 保存结果
     */
    CourseSaveVO saveCourseBaseInfo(CourseBaseInfoSaveDTO courseBaseInfoSaveDTO);
}
