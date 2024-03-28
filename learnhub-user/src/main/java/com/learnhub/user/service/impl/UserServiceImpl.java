package com.learnhub.user.service.impl;

import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.common.enums.UserType;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.exceptions.ForbiddenException;
import com.learnhub.common.utils.AssertUtils;
import com.learnhub.common.utils.StringUtils;
import com.learnhub.user.domain.po.User;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.enums.UserStatus;
import com.learnhub.user.mapper.UserMapper;
import com.learnhub.user.service.IUserDetailService;
import com.learnhub.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.learnhub.user.constants.UserConstants.STUDENT_ROLE_ID;
import static com.learnhub.user.constants.UserConstants.TEACHER_ROLE_ID;
import static com.learnhub.user.constants.UserErrorInfo.Msg.*;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/27 15:13
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final IUserDetailService userDetailService;

    @Override
    public LoginUserDTO queryLoginUser(LoginFormDTO loginDTO, boolean isStaff) {
        // 1.判断登录方式
        Integer type = loginDTO.getType();
        User user = null;
        // 2.用户名和密码登录
        if (type == 1) {
            user = loginByPw(loginDTO);
        }
        // 4.错误的登录方式
        if (user == null) {
            throw new BadRequestException(ILLEGAL_LOGIN_TYPE);
        }
        // 5.判断用户类型与登录方式是否匹配
        if (isStaff ^ user.getType() != UserType.STUDENT) {
            throw new BadRequestException(isStaff ? "非管理端用户" : "非学生端用户");
        }
        // 6.封装返回
        LoginUserDTO userDTO = new LoginUserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setRoleId(handleRoleId(user));
        return userDTO;
    }

    private User loginByPw(LoginFormDTO loginDTO) {
        // 1.数据校验
        String username = loginDTO.getUsername();
        String cellPhone = loginDTO.getCellPhone();
        if (StringUtils.isBlank(username) && StringUtils.isBlank(cellPhone)) {
            throw new BadRequestException(INVALID_UN);
        }
        // 2.根据用户名和手机号查询
        User user = userMapper.queryUserByNameAndPhone(username, cellPhone);
        AssertUtils.isNotNull(user, INVALID_UN_OR_PW);
        // 3.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException(USER_FROZEN);
        }
        // 4.校验密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException(INVALID_UN_OR_PW);
        }

        return user;
    }

    private Long handleRoleId(User user) {
        Long roleId = 0L;
        switch (user.getType()) {
            case STUDENT:
                roleId = STUDENT_ROLE_ID;
                break;
            case TEACHER:
                roleId = TEACHER_ROLE_ID;
                break;
            case STAFF:
                roleId = userDetailService.queryUserRoleIdById(user.getId());
                break;
            default:
                break;
        }
        return roleId;
    }

}
