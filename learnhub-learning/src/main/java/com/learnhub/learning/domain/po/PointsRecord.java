package com.learnhub.learning.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import com.learnhub.learning.enums.PointsRecordType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学习积分记录
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/15 17:43
 */
@Data
public class PointsRecord {
    /**
     * 积分记录表id
     */
    @AutoId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 积分方式：1-课程学习，2-每日签到，3-课程问答， 4-课程笔记，5-课程评价
     */
    private PointsRecordType type;

    /**
     * 积分值
     */
    private Integer points;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
