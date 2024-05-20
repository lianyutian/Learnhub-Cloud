package com.learnhub.learning.service.impl;

import com.learnhub.learning.domain.po.LearningLesson;
import com.learnhub.learning.mapper.LearningLessonMapper;
import com.learnhub.learning.service.ILearningLessonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 学习课程实现类
 *
 * @author lm
 * @since 2024-05-13 16:37:33
 * @version 1.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class LearningLessonServiceImpl implements ILearningLessonService {

    private final LearningLessonMapper learningLessonMapper;

    @Override
    public LearningLesson queryByUserAndCourseId(Long userId, Long courseId) {
        return learningLessonMapper.queryByUserAndCourseId(userId, courseId);
    }

    @Override
    public void updateLearningLesson(LearningLesson lesson) {
        learningLessonMapper.updateLearningLessonWithLesson(lesson);
    }

}
