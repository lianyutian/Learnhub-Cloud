package com.learnhub.course.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 所有课程分类数据
 *
 * @author lm
 * @since 2024-05-11 17:36:48
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SimpleCategoryVO", description = "所有课程分类数据")
public class SimpleCategoryVO {
    private Long id;
    private String name;
    private List<SimpleCategoryVO> children;
    private Integer level;
    private Long parentId;
}
