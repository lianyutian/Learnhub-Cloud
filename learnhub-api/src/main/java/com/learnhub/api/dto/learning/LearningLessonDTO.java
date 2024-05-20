package com.learnhub.api.dto.learning;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 学习课表进度信息
 *
 * @author lm
 * @since 2024-05-13 16:22:17
 * @version 1.0
 */
@Data
@Schema(description = "学习课表进度信息", name = "LearningLessonDTO")
public class LearningLessonDTO {
    @Schema(description = "课表id", name = "id")
    private Long id;
    @Schema(description = "最近学习的小节id", name = "latestSectionId")
    private Long latestSectionId;
    @Schema(description = "学习过的小节的记录", name = "records")
    private List<LearningRecordDTO> records;
}
