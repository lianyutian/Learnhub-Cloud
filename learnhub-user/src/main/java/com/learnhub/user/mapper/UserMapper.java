package com.learnhub.user.mapper;

import com.learnhub.user.domain.po.User;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 17:35
 */
public interface UserMapper {
    /**
     * 根据用户名或者手机号查询用户
     *
     * @param username 用户名
     * @param cellPhone 手机号
     * @return 用户信息
     */
    User queryUserByNameAndPhone(String username, String cellPhone);
}
