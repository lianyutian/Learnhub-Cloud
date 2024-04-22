package com.learnhub.promotion.mapper;

import com.learnhub.promotion.domain.po.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:35
 */
@Mapper
public interface CouponMapper {
    /**
     * 保存优惠券
     *
     * @param coupon 优惠券
     */
    void saveCoupon(Coupon coupon);
}
