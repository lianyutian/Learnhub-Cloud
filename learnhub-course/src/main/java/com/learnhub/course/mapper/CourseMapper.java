package com.learnhub.course.mapper;

import com.learnhub.course.domain.po.CategoryHierarchy;
import com.learnhub.course.domain.po.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CategoryMapper
 *
 * @author lm
 * @since 2024-05-11 22:17:43
 * @version 1.0
 */
@Mapper
public interface CourseMapper {

    /**
     * 查询已上架课程分类id集合
     *
     * @return 分类id集合
     */
    List<CategoryHierarchy> queryUpCategoryIds();

    /**
     * 根据id查询课程信息
     *
     * @param id 课程id
     * @return 课程信息
     */
    Course queryCourseById(Long id);

    /**
     * 根据课程名称和id查询课程数量
     *
     * @param name 课程名称
     * @param id   课程id
     * @return 课程数量
     */
    int countCourseByNameAndId(String name, Long id);
}
