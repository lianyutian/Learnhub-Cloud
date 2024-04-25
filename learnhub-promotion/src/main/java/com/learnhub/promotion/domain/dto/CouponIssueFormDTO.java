package com.learnhub.promotion.domain.dto;

import com.learnhub.common.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 14:32
 */
@Data
@Schema(description = "优惠券发放的表单实体", name = "CouponIssueFormDTO")
public class CouponIssueFormDTO {
    @Schema(description = "优惠券id", name = "id")
    private Long id;

    @Schema(description = "发放开始时间", name = "issueBeginTime")
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_TIME_FORMAT)
    @Future(message = "发放开始时间必须晚于当前时间")
    private LocalDateTime issueBeginTime;

    @Future(message = "发放结束时间必须晚于当前时间")
    @NotNull(message = "发放结束时间不能为空")
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
}
