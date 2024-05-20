package com.learnhub.api.client.learning;

import com.learnhub.api.client.learning.fallback.LearningClientFallback;
import com.learnhub.api.dto.learning.LearningLessonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * LearningClient
 *
 * @author lm
 * @since 2024-05-13 16:20:44
 * @version 1.0
 */
@FeignClient(value = "learning-service", fallbackFactory = LearningClientFallback.class)
public interface LearningClient {
    /**
     * 查询当前用户指定课程的学习进度
     *
     * @param courseId 课程id
     * @return 课表信息、学习记录及进度信息
     */
    @GetMapping("/learning-records/course/{courseId}")
    LearningLessonDTO queryLearningRecordByCourseId(@PathVariable("courseId") Long courseId);
}
