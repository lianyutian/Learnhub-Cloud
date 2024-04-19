package com.learnhub.learning.mapper;

import com.learnhub.learning.domain.po.PointsBoardSeason;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 查询赛季信息列表
     *
     * @param now 当前时间
     * @return 赛季信息列表
     */
    List<PointsBoardSeason> queryPointsBoardSeasons(LocalDateTime now);
}
