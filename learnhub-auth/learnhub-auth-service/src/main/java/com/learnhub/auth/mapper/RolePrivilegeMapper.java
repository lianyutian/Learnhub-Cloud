package com.learnhub.auth.mapper;

import com.learnhub.auth.domain.po.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 16:21
 */
@Mapper
public interface RolePrivilegeMapper {
    /**
     * 查询角色权限关联列表
     *
     * @return 角色权限关联列表
     */
    List<RolePrivilege> queryRolePrivilegeList();
}
