package com.learnhub.remark.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 15:59
 */
@Schema(description = "点赞记录DTO", name = "LikeRecordFormDTO")
@Data
public class LikeRecordFormDTO {
    @Schema(description = "点赞业务id", name = "bizId", example = "1578558664933920770", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "业务id不能为空")
    private Long bizId;

    @Schema(description = "点赞业务类型", name = "bizType", example = "QA", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "业务类型不能为空")
    private String bizType;

    @Schema(description = "是否点赞，true：点赞；false：取消点赞", name = "liked", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否点赞不能为空")
    private Boolean liked;
}
