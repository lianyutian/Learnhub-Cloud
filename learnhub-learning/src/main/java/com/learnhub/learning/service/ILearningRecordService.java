package com.learnhub.learning.service;

import com.learnhub.api.dto.learning.LearningLessonDTO;
import com.learnhub.learning.domain.dto.LearningRecordFormDTO;
import com.learnhub.learning.domain.po.LearningRecord;

/**
 * 学习记录服务
 *
 * @author lm
 * @since 2024-05-13 16:27:01
 * @version 1.0
 */
public interface ILearningRecordService {
    /**
     * 根据课程id查询学习记录
     *
     * @param courseId 课程id
     * @return 学习记录
     */
    LearningLessonDTO queryLearningRecordByCourseId(Long courseId);

    /**
     * 提交学习记录
     *
     * @param formDTO 学习记录
     */
    void saveLearningRecord(LearningRecordFormDTO formDTO);

    /**
     * 更新学习记录
     *
     * @param record 学习记录
     */
    void updateLearningRecord(LearningRecord record);
}
