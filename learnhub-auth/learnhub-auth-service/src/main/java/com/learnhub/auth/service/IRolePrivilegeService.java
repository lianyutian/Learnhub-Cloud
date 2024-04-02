package com.learnhub.auth.service;

import com.learnhub.auth.domain.po.RolePrivilege;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 16:18
 */
public interface IRolePrivilegeService {

    /**
     * 查询角色权限关联列表
     *
     * @return 角色权限关联列表
     */
    List<RolePrivilege> queyRolePrivilegeList();
}
