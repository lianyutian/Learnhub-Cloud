package com.learnhub.promotion.domain.vo;

import com.learnhub.common.utils.DateUtils;
import com.learnhub.promotion.enums.DiscountType;
import com.learnhub.promotion.enums.ObtainType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 16:31
 */
@Data
@Schema(description = "优惠券详细数据", name = "CouponDetailVO")
public class CouponDetailVO {
    @Schema(description = "优惠券id，新增不需要添加，更新必填", name = "id")
    private Long id;

    @Schema(description = "优惠券名称", name = "name")
    private String name;

    @Schema(description = "优惠券使用范围，key范围类型(0-没有限制，1-限定分类，2-限定课程)，值是范围ID", name = "scopes")
    private List<CouponScopeVO> scopes;

    @Schema(description = "优惠券类型，1：每满减，2：折扣，3：无门槛，4：普通满减", name = "discountType")
    private DiscountType discountType;

    @Schema(description = "折扣门槛，0代表无门槛", name = "thresholdAmount")
    private Integer thresholdAmount;

    @Schema(description = "折扣值，满减填抵扣金额；打折填折扣值：80标示打8折", name = "discountValue")
    private Integer discountValue;

    @Schema(description = "最大优惠金额", name = "maxDiscountAmount")
    private Integer maxDiscountAmount;

    @Schema(description = "发放开始时间", name = "issueBeginTime")
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime issueBeginTime;

    @Schema(description = "发放结束时间", name = "issueEndTime")
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime issueEndTime;

    @Schema(description = "有效天数", name = "termDays")
    private Integer termDays;

    @Schema(description = "使用有效期开始时间", name = "termBeginTime")
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime termBeginTime;

    @Schema(description = "使用有效期结束时间", name = "termEndTime")
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_TIME_FORMAT)
    private LocalDateTime termEndTime;

    @Schema(description = "优惠券总量，如果为0代表无上限", name = "totalNum")
    private Integer totalNum;

    @Schema(description = "每人领取的上限", name = "userLimit")
    private Integer userLimit;

    @Schema(description = "获取方式1：手动领取，2：指定发放（通过兑换码兑换）", name = "obtainType")
    private ObtainType obtainType;
}
