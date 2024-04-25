package com.learnhub.promotion.service;

import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.promotion.domain.dto.CouponFormDTO;
import com.learnhub.promotion.domain.dto.CouponIssueFormDTO;
import com.learnhub.promotion.domain.query.CouponQuery;
import com.learnhub.promotion.domain.vo.CouponDetailVO;
import com.learnhub.promotion.domain.vo.CouponPageVO;

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

    /**
     * 分页查询优惠券
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageDTO<CouponPageVO> queryCouponByPage(CouponQuery query);

    /**
     * 发放优惠券
     *
     * @param dto 发放表单
     */
    void beginIssue(CouponIssueFormDTO dto);

    /**
     * 根据id查询优惠券
     *
     * @param id 优惠券id
     * @return 优惠券详情
     */
    CouponDetailVO queryCouponById(Long id);

    /**
     * 更新优惠券
     *
     * @param dto 优惠券表单
     */
    void updateCouponById(CouponFormDTO dto);
}
