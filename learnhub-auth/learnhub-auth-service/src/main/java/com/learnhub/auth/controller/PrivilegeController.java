package com.learnhub.auth.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.learnhub.auth.domain.dto.PrivilegeDTO;
import com.learnhub.auth.domain.po.Privilege;
import com.learnhub.auth.service.IPrivilegeService;
import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.common.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 13:47
 */
@RestController
@RequestMapping("/privileges")
@Tag(name = "权限管理相关接口")
@RequiredArgsConstructor
public class PrivilegeController {

    private final IPrivilegeService privilegesService;

    /**
     * 分页查询所有权限
     *
     * @param pageQuery 分页查询条件
     * @return 分页结果
     */
    @Operation(summary = "分页查询所有权限")
    @GetMapping
    public PageDTO<PrivilegeDTO> listAllPrivileges(PageQuery pageQuery) {
        // 1.分页查询
        PageDTO<Privilege> page = privilegesService.queryPrivilegesByPage(pageQuery);
        // 2.非空判断
        List<Privilege> list = page.getList();
        if (CollectionUtil.isEmpty(list)) {
            // 结果为空，返回空结果 添加总页码数
            return new PageDTO<>(page.getTotal(), page.getPages(), Collections.emptyList());
        }
        // 3.数据转换
        List<PrivilegeDTO> dtoList = list.stream().map(Privilege::toDTO).collect(Collectors.toList());
        // 4.封装返回
        return new PageDTO<>(page.getTotal(), page.getPages(), dtoList);
    }


}
