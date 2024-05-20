package com.learnhub.course.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 课程内容草稿
 *
 * @author lm
 * @since 2024-05-16 13:45:02
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("course_content_draft")
public class CourseContentDraft {
    /**
     * 课程内容id
     */
    private Long id;

    /**
     * 课程介绍
     */
    private String courseIntroduce;

    /**
     * 适用人群
     */
    private String usePeople;

    /**
     * 课程详情
     */
    private String courseDetail;

    /**
     * 部门id
     */
    private Long depId;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long creater;

    /**
     * 更新人
     */
    private Long updater;
}
