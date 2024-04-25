package com.learnhub.promotion.service;

import com.learnhub.promotion.domain.po.Coupon;

/**
 * 兑换码生成服务
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/23 16:08
 */
public interface IExchangeCodeService {
    /**
     * 异步生成兑换码
     *
     * @param coupon 优惠券
     */
    void asyncGenerateCode(Coupon coupon);
}
