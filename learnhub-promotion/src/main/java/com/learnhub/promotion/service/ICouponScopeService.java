package com.learnhub.promotion.service;

import com.learnhub.promotion.domain.po.CouponScope;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:51
 */
public interface ICouponScopeService {
    /**
     * 保存优惠券范围
     *
     * @param list 优惠券范围
     */
    void saveCouponScopes(List<CouponScope> list);
}
