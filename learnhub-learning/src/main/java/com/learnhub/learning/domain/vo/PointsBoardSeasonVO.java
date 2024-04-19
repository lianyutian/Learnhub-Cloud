package com.learnhub.learning.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/18 14:58
 */
@Data
@Schema(description = "积分排行榜赛季信息")
public class PointsBoardSeasonVO {

    @Schema(description = "赛季id，1，就是第1赛季", name = "id")
    private Integer id;

    @Schema(description = "赛季名称，例如：第1赛季", name = "name")
    private String name;

    @Schema(description = "赛季开始时间", name = "beginTime")
    private LocalDate beginTime;

    @Schema(description = "赛季结束时间", name = "endTime")
    private LocalDate endTime;
}
