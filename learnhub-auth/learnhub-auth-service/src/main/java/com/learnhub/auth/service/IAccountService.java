package com.learnhub.auth.service;

import com.learnhub.api.dto.user.LoginFormDTO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 14:14
 */
public interface IAccountService {

    /**
     * 根据账号名密码登录
     *
     * @param loginFormDTO 登录表单
     * @param isAdmin 是否管理界面
     * @return 登录结果
     */
    String loginByPw(LoginFormDTO loginFormDTO, boolean isAdmin);
}
