package com.learnhub.learning.mapper;

import com.learnhub.learning.domain.po.PointsBoard;
import com.learnhub.learning.domain.query.PointsBoardQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
     * @param tableName 历史积分榜单表名
     * @param query 分页查询参数
     */
    void queryPointsBoardsByPage(String tableName, PointsBoardQuery query);

    /**
     * 创建赛季积分排行榜表
     *
     * @param tableName 表名
     */
    void createPointsBoardTable(String tableName);

    /**
     * 批量保存赛季积分排行榜
     *
     * @param tableName 积分榜单表名
     * @param boardList 赛季积分排行榜
     */
    void saveBatch(String tableName, List<PointsBoard> boardList);
}
