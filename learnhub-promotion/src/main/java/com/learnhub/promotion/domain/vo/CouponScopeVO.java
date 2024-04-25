package com.learnhub.promotion.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 16:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "优惠券使用范围", name = "CouponScopeVO")
public class CouponScopeVO {
    @Schema(description = "范围id集合", name = "id")
    private Long id;
    @Schema(description = "范围名称集合", name = "name")
    private String name;
}
