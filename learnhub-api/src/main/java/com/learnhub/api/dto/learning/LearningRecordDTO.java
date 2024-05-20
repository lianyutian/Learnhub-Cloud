package com.learnhub.api.dto.learning;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 小节信息及学习进度
 *
 * @author lm
 * @since 2024-05-13 16:23:22
 * @version 1.0
 */
@Data
@Schema(description = "小节信息及学习进度", name = "LearningRecordDTO")
public class LearningRecordDTO {
    @Schema(description = "对应节的id", name = "sectionId")
    private Long sectionId;
    @Schema(description = "视频的当前观看时长，单位秒", name = "moment")
    private Integer moment;
    @Schema(description = "是否完成学习，默认false", name = "finished")
    private Boolean finished;
}
