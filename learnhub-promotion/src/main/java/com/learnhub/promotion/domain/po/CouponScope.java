package com.learnhub.promotion.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 优惠券作用范围信息
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/22 17:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CouponScope {
    @AutoId
    private Long id;

    /**
     * 范围限定类型：1-分类，2-课程，等等
     */
    private Integer type;

    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * 优惠券作用范围的业务id，例如分类id、课程id
     */
    private Long bizId;

}
