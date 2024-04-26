package com.learnhub.auth.service;

import com.learnhub.api.dto.auth.RoleDTO;
import com.learnhub.auth.domain.po.Role;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 17:28
 */
public interface IRoleService {
    /**
     * 根据id查询角色
     *
     * @param id id
     * @return 角色
     */
    RoleDTO queryRoleById(Long id);
}
