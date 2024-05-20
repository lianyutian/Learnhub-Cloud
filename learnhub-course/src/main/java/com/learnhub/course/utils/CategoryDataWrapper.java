package com.learnhub.course.utils;

import com.learnhub.common.utils.TreeDataUtils;
import com.learnhub.course.domain.po.Category;
import com.learnhub.course.domain.vo.SimpleCategoryVO;

import java.util.List;

/**
 * CategoryDataWrapper
 *
 * @author lm
 * @since 2024-05-13 14:58:01
 * @version 1.0
 */
public class CategoryDataWrapper implements TreeDataUtils.DataProcessor<SimpleCategoryVO, Category> {
    @Override
    public Object getParentKey(Category category) {
        return category.getParentId();
    }

    @Override
    public Object getKey(Category category) {
        return category.getId();
    }

    @Override
    public Object getRootKey() {
        return 0L;
    }

    @Override
    public List<SimpleCategoryVO> getChild(SimpleCategoryVO simpleCategoryVO) {
        return simpleCategoryVO.getChildren();
    }

    @Override
    public void setChild(SimpleCategoryVO parent, List<SimpleCategoryVO> child) {
        parent.setChildren(child);
    }
}