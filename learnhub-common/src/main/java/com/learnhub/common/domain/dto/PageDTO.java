package com.learnhub.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.Convert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 14:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页结果", name = "PageDTO")
public class PageDTO<T> {
    @Schema(description = "总条数", name = "total")
    protected Long total;
    @Schema(description = "总页码数", name = "pages")
    protected Integer pages;
    @Schema(description = "当前页数据", name = "list")
    protected List<T> list;

    public static <T> PageDTO<T> empty(Long total, Integer pages) {
        return new PageDTO<>(total, pages, CollUtils.emptyList());
    }
    public static <T> PageDTO<T> empty(Page<?> page) {
        return new PageDTO<>(page.getTotal(), page.getPages(), CollUtils.emptyList());
    }

    public static <T> PageDTO<T> of(Page<T> page) {
        if(page == null){
            return new PageDTO<>();
        }
        if (CollUtils.isEmpty(page.getResult())) {
            return empty(page);
        }
        return new PageDTO<>(page.getTotal(), page.getPages(), page.getResult());
    }
    public static <T,R> PageDTO<T> of(Page<R> page, Function<R, T> mapper) {
        if(page == null){
            return new PageDTO<>();
        }
        if (CollUtils.isEmpty(page.getResult())) {
            return empty(page);
        }
        return new PageDTO<>(page.getTotal(), page.getPages(),
                page.getResult().stream().map(mapper).collect(Collectors.toList()));
    }
    public static <T> PageDTO<T> of(Page<?> page, List<T> list) {
        return new PageDTO<>(page.getTotal(), page.getPages(), list);
    }

    public static <T, R> PageDTO<T> of(Page<R> page, Class<T> clazz) {
        return new PageDTO<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getResult(), clazz));
    }

    public static <T, R> PageDTO<T> of(Page<R> page, Class<T> clazz, Convert<R, T> convert) {
        return new PageDTO<>(page.getTotal(), page.getPages(), BeanUtils.copyList(page.getResult(), clazz, convert));
    }

    @Schema(hidden = true)
    @JsonIgnore
    public boolean isEmpty(){
        return list == null || list.size() == 0;
    }
}
