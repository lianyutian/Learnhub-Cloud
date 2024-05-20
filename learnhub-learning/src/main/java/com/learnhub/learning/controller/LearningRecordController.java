package com.learnhub.learning.controller;

import com.learnhub.api.dto.learning.LearningLessonDTO;
import com.learnhub.learning.domain.dto.LearningRecordFormDTO;
import com.learnhub.learning.service.ILearningRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * LearningRecordController
 *
 * @author lm
 * @since 2024-05-13 16:25:56
 * @version 1.0
 */
@RestController
@RequestMapping("/learning-records")
@Tag(name = "学习记录的相关接口")
@AllArgsConstructor
public class LearningRecordController {
    private final ILearningRecordService learningRecordService;

    @Operation(summary = "查询指定课程的学习记录")
    @GetMapping("/course/{courseId}")
    public LearningLessonDTO queryLearningRecordByCourseId(
            @Parameter(name = "课程id", example = "2") @PathVariable("courseId") Long courseId) {
        return learningRecordService.queryLearningRecordByCourseId(courseId);
    }

    @Operation(summary = "提交学习记录")
    @PostMapping("saveLearningRecord")
    public void saveLearningRecord(@RequestBody LearningRecordFormDTO formDTO){
        learningRecordService.saveLearningRecord(formDTO);
    }
}
