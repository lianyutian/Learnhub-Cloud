package com.learnhub.auth.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.learnhub.auth.common.domain.dto.PrivilegeRoleDTO;
import com.learnhub.auth.domain.po.Privilege;
import com.learnhub.auth.domain.po.RolePrivilege;
import com.learnhub.auth.mapper.PrivilegeMapper;
import com.learnhub.auth.service.IPrivilegeService;
import com.learnhub.auth.service.IRolePrivilegeService;
import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.common.domain.query.PageQuery;
import com.learnhub.common.utils.CollUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 14:33
 */
@Service
@AllArgsConstructor
public class PrivilegeServiceImpl implements IPrivilegeService {

    private final PrivilegeMapper privilegeMapper;
    private final IRolePrivilegeService rolePrivilegeService;

    @Override
    public PageDTO<Privilege> queryPrivilegesByPage(PageQuery pageQuery) {
        Page<Privilege> page = pageQuery.toMpPage().doSelectPage(
                () -> privilegeMapper.queryPrivilegesByPage(pageQuery)
        );

        if (CollUtils.isEmpty(page.getResult())) {
            return PageDTO.empty(page);
        }

        return PageDTO.of(page);
    }

    @Override
    public List<PrivilegeRoleDTO> queryPrivilegeRoleList() {
        // 1.查询所有权限
        List<Privilege> privileges = privilegeMapper.queryPrivilegeList();
        // 2.查询所有角色
        List<RolePrivilege> rpList = rolePrivilegeService.queyRolePrivilegeList();
        // 3.按照权限将角色分组
        Map<Long, List<RolePrivilege>> rpMap = rpList.stream()
                .collect(Collectors.groupingBy(RolePrivilege::getPrivilegeId));
        // 4.组装权限对应角色
        List<PrivilegeRoleDTO> list = new ArrayList<>(privileges.size());
        for (Privilege privilege : privileges) {
            // 4.1.根据权限查询角色
            Set<Long> roles = rpMap.get(privilege.getId())
                    .stream()
                    .map(RolePrivilege::getRoleId)
                    .collect(Collectors.toSet());
            // 4.2.组装
            PrivilegeRoleDTO prDTO = new PrivilegeRoleDTO();
            prDTO.setId(privilege.getId());
            prDTO.setRoles(roles);
            prDTO.setAntPath(privilege.getMethod() + ":" + privilege.getUri());
            prDTO.setInternal(privilege.getInternal());
            // 4.3.存入map
            list.add(prDTO);
        }
        return list;
    }
}
