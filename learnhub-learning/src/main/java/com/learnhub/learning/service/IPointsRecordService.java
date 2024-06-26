package com.learnhub.learning.service;

import com.learnhub.learning.domain.vo.PointsStatisticsVO;
import com.learnhub.learning.enums.PointsRecordType;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 17:52
 */
public interface IPointsRecordService {

    /**
     * 添加积分记录
     *
     * @param userId 用户id
     * @param points 积分值
     * @param type 积分类型
     */
    void addPointsRecord(Long userId, int points, PointsRecordType type);

    /**
     * 查询我的今日积分
     *
     * @return 今日积分
     */
    List<PointsStatisticsVO> queryMyPointsToday();

    /**
     * 创建指定赛季的积分记录表
     *
     * @param season 赛季
     */
    void createPointsRecordTableOfLastSeason(Integer season);

    /**
     * 迁移指定赛季的积分记录
     *
     * @param season 赛季
     */
    void migrationPointsRecord(Integer season);

    /**
     * 清除指定赛季的积分记录
     *
     * @param season 赛季
     */
    void clearPointsRecord(Integer season);
}
