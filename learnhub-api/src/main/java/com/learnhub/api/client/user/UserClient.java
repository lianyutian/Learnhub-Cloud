package com.learnhub.api.client.user;

import com.learnhub.api.client.user.fallback.UserClientFallback;
import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
