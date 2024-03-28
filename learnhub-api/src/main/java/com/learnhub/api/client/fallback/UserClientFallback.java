package com.learnhub.api.client.fallback;

import com.learnhub.api.client.UserClient;
import com.learnhub.api.dto.user.LoginFormDTO;
import com.learnhub.common.domain.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/28 14:20
 */
@Slf4j
public class UserClientFallback implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        log.error("查询用户服务出现异常", cause);
        return new UserClient() {
            @Override
            public LoginUserDTO queryUserDetail(LoginFormDTO loginDTO, boolean isStaff) {
                return null;
            }
        };
    }
}
