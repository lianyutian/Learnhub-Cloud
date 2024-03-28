package com.learnhub.auth.service.impl;

import com.learnhub.api.client.UserClient;
import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.auth.service.IAccountService;
import com.learnhub.common.domain.dto.LoginUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 14:16
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final UserClient userClient;

    @Override
    public String loginByPw(LoginFormDTO loginFormDTO, boolean isStaff) {
        // 1.查询并校验用户信息
        LoginUserDTO detail = userClient.queryUserDetail(loginFormDTO, isStaff);
    }
}
