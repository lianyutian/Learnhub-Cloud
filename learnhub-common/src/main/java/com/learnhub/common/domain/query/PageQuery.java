package com.learnhub.common.domain.query;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.learnhub.common.constants.Constant;
import com.learnhub.common.utils.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 14:12
 */
@Data
@Schema(description = "分页请求参数", name = "PageQuery")
@Accessors(chain = true)
public class PageQuery {
    public static final Integer DEFAULT_PAGE_SIZE = 20;
    public static final Integer DEFAULT_PAGE_NUM = 1;

    @Schema(description = "页码", name = "pageNo", example = "1")
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNo = DEFAULT_PAGE_NUM;

    @Schema(description = "每页大小", name = "pageSize", example = "5")
    @Min(value = 1, message = "每页查询数量不能小于1")
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    @Schema(description = "是否升序", name = "isAsc", example = "true")
    private Boolean isAsc = true;

    @Schema(description = "排序字段", name = "sortBy", example = "id")
    private String sortBy;

    public int from() {
        return (pageNo - 1) * pageSize;
    }

    public <T> Page<T> toMpPage() {
        Page<T> page = PageHelper.startPage(pageNo, pageSize);
        // 前端是否有排序字段
        if (StringUtils.isNotBlank(sortBy)) {
            page.setOrderBy(sortBy + " " + isAsc);
        }
        return page;
    }

    public <T> Page<T> toMpPage(String orderItems) {
        Page<T> page = PageHelper.startPage(pageNo, pageSize);
        // 是否手动指定排序方式
        if (StringUtils.isNotBlank(orderItems)) {
            page.setOrderBy(orderItems);
            return page;
        }
        // 前端是否有排序字段
        if (StringUtils.isNotEmpty(sortBy)) {
            page.setOrderBy(sortBy + " " + isAsc);
        }
        return page;
    }

    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc) {
        if (StringUtils.isBlank(sortBy)) {
            sortBy = defaultSortBy;
            this.isAsc = isAsc;
        }
        Page<T> page = PageHelper.startPage(pageNo, pageSize);
        page.setOrderBy(sortBy + " " + isAsc);
        return page;
    }

    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMpPage(Constant.DATA_FIELD_NAME_CREATE_TIME, false);
    }
}
