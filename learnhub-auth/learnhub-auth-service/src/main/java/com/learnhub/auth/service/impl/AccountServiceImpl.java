package com.learnhub.auth.service.impl;

import com.learnhub.api.client.UserClient;
import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.auth.common.constants.JwtConstants;
import com.learnhub.auth.service.IAccountService;
import com.learnhub.auth.service.ILoginRecordService;
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
    private final ILoginRecordService loginRecordService;

    @Override
    public String loginByPw(LoginFormDTO loginFormDTO, boolean isStaff) {
        // 1.查询并校验用户信息
        LoginUserDTO loginUserDTO = userClient.queryLoginUser(loginFormDTO, isStaff);
        if (loginUserDTO == null) {
            throw new BadRequestException("登录信息有误");
        }
        // 2.基于JWT生成登录token
        // 2.1.设置记住我标记
        loginUserDTO.setRememberMe(loginFormDTO.getRememberMe());
        // 2.2.生成token
        String token = generateToken(loginUserDTO);
        // 3.计入登录信息表
        loginRecordService.saveLoginSuccessRecord(loginFormDTO.getCellPhone(), loginUserDTO.getUserId());
        // 4.返回结果
        return token;
    }

    @Override
    public void logout() {
        // 删除jti
        jwtTool.cleanJtiCache();
        // 删除cookie
        WebUtils.cookieBuilder()
                .name(JwtConstants.REFRESH_HEADER)
                .value("")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    @Override
    public String refreshToken(String token) {
        // 1.校验refresh-token,校验JTI
        LoginUserDTO userDTO = jwtTool.parseRefreshToken(token);
        // 2.生成新的access-token、refresh-token
        return generateToken(userDTO);
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
