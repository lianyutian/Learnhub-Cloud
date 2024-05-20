package com.learnhub.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.course.domain.po.CourseTeacher;
import com.learnhub.course.mapper.CourseCatalogueMapper;
import com.learnhub.course.mapper.CourseTeacherMapper;
import com.learnhub.course.service.ICourseTeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程老师关系服务实现类
 *
 * @author lm
 * @since 2024-05-14 10:56:50
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CourseTeacherServiceImpl implements ICourseTeacherService {

    private final CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<Long> queryTeacherIdByCourseId(Long courseId) {
        //1.查询条件
        LambdaQueryWrapper<CourseTeacher> queryWrapper =
                Wrappers.lambdaQuery(CourseTeacher.class)
                        .eq(CourseTeacher::getCourseId, courseId)
                        .orderByAsc(CourseTeacher::getCIndex);
        //2.查询数据
        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(queryWrapper);
        //3.组装数据
        return CollUtils.isEmpty(courseTeachers) ?
                new ArrayList<>() : courseTeachers
                .stream()
                .map(CourseTeacher::getTeacherId)
                .collect(Collectors.toList());
    }
}
