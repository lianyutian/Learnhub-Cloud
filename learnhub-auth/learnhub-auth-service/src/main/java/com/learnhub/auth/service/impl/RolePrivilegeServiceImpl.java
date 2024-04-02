package com.learnhub.auth.service.impl;

import com.learnhub.auth.domain.po.RolePrivilege;
import com.learnhub.auth.mapper.RolePrivilegeMapper;
import com.learnhub.auth.service.IRolePrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 16:20
 */
@Service
@AllArgsConstructor
public class RolePrivilegeServiceImpl implements IRolePrivilegeService {

    private final RolePrivilegeMapper rolePrivilegeMapper;

    @Override
    public List<RolePrivilege> queyRolePrivilegeList() {
        return rolePrivilegeMapper.queryRolePrivilegeList();
    }
}
