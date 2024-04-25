package com.learnhub.promotion.domain.vo;

import com.learnhub.promotion.enums.CouponStatus;
import com.learnhub.promotion.enums.DiscountType;
import com.learnhub.promotion.enums.ObtainType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 11:18
 */
@Data
@Schema(name = "CouponPageVO", description = "优惠券分页实体")
public class CouponPageVO {

    @Schema(description = "优惠券id，新增不需要添加，更新必填", name = "id")
    private Long id;

    @Schema(description = "优惠券名称", name = "name")
    private String name;

    @Schema(description = "是否限定使用范围", name = "specific")
    private Boolean specific;

    @Schema(description = "优惠券类型，1：每满减，2：折扣，3：无门槛，4：普通满减", name = "discountType")
    private DiscountType discountType;

    @Schema(description = "折扣门槛，0代表无门槛", name = "thresholdAmount")
    private Integer thresholdAmount;

    @Schema(description = "折扣值，满减填抵扣金额；打折填折扣值：80标示打8折", name = "discountValue")
    private Integer discountValue;

    @Schema(description = "最大优惠金额", name = "maxDiscountAmount")
    private Integer maxDiscountAmount;

    @Schema(description = "获取方式1：手动领取，2：指定发放（通过兑换码兑换）", name = "obtainType")
    private ObtainType obtainType;

    @Schema(description = "已使用", name = "usedNum")
    private Integer usedNum;

    @Schema(description = "已发放数量", name = "issueNum")
    private Integer issueNum;

    @Schema(description = "优惠券总量", name = "totalNum")
    private Integer totalNum;

    @Schema(description = "优惠券创建时间", name = "createTime")
    private LocalDateTime createTime;

    @Schema(description = "发放开始时间", name = "issueBeginTime")
    private LocalDateTime issueBeginTime;

    @Schema(description = "发放结束时间", name = "issueEndTime")
    private LocalDateTime issueEndTime;

    @Schema(description = "有效天数", name = "termDays")
    private Integer termDays;

    @Schema(description = "使用有效期开始时间", name = "termBeginTime")
    private LocalDateTime termBeginTime;

    @Schema(description = "使用有效期结束时间", name = "termEndTime")
    private LocalDateTime termEndTime;

    @Schema(description = "状态", name = "status")
    private CouponStatus status;
}
