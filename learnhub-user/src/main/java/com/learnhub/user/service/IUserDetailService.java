package com.learnhub.user.service;

import com.learnhub.user.domain.po.UserDetail;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/27 15:13
 */
public interface IUserDetailService {

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

    /**
     * 根据用户id查询用户详情
     *
     * @param ids 用户id
     * @return 用户详情
     */
    List<UserDetail> queryUserDetailByIds(List<Long> ids);
}
