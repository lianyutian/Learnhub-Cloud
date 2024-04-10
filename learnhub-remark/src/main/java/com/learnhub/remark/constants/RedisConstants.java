package com.learnhub.remark.constants;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/10 17:33
 */
public interface RedisConstants {
    /**
     * 业务对应点赞用户
     */
    String LIKES_BIZ_KEY_PREFIX = "likes:set:biz:";

    /**
     * 业务对应点赞次数
     */
    String LIKES_TIMES_KEY_PREFIX = "likes:times:type:";
}
