package com.learnhub.learning.mapper;

import com.learnhub.learning.domain.po.PointsRecord;
import com.learnhub.learning.enums.PointsRecordType;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 17:45
 */
@Mapper
public interface PointsRecordMapper {
    /**
     * 根据类型和时间查询用户积分
     *
     * @param userId 用户id
     * @param type 积分类型
     * @param begin 开始时间
     * @param end 结束时间
     * @return 积分
     */
    int queryUserPointsByTypeAndDate(Long userId, PointsRecordType type, LocalDateTime begin, LocalDateTime end);

    /**
     * 保存积分记录
     *
     * @param pointsRecord 积分记录
     */
    void savePointsRecord(PointsRecord pointsRecord);

    /**
     * 查询用户积分
     *
     * @param userId 用户id
     * @param begin 开始时间
     * @param end 结束时间
     * @return 积分
     */
    List<PointsRecord> queryUserPointsByDate(Long userId, LocalDateTime begin, LocalDateTime end);
}
