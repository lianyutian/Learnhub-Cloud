package com.learnhub.user.mapper;

import com.learnhub.user.domain.po.UserDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:05]
 */
@Mapper
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

    /**
     * 保存用户详情
     *
     * @param detail 用户详情
     */
    void saveUserDetail(UserDetail detail);
}
