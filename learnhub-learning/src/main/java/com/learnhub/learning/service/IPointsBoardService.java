package com.learnhub.learning.service;

import com.learnhub.learning.domain.po.PointsBoard;
import com.learnhub.learning.domain.query.PointsBoardQuery;
import com.learnhub.learning.domain.vo.PointsBoardVO;

import java.util.List;

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

    /**
     * 创建指定赛季的积分排行榜
     *
     * @param season 赛季
     */
    void createPointsBoardTableBySeason(Integer season);

    /**
     * 查询当前赛季的积分排行榜
     *
     * @param key      关键字
     * @param pageNo   页码
     * @param pageSize 页大小
     * @return 积分排行榜
     */
    List<PointsBoard> queryCurrentBoardList(String key, Integer pageNo, Integer pageSize);
}
