package com.learnhub.user.mapper;

import com.learnhub.user.domain.po.UserDetail;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:05]
 */
public interface UserDetailMapper {

    /**
     * 根据用户id查询用户详情
     *
     * @param id 用户id
     * @return 用户详情
     */
    UserDetail queryUserDetailById(Long id);

    /**
     * 根据用户id查询用户角色id
     *
     * @param id 用户id
     * @return 角色id
     */
    Long queryUserRoleIdById(Long id);
}
