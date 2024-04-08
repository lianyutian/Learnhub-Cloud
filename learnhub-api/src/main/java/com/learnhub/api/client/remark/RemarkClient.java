package com.learnhub.api.client.remark;

import com.learnhub.api.client.remark.fallback.RemarkClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 15:26
 */
@FeignClient(value = "remark-service", fallbackFactory = RemarkClientFallback.class)
public interface RemarkClient {
    /**
     * 查询当前用户是否点赞了指定的业务
     *
     * @param bizIds 业务id
     * @return 业务点赞状态
     */
    @GetMapping("/likes/list")
    Set<Long> isBizLiked(@RequestParam("bizIds") Iterable<Long> bizIds);
}
