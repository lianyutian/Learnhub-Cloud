package com.learnhub.user.controller;

import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.user.domain.query.UserPageQuery;
import com.learnhub.user.domain.vo.StaffVO;
import com.learnhub.user.service.IStaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 16:15
 */
@RestController
@RequestMapping("/staffs")
@AllArgsConstructor
@Tag(name = "后台用户管理接口")
public class StaffController {

    private final IStaffService staffService;

    @Operation(summary = "分页查询员工信息")
    @GetMapping("page")
    public PageDTO<StaffVO> queryStaffByPage(UserPageQuery pageQuery){
        return staffService.queryStaffByPage(pageQuery);
    }
}
