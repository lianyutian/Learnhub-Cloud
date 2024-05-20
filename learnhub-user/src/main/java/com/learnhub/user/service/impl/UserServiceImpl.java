package com.learnhub.user.service.impl;

import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.common.enums.UserType;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.exceptions.ForbiddenException;
import com.learnhub.common.utils.AssertUtils;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.StringUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.user.domain.po.User;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.domain.vo.UserDetailVO;
import com.learnhub.user.enums.UserStatus;
import com.learnhub.user.mapper.UserDetailMapper;
import com.learnhub.user.mapper.UserMapper;
import com.learnhub.user.service.IUserDetailService;
import com.learnhub.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.learnhub.user.constants.UserConstants.DEFAULT_PASSWORD;
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
    private final UserDetailMapper userDetailMapper;


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

    @Override
    @Transactional(rollbackFor = {DbException.class, Exception.class})
    public Long saveUser(UserDTO userDTO) {
        UserType type = UserType.of(userDTO.getType());
        // 1.保存用户基本信息
        User user = new User();
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setCellPhone(userDTO.getCellPhone());
        user.setUsername(userDTO.getUsername());
        user.setType(type);
        user.setStatus(UserStatus.NORMAL);
        userMapper.saveUser(user);
        // 2.新增详情
        UserDetail detail = BeanUtils.toBean(userDTO, UserDetail.class);
        detail.setId(user.getId());
        detail.setType(type);
        if (type == UserType.TEACHER) {
            detail.setRoleId(TEACHER_ROLE_ID);
        } else {
            if (userDTO.getRoleId() == null) {
                throw new BadRequestException("员工角色信息不能为空");
            }
        }
        userDetailMapper.saveUserDetail(detail);
        return user.getId();
    }

    @Override
    public UserDetailVO myInfo() {
        // 1.获取登录用户id
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return null;
        }
        // 2.查询用户
        UserDetail userDetail = userDetailService.queryUserDetailById(userId);
        AssertUtils.isNotNull(userDetail, USER_ID_NOT_EXISTS);
        // 3.封装vo
        UserType type = userDetail.getType();
        // 3.1.基本信息
        UserDetailVO vo = BeanUtils.toBean(userDetail, UserDetailVO.class);
//        // 3.2.详情信息
//        switch (type) {
//            case STAFF:
//                RoleDTO roleDTO = authClient.queryRoleById(userDetail.getRoleId());
//                vo.setRoleName(roleDTO == null ? "" : roleDTO.getName());
//                break;
//            case STUDENT:
//                vo.setRoleName(STUDENT_ROLE_NAME);
//                break;
//            case TEACHER:
//                vo.setRoleName(TEACHER_ROLE_NAME);
//                break;
//            default:
//                break;
//        }
        return vo;
    }

    private User loginByPw(LoginFormDTO loginDTO) {
        // 1.数据校验
        String username = loginDTO.getUsername();
        String cellPhone = loginDTO.getCellPhone();
        if (StringUtils.isBlank(username) && StringUtils.isBlank(cellPhone)) {
            throw new BadRequestException(INVALID_UN);
        }
        // 2.根据收机号查询
        User user = userMapper.queryUserByPhone(cellPhone);
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
            case STUDENT -> roleId = STUDENT_ROLE_ID;
            case TEACHER -> roleId = TEACHER_ROLE_ID;
            case STAFF -> roleId = userDetailService.queryUserRoleIdById(user.getId());
            default -> {
            }
        }
        return roleId;
    }

}
