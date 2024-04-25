package com.learnhub.user.service.impl;

import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.mapper.UserDetailMapper;
import com.learnhub.user.service.IUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:04]
 */
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements IUserDetailService {
    private final UserDetailMapper userDetailMapper;

    @Override
    public UserDetail queryUserDetailById(Long id) {
        return userDetailMapper.queryUserDetailById(id);
    }

    @Override
    public Long queryUserRoleIdById(Long id) {
        return userDetailMapper.queryUserRoleIdById(id);
    }

    @Override
    public List<UserDetail> queryUserDetailByIds(List<Long> ids) {
        return userDetailMapper.queryUserDetailByIds(ids);
    }

    @Override
    public UserDTO queryUserByName(String username) {
        return userDetailMapper.queryUserByName(username);
    }
}
