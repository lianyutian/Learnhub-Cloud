package com.learnhub.course.service;

import java.util.List;

/**
 * 课程老师关系表服务类
 *
 * @author lm
 * @since 2024-05-14 10:30:22
 * @version 1.0
 */
public interface ICourseTeacherService {
    /**
     * 根据课程id查询课程老师id
     *
     * @param courseId 课程id
     * @return 课程老师id
     */
    List<Long> queryTeacherIdByCourseId(Long courseId);
}
