package com.learnhub.auth.service.impl;

import com.learnhub.api.client.UserClient;
import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.auth.common.constants.JwtConstants;
import com.learnhub.auth.service.IAccountService;
import com.learnhub.auth.utils.JwtTool;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.utils.BooleanUtils;
import com.learnhub.common.utils.WebUtils;
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
    private final JwtTool jwtTool;

    @Override
    public String loginByPw(LoginFormDTO loginFormDTO, boolean isStaff) {
        // 1.查询并校验用户信息
        LoginUserDTO loginUserDTO = userClient.queryUserDetail(loginFormDTO, isStaff);
        if (loginUserDTO == null) {
            throw new BadRequestException("登录信息有误");
        }
        // 2.基于JWT生成登录token
        // 2.1.设置记住我标记
        loginUserDTO.setRememberMe(loginFormDTO.getRememberMe());
        // 2.2.生成token
        String token = generateToken(loginUserDTO);
        // 3.计入登录信息表
        //loginRecordService.loginSuccess(loginFormDTO.getCellPhone(), loginUserDTO.getUserId());
        // 4.返回结果
        return token;
    }

    private String generateToken(LoginUserDTO detail) {
        // 2.2.生成access-token
        String token = jwtTool.createToken(detail);
        // 2.3.生成refresh-token，将refresh-token的JTI 保存到Redis
        String refreshToken = jwtTool.createRefreshToken(detail);
        // 2.4.将refresh-token写入用户cookie，并设置HttpOnly为true
        int maxAge = BooleanUtils.isTrue(detail.getRememberMe()) ?
                (int) JwtConstants.JWT_REMEMBER_ME_TTL.toSeconds() : -1;
        WebUtils.cookieBuilder()
                .name(detail.getRoleId() == 2 ? JwtConstants.REFRESH_HEADER : JwtConstants.ADMIN_REFRESH_HEADER)
                .value(refreshToken)
                .maxAge(maxAge)
                .httpOnly(true)
                .build();
        return token;
    }
}