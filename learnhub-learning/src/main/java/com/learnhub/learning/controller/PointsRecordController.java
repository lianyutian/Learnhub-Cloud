package com.learnhub.learning.controller;

import com.learnhub.learning.domain.vo.PointsStatisticsVO;
import com.learnhub.learning.service.IPointsRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 9:32
 */
@Tag(name = "积分记录相关接口")
@RequiredArgsConstructor
@RequestMapping("points")
@RestController
public class PointsRecordController {

    private final IPointsRecordService pointsRecordService;

    @Operation(summary = "查询我的今日积分")
    @GetMapping("today")
    public List<PointsStatisticsVO> queryMyPointsToday(){
        return pointsRecordService.queryMyPointsToday();
    }
}
