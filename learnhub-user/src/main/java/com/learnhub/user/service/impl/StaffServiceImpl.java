package com.learnhub.user.service.impl;

import com.github.pagehelper.Page;
import com.learnhub.api.cache.RoleCache;
import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.common.enums.UserType;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.user.domain.po.UserDetail;
import com.learnhub.user.domain.query.UserPageQuery;
import com.learnhub.user.domain.vo.StaffVO;
import com.learnhub.user.service.IStaffService;
import com.learnhub.user.service.IUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Service;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 16:23
 */
@Service
@AllArgsConstructor
public class StaffServiceImpl implements IStaffService {

    private final IUserDetailService userDetailService;
    private final RoleCache roleCache;

    @Override
    public PageDTO<StaffVO> queryStaffByPage(UserPageQuery pageQuery) {
        // 1.搜索
        Page<UserDetail> p = userDetailService.queryUserDetailByPage(pageQuery, UserType.STAFF);
        // 2.处理vo
        return PageDTO.of(p, u -> {
            StaffVO staffVO = BeanUtils.toBean(u, StaffVO.class);
            staffVO.setRoleName(roleCache.getRoleName(u.getRoleId()));
            return staffVO;
        });
    }
}
