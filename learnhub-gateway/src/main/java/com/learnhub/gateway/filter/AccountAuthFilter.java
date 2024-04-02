package com.learnhub.gateway.filter;

import com.learnhub.authsdk.gateway.config.AuthProperties;
import com.learnhub.authsdk.gateway.utils.AuthUtil;
import com.learnhub.common.domain.Result;
import com.learnhub.common.domain.dto.LoginUserDTO;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.exceptions.UnauthorizedException;
import com.learnhub.common.utils.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.learnhub.auth.common.constants.JwtConstants.AUTHORIZATION_HEADER;
import static com.learnhub.auth.common.constants.JwtConstants.USER_HEADER;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/29 17:49
 */
@Component
public class AccountAuthFilter implements GlobalFilter, Ordered {
    private final AuthUtil authUtil;
    private final AuthProperties authProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public AccountAuthFilter(AuthUtil authUtil, AuthProperties authProperties) {
        this.authUtil = authUtil;
        this.authProperties = authProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethod().name();
        String path = request.getPath().toString();
        String antPath = method + ":" + path;

        // 2.判断是否是无需登录的路径
        if (isExcludePath(antPath)) {
            // 直接放行
            return chain.filter(exchange);
        }

        // 3.尝试获取用户信息
        List<String> authHeaders = exchange.getRequest().getHeaders().get(AUTHORIZATION_HEADER);
        String token = authHeaders == null ? "" : authHeaders.get(0);

        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException("未登录");
        }

        Result<LoginUserDTO> result = authUtil.parseToken(token);

        // 4.如果用户是登录状态，尝试更新请求头，传递用户信息
        if (result.success()) {
            exchange.mutate()
                    .request(builder -> builder.header(USER_HEADER, result.getData().getUserId().toString()))
                    .build();
        }

        // 5.校验权限
        authUtil.checkAuth(antPath, result);

        // 6.放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1000;
    }

    private boolean isExcludePath(String antPath) {
        for (String pathPattern : authProperties.getExcludePath()) {
            if (antPathMatcher.match(pathPattern, antPath)) {
                return true;
            }
        }
        return false;
    }
}
