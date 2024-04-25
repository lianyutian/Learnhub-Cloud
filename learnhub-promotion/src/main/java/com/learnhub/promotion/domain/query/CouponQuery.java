package com.learnhub.promotion.domain.query;

import com.learnhub.common.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 11:16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "优惠券查询参数", name = "CouponQuery")
public class CouponQuery extends PageQuery {
    @Schema(description = "优惠券折扣类型：1：每满减，2：折扣，3：无门槛，4：满减", name = "type")
    private Integer type;

    @Schema(description = "优惠券状态，1：待发放，2：发放中，3：已结束, 4：取消/终止", name = "status")
    private Integer status;

    @Schema(description = "优惠券名称", name = "name")
    private String name;
}
