package com.learnhub.promotion.service.impl;

import com.learnhub.promotion.domain.po.CouponScope;
import com.learnhub.promotion.mapper.CouponScopeMapper;
import com.learnhub.promotion.service.ICouponScopeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:52
 */
@Slf4j
@Service
@AllArgsConstructor
public class CouponScopeServiceImpl implements ICouponScopeService {

    private final CouponScopeMapper couponScopeMapper;

    @Override
    public void saveCouponScopes(List<CouponScope> list) {
        couponScopeMapper.saveCouponScopes(list);
    }
}
