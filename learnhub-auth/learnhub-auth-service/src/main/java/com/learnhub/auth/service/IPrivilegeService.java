package com.learnhub.auth.service;

import com.learnhub.auth.common.domain.dto.PrivilegeRoleDTO;
import com.learnhub.auth.domain.po.Privilege;
import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.common.domain.query.PageQuery;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 13:54
 */
public interface IPrivilegeService {
    /**
     * 分页查询权限列表
     *
     * @param pageQuery 分页参数
     * @return 权限列表
     */
    PageDTO<Privilege> queryPrivilegesByPage(PageQuery pageQuery);

    /**
     * 查询权限角色列表
     *
     * @return 权限角色列表
     */
    List<PrivilegeRoleDTO> queryPrivilegeRoleList();
}
