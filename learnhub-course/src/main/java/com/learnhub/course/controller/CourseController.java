package com.learnhub.course.controller;

import com.learnhub.common.validate.annotations.ParamChecker;
import com.learnhub.course.domain.dto.CourseBaseInfoSaveDTO;
import com.learnhub.course.domain.vo.CourseSaveVO;
import com.learnhub.course.domain.vo.NameExistVO;
import com.learnhub.course.service.ICourseDraftService;
import com.learnhub.course.service.ICourseService;
import com.learnhub.course.utils.CourseSaveBaseGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * CourseController
 *
 * @author lm
 * @since 2024-05-14 10:07:17
 * @version 1.0
 */
@Tag(name = "课程相关接口")
@RestController
@RequestMapping("courses")
@Slf4j
@Validated
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;
    private final ICourseDraftService courseDraftService;

    @Operation(summary = "校验课程名称是否已经存在")
    @GetMapping("/checkName")
    @Parameters({
            @Parameter(name = "id", description = "id"),
            @Parameter(name = "name", description = "课程名称")
    })
    public NameExistVO checkNameExist(@RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "name") String name) {
        return courseService.checkName(name, id);
    }

    @PostMapping("baseInfo/save")
    @Operation(summary = "保存课程基本信息")
    //校验非业务限制的字段
    @ParamChecker
    public CourseSaveVO saveCourseBaseInfo(@RequestBody @Validated(CourseSaveBaseGroup.class) CourseBaseInfoSaveDTO courseBaseInfoSaveDTO) {
        return courseDraftService.saveCourseBaseInfo(courseBaseInfoSaveDTO);
    }
}
