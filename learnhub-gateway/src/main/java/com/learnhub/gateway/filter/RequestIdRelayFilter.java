package com.learnhub.gateway.filter;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.learnhub.common.constants.Constant.GATEWAY_ORIGIN_NAME;
import static com.learnhub.common.constants.Constant.REQUEST_FROM_HEADER;
import static com.learnhub.common.constants.Constant.REQUEST_ID_HEADER;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/25 16:07
 */

@Slf4j
@Component
public class RequestIdRelayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.生成 RequestId
        String requestId = UUID.randomUUID().toString(true);
        // 2.保存到日志变量池
        MDC.put(REQUEST_ID_HEADER, requestId);
        // 3.更新请求头，添加标示
        String path = exchange.getRequest().getPath().toString();
        exchange = exchange.mutate().request(b -> {
                    // 3.1.添加请求id标示
                    b.header(REQUEST_ID_HEADER, requestId);
                    // 3.2 swagger文档忽略标识
                    if (path.contains("/v3/api-docs")) {
                        return;
                    }
                    // 3.3.添加网关标示
                    if (!path.startsWith("/ps/notify")) {
                        b.header(REQUEST_FROM_HEADER, GATEWAY_ORIGIN_NAME);
                    }
                }
        ).build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
