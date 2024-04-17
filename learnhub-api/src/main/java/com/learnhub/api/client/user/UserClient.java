package com.learnhub.api.client.user;

import com.learnhub.api.client.user.fallback.UserClientFallback;
import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 14:18
 */
@FeignClient(value = "user-service", fallbackFactory = UserClientFallback.class)
public interface UserClient {
    /**
     * 登录接口
     *
     * @param loginDTO 登录信息
     * @param isStaff 是否是员工
     * @return 用户详情
     */
    @PostMapping("/user/queryLoginUser/{isStaff}")
    LoginUserDTO queryLoginUser(@RequestBody LoginFormDTO loginDTO, @PathVariable("isStaff") boolean isStaff);

    /**
     * 根据id批量查询用户信息
     *
     * @param ids 用户id集合
     * @return 用户集合
     */
    @Hidden
    @GetMapping("/user/list")
    List<UserDTO> queryUserDetailByIds(@RequestParam("ids") Iterable<Long> ids);
}
