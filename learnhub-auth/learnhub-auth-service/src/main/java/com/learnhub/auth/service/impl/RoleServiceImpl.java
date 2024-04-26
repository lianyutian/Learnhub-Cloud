package com.learnhub.auth.service.impl;

import com.learnhub.api.dto.auth.RoleDTO;
import com.learnhub.auth.domain.po.Role;
import com.learnhub.auth.mapper.RoleMapper;
import com.learnhub.auth.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 17:33
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleMapper rolemapper;

    @Override
    public RoleDTO queryRoleById(Long id) {
        Role role = rolemapper.queryRoleById(id);
        if (role == null) {
            return null;
        }
        return role.toDTO();
    }
}
