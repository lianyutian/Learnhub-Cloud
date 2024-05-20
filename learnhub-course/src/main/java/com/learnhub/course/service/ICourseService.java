package com.learnhub.course.service;

import com.learnhub.api.dto.course.CourseFullInfoDTO;
import com.learnhub.course.domain.vo.NameExistVO;

import java.util.List;

/**
 * 课程服务类
 *
 * @author lm
 * @since 2024-05-13 13:47:25
 * @version 1.0
 */
public interface ICourseService {

    /**
     * 查询已上架课程的课程分类id列表
     *
     * @return 课程分类id列表
     */
    List<Long> queryUpCategoryIds();

    /**
     * 根据课程id查询课程信息
     *
     * @param id            课程id
     * @param withCatalogue 是否查询目录信息
     * @param withTeachers  是否查询课程老师信息
     * @return 课程信息
     */
    CourseFullInfoDTO queryCourseInfoById(Long id, boolean withCatalogue, boolean withTeachers);

    /**
     * 校验课程名称是否已经存在
     *
     * @param id   课程id
     * @param name 课程名称
     * @return 是否存在
     */
    NameExistVO checkName(String name, Long id);
}
