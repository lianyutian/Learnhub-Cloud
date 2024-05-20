package com.learnhub.course.controller;

import com.learnhub.course.domain.vo.SimpleCategoryVO;
import com.learnhub.course.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程分类Controller
 *
 * @author lm
 * @since 2024-05-11 17:33:36
 * @version 1.0
 */
@RestController
@Tag(name = "课程分类相关接口")
@RequestMapping("categorys")
@Slf4j
@Validated
@AllArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("queryAllCategorys")
    @Operation(summary = "获取所有的课程分类信息，只包含id,名称,课程分类关系")
    public List<SimpleCategoryVO> queryAllCategorys(@RequestParam(value = "admin", required = false, defaultValue = "0")
                                                    Boolean admin) {
        return categoryService.queryAllCategorys(admin);
    }
}
