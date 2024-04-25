package com.learnhub.promotion.mapper;

import com.learnhub.promotion.domain.po.Coupon;
import com.learnhub.promotion.domain.query.CouponQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 分页查询优惠券
     *
     * @param query 查询参数
     * @return 优惠券列表
     */
    List<Coupon> queryCouponByPage(CouponQuery query);

    /**
     * 根据id查询优惠券
     *
     * @param id 优惠券id
     * @return 优惠券
     */
    Coupon queryCouponById(Long id);

    /**
     * 更新优惠券
     *
     * @param copyBean 优惠券
     */
    void updateCouponById(Coupon copyBean);
}
