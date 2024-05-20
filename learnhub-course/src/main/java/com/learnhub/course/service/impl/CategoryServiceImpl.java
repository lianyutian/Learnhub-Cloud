package com.learnhub.course.service.impl;

import com.learnhub.common.enums.CommonStatus;
import com.learnhub.common.exceptions.BizIllegalException;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.TreeDataUtils;
import com.learnhub.course.constants.CourseErrorInfo;
import com.learnhub.course.domain.po.Category;
import com.learnhub.course.domain.vo.SimpleCategoryVO;
import com.learnhub.course.mapper.CategoryMapper;
import com.learnhub.course.service.ICategoryService;
import com.learnhub.course.service.ICourseService;
import com.learnhub.course.utils.CategoryDataWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 课程服务实现类
 *
 * @author lm
 * @since 2024-05-11 17:42:38
 * @version 1.0
 */
@Service
@Slf4j
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICourseService courseService;
    private final CategoryMapper categoryMapper;

    @Override
    public List<SimpleCategoryVO> queryAllCategorys(Boolean admin) {
        // 1.查询以上架课程的课程分类id列表
        List<Long> categoryIdList = admin ? null : courseService.queryUpCategoryIds();
        // 1.1.判空
        if (!admin && CollUtils.isEmpty(categoryIdList)) {
            return new ArrayList<>();
        }
        // 2.升序查询所有未禁用的课程分类
        List<Category> categories = categoryMapper.queryAllCategorys();
        // 3.将课程分类转换成树状结构
        List<SimpleCategoryVO> simpleCategoryVOS = TreeDataUtils.parseToTree(categories,
                SimpleCategoryVO.class, new CategoryDataWrapper());
        // 4.过滤掉没有三级子课程分类的课程分类
        filter(simpleCategoryVOS);
        return simpleCategoryVOS;
    }

    @Override
    public List<Long> checkCategory(Long thirdCateId) {
        //1.查询三级课程分类
        Category thirdCategory = categoryMapper.queryCategoryById(thirdCateId);
        //1.1判断三级课程分类状态
        if (thirdCategory.getStatus() != CommonStatus.ENABLE.getValue()) {
            throw new BizIllegalException(CourseErrorInfo.Msg.COURSE_CATEGORY_NOT_FOUND);
        }
        //2.查询二级课程分类
        Category secondCategory = categoryMapper.queryCategoryById(thirdCategory.getParentId());
        //2.1判断三级课程分类状态
        if (secondCategory.getStatus() != CommonStatus.ENABLE.getValue()) {
            throw new BizIllegalException(CourseErrorInfo.Msg.COURSE_CATEGORY_NOT_FOUND);
        }
        //3.返回数据
        return Arrays.asList(secondCategory.getParentId(), secondCategory.getId(), thirdCateId);
    }

    /**
     * 过滤掉没有三级课程分类的
     *
     * @param simpleCategoryVOS 所有课程分类数据
     */
    private void filter(List<SimpleCategoryVO> simpleCategoryVOS) {
        // 1.判空
        if (CollUtils.isEmpty(simpleCategoryVOS)) {
            return;
        }
        // 2.遍历分类列表
        for (int count = simpleCategoryVOS.size() - 1; count >= 0; count--) {
            SimpleCategoryVO simpleCategoryVO = simpleCategoryVOS.get(count);
            if (simpleCategoryVO.getLevel() == 3) {
                continue;
            }
            filter(simpleCategoryVO.getChildren());
            if (CollUtils.isEmpty(simpleCategoryVO.getChildren())) {
                simpleCategoryVOS.remove(count);
            }
        }
    }
}
