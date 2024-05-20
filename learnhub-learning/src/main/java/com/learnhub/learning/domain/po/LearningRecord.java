package com.learnhub.learning.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程学习记录
 *
 * @author lm
 * @since 2024-05-13 16:41:28
 * @version 1.0
 */
@Data
public class LearningRecord {
    /**
     * 学习记录的id
     */
    @AutoId
    private Long id;

    /**
     * 对应课表的id
     */
    private Long lessonId;

    /**
     * 对应小节的id
     */
    private Long sectionId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频的当前观看时间点，单位秒
     */
    private Integer moment;

    /**
     * 是否完成学习，默认false
     */
    private Boolean finished;

    /**
     * 第一次观看时间
     */
    private LocalDateTime createTime;

    /**
     * 完成学习的时间
     */
    private LocalDateTime finishTime;

    /**
     * 更新时间（最近一次观看时间）
     */
    private LocalDateTime updateTime;
}
