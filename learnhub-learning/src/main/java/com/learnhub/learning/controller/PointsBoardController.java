package com.learnhub.learning.controller;

import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.learning.domain.po.PointsBoardSeason;
import com.learnhub.learning.domain.query.PointsBoardQuery;
import com.learnhub.learning.domain.vo.PointsBoardSeasonVO;
import com.learnhub.learning.domain.vo.PointsBoardVO;
import com.learnhub.learning.service.IPointsBoardSeasonService;
import com.learnhub.learning.service.IPointsBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:20
 */
@RestController
@RequestMapping("boards")
@RequiredArgsConstructor
@Tag(name = "积分排行榜相关接口")
public class PointsBoardController {

    private final IPointsBoardService pointsBoardService;
    private final IPointsBoardSeasonService pointsBoardSeasonService;

    @GetMapping
    @Operation(summary = "分页查询指定赛季的积分排行榜")
    public PointsBoardVO queryPointsBoardBySeason(PointsBoardQuery query) {
        return pointsBoardService.queryPointsBoardBySeason(query);
    }

    @Operation(summary = "查询赛季信息列表")
    @GetMapping("/seasons/list")
    public List<PointsBoardSeasonVO> queryPointsBoardSeasons() {
        // 1.获取时间
        LocalDateTime now = LocalDateTime.now();

        // 2.查询赛季列表，必须是当前赛季之前的（开始时间小于等于当前时间）
        List<PointsBoardSeason> list = pointsBoardSeasonService.queryPointsBoardSeasons(now);
        if (CollUtils.isEmpty(list)) {
            return CollUtils.emptyList();
        }
        // 3.返回VO
        return BeanUtils.copyToList(list, PointsBoardSeasonVO.class);
    }
}
