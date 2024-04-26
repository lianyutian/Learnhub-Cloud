package com.learnhub.auth.controller;

import com.learnhub.api.dto.auth.RoleDTO;
import com.learnhub.auth.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 15:08
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @Operation(summary = "根据id查询角色")
    @GetMapping("/{id}")
    public RoleDTO queryRoleById(@PathVariable("id") Long id){
        return roleService.queryRoleById(id);
    }
}
