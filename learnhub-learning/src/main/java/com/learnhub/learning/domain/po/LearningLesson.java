package com.learnhub.learning.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import com.learnhub.learning.enums.LessonStatus;
import com.learnhub.learning.enums.PlanStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 学习课程表
 *
 * @author lm
 * @since 2024-05-13 16:33:04
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LearningLesson {
    /**
     * 主键
     */
    @AutoId
    private Long id;

    /**
     * 学员id
     */
    private Long userId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程状态，0-未学习，1-学习中，2-已学完，3-已失效
     */
    private LessonStatus status;

    /**
     * 每周学习频率，每周3天，每天2节，则频率为6
     */
    private Integer weekFreq;

    /**
     * 学习计划状态，0-没有计划，1-计划进行中
     */
    private PlanStatus planStatus;

    /**
     * 已学习小节数量
     */
    private Integer learnedSections;

    /**
     * 最近一次学习的小节id
     */
    private Long latestSectionId;

    /**
     * 最近一次学习的时间
     */
    private LocalDateTime latestLearnTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
