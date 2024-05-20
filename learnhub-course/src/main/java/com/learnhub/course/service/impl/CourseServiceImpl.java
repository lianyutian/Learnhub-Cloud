package com.learnhub.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learnhub.api.dto.course.CourseFullInfoDTO;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.course.constants.CourseErrorInfo;
import com.learnhub.course.constants.RedisConstants;
import com.learnhub.course.domain.po.CategoryHierarchy;
import com.learnhub.course.domain.po.Course;
import com.learnhub.course.domain.vo.NameExistVO;
import com.learnhub.course.mapper.CategoryMapper;
import com.learnhub.course.mapper.CourseDraftMapper;
import com.learnhub.course.mapper.CourseMapper;
import com.learnhub.course.service.ICourseCatalogueService;
import com.learnhub.course.service.ICourseDraftService;
import com.learnhub.course.service.ICourseService;
import com.learnhub.course.service.ICourseTeacherService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程服务实现类
 *
 * @author lm
 * @since 2024-05-13 14:02:43
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final CategoryMapper categoryMapper;
    private final ICourseCatalogueService courseCatalogueService;
    private final ICourseTeacherService courseTeacherService;
    private final CourseMapper courseMapper;
    private final CourseDraftMapper courseDraftMapper;

    @Override
    @Cacheable(cacheNames = RedisConstants.Formatter.CATEGORY_ID_LIST_HAVE_COURSE)
    public List<Long> queryUpCategoryIds() {
        // 1.查询条件
        List<CategoryHierarchy> category3s = categoryMapper.queryUpCategoryIds();
        // 1.1.判空
        if(CollUtils.isEmpty(category3s)) {
            return new ArrayList<>();
        }
        // 2.将课程分类id设置到categoryIdList
        List<Long> categoryIdList = new ArrayList<>();
        category3s.forEach(category3->{
            category3.setId(categoryIdList);
        });
        // 2.1.去重，并返回数据
        return categoryIdList.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public CourseFullInfoDTO queryCourseInfoById(Long id, boolean withCatalogue, boolean withTeachers) {
        // 1.查询课程基本信息
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new BadRequestException(CourseErrorInfo.Msg.COURSE_CHECK_NOT_EXISTS);
        }
        // 2.转换vo
        CourseFullInfoDTO courseFullInfoDTO = BeanUtils.toBean(course, CourseFullInfoDTO.class);

        // 3.查询目录信息
        if (withCatalogue) {
            courseFullInfoDTO.setChapters(courseCatalogueService.queryCourseCatalogues(id, true));
        }

        // 4.查询教师信息
        if (withTeachers) {
            courseFullInfoDTO.setTeacherIds(courseTeacherService.queryTeacherIdByCourseId(id));
        }

        return courseFullInfoDTO;
    }

    @Override
    public NameExistVO checkName(String name, Long id) {
        //1.正式课程同名课程数量
        LambdaQueryWrapper<Course> queryWrapper =
                Wrappers.lambdaQuery(Course.class)
                        .eq(Course::getName, name)
                        .last(id != null, " and id !=" + id);
        //2.统计数量
        Long num = courseMapper.selectCount(queryWrapper);
        if (num > 0) {
            return NameExistVO.EXISTED;
        }
        //3.统计草稿课程同名数量
        int sameNameCourseNum = courseDraftMapper.countByNameAndId(name, id);
        return new NameExistVO(sameNameCourseNum > 0);
    }
}
