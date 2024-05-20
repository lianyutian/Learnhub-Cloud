package com.learnhub.course.controller;

import com.learnhub.api.dto.course.CourseFullInfoDTO;
import com.learnhub.course.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * CourseInfoController
 * 课程信息内部服务接口调用
 *
 * @author lm
 * @since 2024-05-14 10:20:14
 * @version 1.0
 */
@RestController
@RequestMapping("course")
@Tag(name = "课程相关接口，内部调用")
@AllArgsConstructor
public class CourseInfoController {

    private final ICourseService courseService;

    @GetMapping("/{id}")
    @Operation(summary = "获取课程信息")
    @Parameters({
            @Parameter(name = "id", description = "获取课程信息"),
            @Parameter(name = "withCatalogue", description = "是否要查询目录信息"),
            @Parameter(name = "withTeachers", description = "是否查询课程老师信息")
    })
    public CourseFullInfoDTO queryCourseInfoById(
            @PathVariable("id") Long id,
            @RequestParam(value = "withCatalogue", required = false) boolean withCatalogue,
            @RequestParam(value = "withTeachers", required = false) boolean withTeachers) {
        return courseService.queryCourseInfoById(id, withCatalogue, withTeachers);
    }
}
