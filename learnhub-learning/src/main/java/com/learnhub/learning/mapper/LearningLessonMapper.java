package com.learnhub.learning.mapper;

import com.learnhub.learning.domain.po.LearningLesson;
import org.apache.ibatis.annotations.Mapper;

/**
 * LearningLessonMapper
 *
 * @author lm
 * @since 2024-05-13 16:38:45
 * @version 1.0
 */
@Mapper
public interface LearningLessonMapper {
    /**
     * 根据用户id和课程id查询学习课表
     *
     * @param userId  用户id
     * @param courseId 课程id
     * @return 学习课表
     */
    LearningLesson queryByUserAndCourseId(Long userId, Long courseId);

    /**
     * 根据id查询学习课表
     *
     * @param lessonId 课表id
     * @return 学习课表
     */
    LearningLesson queryLearningLessonById(Long lessonId);

    /**
     * 更新课表
     *
     * @param learnedSections 已学章节数
     * @param allLearned 是否全部学习完成
     * @param id 课表id
     */
    void updateLearningLesson(Integer learnedSections, boolean allLearned, Long id);

    void updateLearningLessonWithLesson(LearningLesson lesson);
}
