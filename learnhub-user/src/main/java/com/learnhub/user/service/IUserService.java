package com.learnhub.user.service;

import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/27 15:13
 */
public interface IUserService {

    /**
     * 根据登录表单查询登录用户信息
     *
     * @param loginDTO 登录表单
     * @param isStaff 是否员工登录
     * @return 登录用户信息
     */
    LoginUserDTO queryLoginUser(LoginFormDTO loginDTO, boolean isStaff);
}
