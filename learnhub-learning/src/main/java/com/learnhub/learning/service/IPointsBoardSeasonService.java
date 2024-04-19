package com.learnhub.learning.service;

import com.learnhub.learning.domain.po.PointsBoardSeason;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/17 14:57
 */
public interface IPointsBoardSeasonService {
    /**
     * 根据时间查询赛季
     *
     * @param time 时间
     * @return 赛季
     */
    Integer querySeasonByTime(LocalDateTime time);

    /**
     * 查询赛季信息列表
     *
     * @return 赛季信息列表
     */
    List<PointsBoardSeason> queryPointsBoardSeasons(LocalDateTime now);
}
