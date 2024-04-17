package com.learnhub.learning.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/17 14:58
 */
@Mapper
public interface PointsBoardSeasonMapper {
    /**
     * 根据时间查询赛季
     *
     * @param time 时间
     * @return 赛季
     */
    Integer querySeasonByTime(LocalDateTime time);
}
