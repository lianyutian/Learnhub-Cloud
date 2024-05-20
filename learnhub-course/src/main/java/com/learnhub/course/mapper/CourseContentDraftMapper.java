package com.learnhub.course.mapper;

import com.learnhub.course.domain.po.CourseContentDraft;
import org.apache.ibatis.annotations.Mapper;

/**
 * CourseContentDraftMapper
 *
 * @author lm
 * @since 2024-05-16 14:07:29
 * @version 1.0
 */
@Mapper
public interface CourseContentDraftMapper {
    /**
     * 保存课程内容草稿
     *
     * @param courseContentDraft 课程内容草稿
     */
    void saveCourseContentDraft(CourseContentDraft courseContentDraft);

    /**
     * 更新课程内容草稿
     *
     * @param courseContentDraft 课程内容草稿
     */
    void updateCourseContentDraft(CourseContentDraft courseContentDraft);
}
