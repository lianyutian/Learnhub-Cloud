package com.learnhub.promotion.service.impl;

import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.promotion.domain.dto.CouponFormDTO;
import com.learnhub.promotion.domain.po.Coupon;
import com.learnhub.promotion.domain.po.CouponScope;
import com.learnhub.promotion.mapper.CouponMapper;
import com.learnhub.promotion.service.ICouponScopeService;
import com.learnhub.promotion.service.ICouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:25
 */
@Slf4j
@Service
@AllArgsConstructor
public class CouponServiceImpl implements ICouponService {

    private final CouponMapper couponMapper;
    private final ICouponScopeService couponScopeService;

    @Override
    @Transactional(rollbackFor = DbException.class)
    public void saveCoupon(CouponFormDTO dto) {
        // 1.保存优惠券
        // 1.1.转PO
        Coupon coupon = BeanUtils.copyBean(dto, Coupon.class);
        // 1.2.保存
        couponMapper.saveCoupon(coupon);

        if (!dto.getSpecific()) {
            // 没有范围限定
            throw new BadRequestException("没有限定使用范围");
        }
        Long couponId = coupon.getId();
        // 2.保存限定范围
        List<Long> scopes = dto.getScopes();
        if (CollUtils.isEmpty(scopes)) {
            throw new BadRequestException("限定范围不能为空");
        }
        // 2.1.转换PO
        List<CouponScope> list = scopes.stream()
                .map(bizId -> new CouponScope().setBizId(bizId).setCouponId(couponId))
                .collect(Collectors.toList());
        // 2.2.保存
        couponScopeService.saveCouponScopes(list);
    }
}
