package com.learnhub.auth.mapper;

import com.learnhub.auth.domain.po.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 17:33
 */
@Mapper
public interface RoleMapper {

    /**
     * 根据id查询角色
     *
     * @param id id
     * @return 角色
     */
    Role queryRoleById(Long id);
}
