package com.learnhub.learning.mapper;

import com.learnhub.learning.domain.po.PointsBoard;
import com.learnhub.learning.domain.query.PointsBoardQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 17:02
 */
@Mapper
public interface PointsBoardMapper {

    /**
     * 根据赛季id查询积分排行榜
     *
     * @param userId 赛季id
     * @return 积分排行榜
     */
    PointsBoard queryPointsBoardByUserId(Long userId);

    /**
     * 根据赛季id查询积分排行榜
     *
     * @param query 分页查询参数
     */
    void queryPointsBoardsByPage(PointsBoardQuery query);
}
