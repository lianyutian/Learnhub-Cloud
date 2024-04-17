package com.learnhub.learning.task;

import com.learnhub.learning.service.IPointsBoardSeasonService;
import com.learnhub.learning.service.IPointsBoardService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/17 14:54
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PointsBoardTableCreateTask {

    private final IPointsBoardSeasonService pointsBoardSeasonService;

    private final IPointsBoardService pointsBoardService;

    /**
     * 创建上月积分排行榜表
     * 该方法没有参数和返回值。
     * 该方法通过定时任务触发，每月1号凌晨3点执行，用于创建上一个月的积分排行榜表。
     */
    @XxlJob("createPointsBoardTableJob") // 每月1号，凌晨3点执行
    public void createPointsBoardTableOfLastSeason() {
        try {
            // 1. 获取系统当前时区的上月时间
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).minusMonths(1);
            // 2. 查询赛季id
            Integer season = pointsBoardSeasonService.querySeasonByTime(time);
            if (season == null) {
                log.info("赛季信息不存在，无需创建积分排行榜表。");
                return;
            }
            // 3. 创建表
            pointsBoardService.createPointsBoardTableBySeason(season);
            log.info("上月积分排行榜表创建成功。");
        } catch (Exception e) {
            log.error("创建上月积分排行榜表失败: ", e);
            // 处理异常，如发送告警邮件、记录日志等
        }
    }
}
