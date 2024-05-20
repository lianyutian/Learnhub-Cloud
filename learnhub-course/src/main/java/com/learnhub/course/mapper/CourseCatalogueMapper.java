package com.learnhub.course.mapper;

import com.learnhub.course.domain.po.CourseCatalogue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CourseCatalogueMapper
 *
 * @author lm
 * @since 2024-05-14 10:39:21
 * @version 1.0
 */
@Mapper
public interface CourseCatalogueMapper {
    /**
     * 查询课程目录
     *
     * @param courseId      课程id
     * @param withPractice  是否包含练习
     * @return 课程目录
     */
    List<CourseCatalogue> queryCatalogueList(Long courseId, boolean withPractice);
}
