package com.learnhub.api.client.course;

import com.learnhub.api.dto.course.CourseFullInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CourseClient
 *
 * @author lm
 * @since 2024-05-14 10:01:18
 * @version 1.0
 */
@FeignClient(contextId = "course", value = "course-service")
public interface CourseClient {
    /**
     * 根据课程id，获取课程、目录、教师信息
     *
     * @param id 课程id
     * @return 课程信息、目录信息、教师信息
     */
    @GetMapping("/course/{id}")
    CourseFullInfoDTO getCourseInfoById(
            @PathVariable("id") Long id,
            @RequestParam(value = "withCatalogue", required = false) boolean withCatalogue,
            @RequestParam(value = "withTeachers", required = false) boolean withTeachers
    );
}
