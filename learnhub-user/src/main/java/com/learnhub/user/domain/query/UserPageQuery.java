package com.learnhub.user.domain.query;

import com.learnhub.common.domain.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/26 16:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户分页查询条件", name = "UserPageQuery")
public class UserPageQuery extends PageQuery {
    @Schema(description = "账户状态",name = "status")
    private Integer status;
    @Schema(description = "教师名称", name = "name")
    private String name;
    @Schema(description = "手机号码", name = "phone")
    private String phone;
}
