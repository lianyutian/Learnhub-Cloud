package com.learnhub.api.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目与业务关联信息
 *
 * @author lm
 * @since 2024-05-14 10:49:18
 * @version 1.0
 */
@Data
@Schema(description = "题目与业务关联信息", name = "QuestionBizDTO")
@Accessors(chain = true)
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class QuestionBizDTO {
    @Schema(description = "业务id，要关联问题的某业务id，例如小节id", name = "bizId")
    private Long bizId;

    @Schema(description = "题目id", name = "questionId")
    private Long questionId;
}
