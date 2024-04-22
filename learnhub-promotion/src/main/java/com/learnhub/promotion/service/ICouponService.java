package com.learnhub.promotion.service;

import com.learnhub.promotion.domain.dto.CouponFormDTO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:24
 */
public interface ICouponService {

    /**
     * 新增优惠券
     *
     * @param dto 优惠券表单
     */
    void saveCoupon(CouponFormDTO dto);
}
