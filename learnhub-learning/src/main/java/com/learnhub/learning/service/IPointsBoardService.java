package com.learnhub.learning.service;

import com.learnhub.learning.domain.query.PointsBoardQuery;
import com.learnhub.learning.domain.vo.PointsBoardVO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:34
 */
public interface IPointsBoardService {
    /**
     * 分页查询指定赛季的积分排行榜
     *
     * @param query 查询参数
     * @return 积分排行榜
     */
    PointsBoardVO queryPointsBoardBySeason(PointsBoardQuery query);
}
