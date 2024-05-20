package com.learnhub.course.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 课程保存返回VO
 *
 * @author lm
 * @since 2024-05-15 17:50:51
 * @version 1.0
 */
@Data
@Schema(description = "课程保存结果", name = "CourseSaveVO")
@AllArgsConstructor
@NotNull
@Builder
public class CourseSaveVO {
    @Schema(description = "课程id", name = "id")
    private Long id;
}
