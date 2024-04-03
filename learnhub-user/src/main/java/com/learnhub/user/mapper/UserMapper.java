package com.learnhub.user.mapper;

import com.learnhub.user.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 17:35
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名或者手机号查询用户
     *
     * @param username 用户名
     * @param cellPhone 手机号
     * @return 用户信息
     */
    User queryUserByNameAndPhone(String username, String cellPhone);

    /**
     * 保存用户
     *
     * @param user 用户
     */
    void saveUser(User user);
}
