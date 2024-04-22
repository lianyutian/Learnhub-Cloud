package com.learnhub.promotion.controller;

import com.learnhub.promotion.domain.dto.CouponFormDTO;
import com.learnhub.promotion.service.ICouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:15
 */
@RestController
@RequestMapping("coupons")
@RequiredArgsConstructor
@Tag(name = "优惠券相关接口")
public class CouponController {
    private final ICouponService couponService;

    @Operation(summary = "新增优惠券接口")
    @PostMapping("saveCoupon")
    public void saveCoupon(@RequestBody @Valid CouponFormDTO dto) {
        couponService.saveCoupon(dto);
    }

}
