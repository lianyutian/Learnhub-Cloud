package com.learnhub.promotion.service.impl;

import com.github.pagehelper.Page;
import com.learnhub.common.domain.dto.PageDTO;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.promotion.domain.dto.CouponFormDTO;
import com.learnhub.promotion.domain.dto.CouponIssueFormDTO;
import com.learnhub.promotion.domain.po.Coupon;
import com.learnhub.promotion.domain.po.CouponScope;
import com.learnhub.promotion.domain.query.CouponQuery;
import com.learnhub.promotion.domain.vo.CouponDetailVO;
import com.learnhub.promotion.domain.vo.CouponPageVO;
import com.learnhub.promotion.domain.vo.CouponScopeVO;
import com.learnhub.promotion.enums.CouponStatus;
import com.learnhub.promotion.enums.ObtainType;
import com.learnhub.promotion.mapper.CouponMapper;
import com.learnhub.promotion.mapper.CouponScopeMapper;
import com.learnhub.promotion.service.ICouponScopeService;
import com.learnhub.promotion.service.ICouponService;
import com.learnhub.promotion.service.IExchangeCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final IExchangeCodeService exchangeCodeService;
    private final CouponScopeMapper couponScopeMapper;

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

    @Override
    public PageDTO<CouponPageVO> queryCouponByPage(CouponQuery query) {
        // 1.分页查询
        try (Page<Coupon> page = query.toMpPageDefaultSortByCreateTimeDesc()) {
            page.doSelectPage( () -> couponMapper.queryCouponByPage(query));

            if (CollUtils.isEmpty(page.getResult())) {
                return PageDTO.empty(page);
            }

            List<CouponPageVO> list = BeanUtils.copyList(page.getResult(), CouponPageVO.class);

            // 3.返回
            return PageDTO.of(page, list);
        }
    }

    @Override
    @Transactional(rollbackFor = DbException.class)
    public void beginIssue(CouponIssueFormDTO dto) {
        // 1.查询优惠券
        Coupon coupon = couponMapper.queryCouponById(dto.getId());
        if (coupon == null) {
            throw new BadRequestException("优惠券不存在!");
        }
        // 2.判断优惠券状态，是否是暂停或待发放
        if (coupon.getStatus() != CouponStatus.DRAFT && coupon.getStatus() != CouponStatus.PAUSE) {
            throw new BadRequestException("优惠券状态异常!");
        }
        // 3.判断是否是立刻发放
        LocalDateTime issueBeginTime = dto.getIssueBeginTime();
        LocalDateTime now = LocalDateTime.now();
        boolean isBegin = issueBeginTime == null || !issueBeginTime.isAfter(now);
        // 4.更新优惠券
        // 4.1.拷贝属性到PO
        Coupon copyBean = BeanUtils.copyBean(dto, Coupon.class);
        // 4.2.更新状态
        if (isBegin) {
            copyBean.setStatus(CouponStatus.ISSUING);
            copyBean.setIssueBeginTime(now);
        }else{
            copyBean.setStatus(CouponStatus.UN_ISSUE);
        }
        // 4.3.写入数据库
        couponMapper.updateCouponById(copyBean);

        // 5.判断是否需要生成兑换码，优惠券类型必须是兑换码，优惠券状态必须是待发放
        if(coupon.getObtainType() == ObtainType.ISSUE && coupon.getStatus() == CouponStatus.DRAFT){
            coupon.setIssueEndTime(copyBean.getIssueEndTime());
            exchangeCodeService.asyncGenerateCode(coupon);
        }

    }

    @Override
    public CouponDetailVO queryCouponById(Long id) {
        // 1.查询优惠券
        Coupon coupon = couponMapper.queryCouponById(id);
        // 2.转换VO
        CouponDetailVO vo = BeanUtils.copyBean(coupon, CouponDetailVO.class);
        if (vo == null || !coupon.getSpecific()) {
            // 数据不存在，或者没有限定范围，直接结束
            return vo;
        }
        // 3.查询限定范围
        List<CouponScope> scopes =couponScopeMapper.queryCouponScopesByCouponId(id);
        if (CollUtils.isEmpty(scopes)) {
            return vo;
        }
        List<CouponScopeVO> scopeVOS = scopes.stream()
                .map(CouponScope::getBizId)
                .map(cateId -> new CouponScopeVO(cateId, ""))
                .collect(Collectors.toList());
        vo.setScopes(scopeVOS);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = DbException.class)
    public void updateCouponById(CouponFormDTO dto) {
        // 1.1.转PO
        Coupon coupon = BeanUtils.copyBean(dto, Coupon.class);
        couponMapper.updateCouponById(coupon);
    }
}
