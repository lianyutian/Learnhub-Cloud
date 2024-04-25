package com.learnhub.promotion.controller;

import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.promotion.domain.dto.CouponFormDTO;
import com.learnhub.promotion.domain.dto.CouponIssueFormDTO;
import com.learnhub.promotion.domain.query.CouponQuery;
import com.learnhub.promotion.domain.vo.CouponDetailVO;
import com.learnhub.promotion.domain.vo.CouponPageVO;
import com.learnhub.promotion.service.ICouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Operation(summary = "分页查询优惠券接口")
    @GetMapping("/page")
    public PageDTO<CouponPageVO> queryCouponByPage(CouponQuery query) {
        return couponService.queryCouponByPage(query);
    }

    @Operation(summary = "发放优惠券接口")
    @PutMapping("/{id}/issue")
    public void beginIssue(@RequestBody @Valid CouponIssueFormDTO dto) {
        couponService.beginIssue(dto);
    }

    @Operation(summary = "根据id查询优惠券接口")
    @GetMapping("/{id}")
    @Parameter(description = "优惠券id", name = "id")
    public CouponDetailVO queryCouponById(@PathVariable("id") Long id) {
        return couponService.queryCouponById(id);
    }

    @Operation(summary = "根据id更新优惠券接口")
    @PutMapping("/{id}")
    public void updateCouponById(@RequestBody @Valid CouponFormDTO dto) {
        couponService.updateCouponById(dto);
    }
}
