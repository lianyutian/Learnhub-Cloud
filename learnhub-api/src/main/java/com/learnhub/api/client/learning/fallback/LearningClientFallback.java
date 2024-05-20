package com.learnhub.api.client.learning.fallback;

import com.learnhub.api.client.learning.LearningClient;
import com.learnhub.api.dto.learning.LearningLessonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * LearningClientFallback
 *
 * @author lm
 * @since 2024-05-13 16:21:15
 * @version 1.0
 */
@Slf4j
public class LearningClientFallback implements FallbackFactory<LearningClient> {
    @Override
    public LearningClient create(Throwable cause) {
        log.error("查询学习服务异常", cause);
        return new LearningClient() {
            @Override
            public LearningLessonDTO queryLearningRecordByCourseId(Long courseId) {
                return null;
            }
        };
    }
}
