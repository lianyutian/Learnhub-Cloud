package com.learnhub.auth.mapper;

import com.learnhub.auth.domain.po.Privilege;
import com.learnhub.common.domain.query.PageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 14:36
 */
@Mapper
public interface PrivilegeMapper {

    /**
     * 分页查询权限列表
     *
     * @param pageQuery 分页参数
     * @return 权限列表
     */
    List<Privilege> queryPrivilegesByPage(PageQuery pageQuery);

    /**
     * 查询所有权限列表
     *
     * @return 权限列表
     */
    List<Privilege> queryPrivilegeList();
}
