package com.learnhub.learning.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PointsBoard {
    /**
     * 榜单id
     */
    private Long id;

    /**
     * 学生id
     */
    private Long userId;

    /**
     * 积分值
     */
    private Integer points;

    /**
     * 名次，只记录赛季前100
     */
    private Integer rank;

    /**
     * 赛季id
     */
    private Integer season;

}
