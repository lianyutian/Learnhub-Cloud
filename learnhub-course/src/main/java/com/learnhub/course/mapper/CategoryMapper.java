package com.learnhub.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learnhub.course.domain.po.Category;
import com.learnhub.course.domain.po.CategoryHierarchy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CategoryMapper
 *
 * @author lm
 * @since 2024-05-11 22:17:43
 * @version 1.0
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询所有分类
     *
     * @return 分类列表
     */
    List<Category> queryAllCategorys();

    /**
     * 查询三级分类
     *
     * @param id 分类id
     * @return 分类
     */
    Category queryCategoryById(Long id);

    /**
     * 查询所有已上架课程的分类
     *
     * @return 分类列表
     */
    List<CategoryHierarchy> queryUpCategoryIds();
}
