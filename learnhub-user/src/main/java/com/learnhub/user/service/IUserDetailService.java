package com.learnhub.user.service;

import com.github.pagehelper.Page;
import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.enums.UserType;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.domain.query.UserPageQuery;

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

    /**
     * 根据用户名查询用户详情
     *
     * @param username 用户名
     * @return UserDTO
     */
    UserDTO queryUserByName(String username);

    /**
     * 根据用户名查询用户详情
     *
     * @param pageQuery 分页查询参数
     * @param userType  用户类型
     * @return UserDTO
     */
    Page<UserDetail> queryUserDetailByPage(UserPageQuery pageQuery, UserType userType);
}
