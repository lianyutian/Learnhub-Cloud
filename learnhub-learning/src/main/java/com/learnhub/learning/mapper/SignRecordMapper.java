package com.learnhub.learning.mapper;

import com.learnhub.learning.enums.PointsRecordType;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 17:45
 */
@Mapper
public interface SignRecordMapper {
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
}
