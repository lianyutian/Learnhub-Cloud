package com.learnhub.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learnhub.course.domain.po.CourseTeacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CourseTeacherMapper
 *
 * @author lm
 * @since 2024-05-14 10:59:03
 * @version 1.0
 */
@Mapper
public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

}
