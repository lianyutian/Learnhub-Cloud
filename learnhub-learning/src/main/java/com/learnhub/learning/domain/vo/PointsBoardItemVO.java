package com.learnhub.learning.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:31
 */
@Data
@Tag(name = "PointsBoardItemVO", description = "积分榜单单项信息")
public class PointsBoardItemVO {
    @Schema(description = "积分值", name = "points")
    private Integer points;

    @Schema(description = "名次", name = "rank")
    private Integer rank;
    
    @Schema(description = "学生姓名", name = "name")
    private String name;
}
