package com.learnhub.api.client.remark.fallback;

import com.learnhub.api.client.remark.RemarkClient;
import com.learnhub.common.utils.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Set;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 15:27
 */
@Slf4j
public class RemarkClientFallback implements FallbackFactory<RemarkClient> {
    @Override
    public RemarkClient create(Throwable cause) {
        log.error("查询remark-service服务异常", cause);
        return new RemarkClient() {
            @Override
            public Set<Long> isBizLiked(Iterable<Long> bizIds) {
                return CollUtils.emptySet();
            }
        };
    }
}
