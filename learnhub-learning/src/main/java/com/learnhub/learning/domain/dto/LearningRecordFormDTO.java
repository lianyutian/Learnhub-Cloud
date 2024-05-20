package com.learnhub.learning.domain.dto;

import com.learnhub.common.validate.annotations.EnumValid;
import com.learnhub.learning.enums.SectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学习记录DTO
 *
 * @author lm
 * @since 2024-05-13 16:54:53
 * @version 1.0
 */
@Data
@Schema(description = "学习记录", name = "LearningRecordFormDTO")
public class LearningRecordFormDTO {
    @Schema(description = "小节类型：1-视频，2-考试", name = "sectionType")
    @NotNull(message = "小节类型不能为空")
    @EnumValid(enumeration = {1, 2}, message = "小节类型错误，只能是：1-视频，2-考试")
    private SectionType sectionType;

    @Schema(description = "课表id", name = "lessonId")
    @NotNull(message = "课表id不能为空")
    private Long lessonId;

    @Schema(description = "对应节的id", name = "sectionId")
    @NotNull(message = "节的id不能为空")
    private Long sectionId;

    @Schema(description = "视频总时长，单位秒", name = "duration")
    private Integer duration;

    @Schema(description = "视频的当前观看时长，单位秒，第一次提交填0", name = "moment")
    private Integer moment;

    @Schema(description = "提交时间", name = "commitTime")
    private LocalDateTime commitTime;
}
