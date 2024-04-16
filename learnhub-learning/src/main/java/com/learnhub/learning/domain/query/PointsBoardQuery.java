package com.learnhub.learning.domain.query;

import com.learnhub.common.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Tag(name="PointsBoardQuery", description = "积分排行榜f分页查询参数")
public class PointsBoardQuery extends PageQuery {

    @Schema(description = "赛季id，为null或者0则代表查询当前赛季", name = "season")
    private Long season;
}
