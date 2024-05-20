package com.learnhub.course.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 课程老师关系实体
 *
 * @author lm
 * @since 2024-05-14 10:58:12
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("course_teacher")
public class CourseTeacher {
    /**
     * 课程老师关系id
     */
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 用户端是否展示
     */
    private Integer isShow;

    /**
     * 序号
     */
    private Integer cIndex;

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

    /**
     * 逻辑删除
     */
    private Integer deleted;

}
