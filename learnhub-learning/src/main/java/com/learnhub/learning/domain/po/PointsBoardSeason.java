package com.learnhub.learning.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/17 15:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PointsBoardSeason {
    /**
     * 自增长id，season标示
     */
    @AutoId
    private Integer id;

    /**
     * 赛季名称，例如：第1赛季
     */
    private String name;

    /**
     * 赛季开始时间
     */
    private LocalDate beginTime;

    /**
     * 赛季结束时间
     */
    private LocalDate endTime;
}
