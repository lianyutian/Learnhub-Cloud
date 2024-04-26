package com.learnhub.api.client.auth;

import com.learnhub.api.dto.auth.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 14:59
 */
@FeignClient("auth-service")
public interface AuthClient {
    /**
     * 根据角色id查询角色
     *
     * @param id 角色id
     * @return 角色
     */
    @GetMapping("/roles/{id}")
    RoleDTO queryRoleById(@PathVariable("id") Long id);
}
