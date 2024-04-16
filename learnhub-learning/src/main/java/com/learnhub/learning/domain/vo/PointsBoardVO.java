package com.learnhub.learning.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:21
 */
@Data
@Schema(description = "积分榜单汇总信息", name = "PointsBoardVO")
public class PointsBoardVO {
    @Schema(description = "我的榜单排名", name = "rank")
    private Integer rank;

    @Schema(description = "我的积分值", name = "points")
    private Integer points;

    @Schema(description = "前100名上榜人信息", name = "boardList")
    private List<PointsBoardItemVO> boardList;
}
