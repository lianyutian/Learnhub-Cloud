package com.learnhub.user.mapper;

import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.enums.UserType;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.domain.query.UserPageQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 根据用户id列表查询用户详情
     *
     * @param ids 用户id列表
     * @return 用户详情列表
     */
    List<UserDetail> queryUserDetailByIds(List<Long> ids);

    /**
     * 根据用户名查询用户详情
     *
     * @param username 用户名
     * @return 用户详情
     */
    UserDTO queryUserByName(String username);

    /**
     * 分页查询用户详情
     *
     * @param pageQuery 分页查询参数
     * @param userType  用户类型
     * @return 用户详情列表
     */
    List<UserDetail> queryUserDetailByPage(UserPageQuery pageQuery, UserType userType);
}
