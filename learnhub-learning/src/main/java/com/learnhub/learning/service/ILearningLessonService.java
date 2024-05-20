package com.learnhub.learning.service;

import com.learnhub.learning.domain.po.LearningLesson;
import com.learnhub.learning.domain.po.LearningRecord;

/**
 * 学习课程服务
 *
 * @author lm
 * @since 2024-05-13 16:32:11
 * @version 1.0
 */
public interface ILearningLessonService {
    /**
     * 根据用户id和课程id查询学习课程
     *
     * @param userId  用户id
     * @param courseId 课程id
     * @return 学习课程
     */
    LearningLesson queryByUserAndCourseId(Long userId, Long courseId);

    /**
     * 更新学习课程
     *
     * @param lesson 学习课程
     */
    void updateLearningLesson(LearningLesson lesson);
}
