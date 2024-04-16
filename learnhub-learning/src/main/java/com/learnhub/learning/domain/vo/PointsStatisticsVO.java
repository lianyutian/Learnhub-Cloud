package com.learnhub.learning.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 10:06
 */
@Data
@Schema(description = "每日积分统计实体", name = "PointsStatisticsVO")
public class PointsStatisticsVO {
    @Schema(description = "获取积分方式", name = "type")
    private String type;

    @Schema(description = "今日已获取积分值", name = "points")
    private Integer points;

    @Schema(description = "单日积分上限", name = "maxPoints")
    private Integer maxPoints;
}
