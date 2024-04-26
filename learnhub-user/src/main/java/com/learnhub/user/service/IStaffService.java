package com.learnhub.user.service;

import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.user.domain.query.UserPageQuery;
import com.learnhub.user.domain.vo.StaffVO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 16:22
 */
public interface IStaffService {

    /**
     * 根据分页查询员工信息
     *
     * @param pageQuery 分页参数
     * @return 分页员工信息
     */
    PageDTO<StaffVO> queryStaffByPage(UserPageQuery pageQuery);
}
