package com.learnhub.promotion.domain.po;

import com.learnhub.promotion.enums.ExchangeCodeStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 16:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExchangeCode {
    /**
     * 兑换码id
     */
    private Integer id;

    /**
     * 兑换码
     */
    private String code;

    /**
     * 兑换码状态， 1：待兑换，2：已兑换，3：兑换活动已结束
     */
    private ExchangeCodeStatus status;

    /**
     * 兑换人
     */
    private Long userId;

    /**
     * 兑换类型，1：优惠券，以后再添加其它类型
     */
    private Integer type;

    /**
     * 兑换码目标id，例如兑换优惠券，该id则是优惠券的配置id
     */
    private Long exchangeTargetId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 兑换码过期时间
     */
    private LocalDateTime expiredTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
