package com.learnhub.learning.controller;

import com.learnhub.learning.domain.query.PointsBoardQuery;
import com.learnhub.learning.domain.vo.PointsBoardVO;
import com.learnhub.learning.service.IPointsBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:20
 */
@RestController
@RequestMapping("boards")
@RequiredArgsConstructor
@Tag(name = "PointsBoardController", description = "积分排行榜")
public class PointsBoardController {

    private final IPointsBoardService pointsBoardService;

    @GetMapping
    @Operation(summary = "分页查询指定赛季的积分排行榜")
    public PointsBoardVO queryPointsBoardBySeason(PointsBoardQuery query){
        return pointsBoardService.queryPointsBoardBySeason(query);
    }
}
