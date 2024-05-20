package com.learnhub.course.mapper;

import com.learnhub.course.domain.po.CourseDraft;
import org.apache.ibatis.annotations.Mapper;

/**
 * CourseDraftMapper
 *
 * @author lm
 * @since 2024-05-15 17:23:27
 * @version 1.0
 */
@Mapper
public interface CourseDraftMapper {
    /**
     * 统计草稿中其他课程
     *
     * @param name 课程名称
     * @param id   课程id
     * @return 课程数量
     */
    int countCourseByNameAndId(String name, Long id);

    /**
     * 保存课程草稿
     *
     * @param courseDraft 课程草稿
     */
    void saveCourseDraft(CourseDraft courseDraft);

    /**
     * 更新课程草稿
     *
     * @param courseDraft 课程草稿
     */
    void updateCourseDraft(CourseDraft courseDraft);
}
