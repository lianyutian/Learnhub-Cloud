package com.learnhub.learning.task;

import com.learnhub.learning.service.IPointsBoardSeasonService;
import com.learnhub.learning.service.IPointsRecordService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 积分明细表数据迁移任务
 * 分成三个步骤:
 *  1. 创建上赛季积分明细表
 *  2. 迁移上赛季积分明细
 *  3. 清空上赛季积分明细
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/19 11:23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PointsRecordDataMigrationTask {

    private final IPointsBoardSeasonService pointsBoardSeasonService;
    private final IPointsRecordService pointsRecordService;

    /**
     * 创建上赛季积分明细表
     */
    @XxlJob("createLastSeasonPointsRecordTableJob")
    public void createLastSeasonPointsRecordTable() {
        log.info("创建上赛季积分明细表任务开始");
        try {
            // 1. 获取系统当前时区的上月时间
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).minusMonths(1);
            // 2. 查询赛季id
            Integer season = pointsBoardSeasonService.querySeasonByTime(time);
            if (season == null) {
                log.info("赛季信息不存在，无需创建积分明细表。");
                return;
            }
            // 3. 创建表
            pointsRecordService.createPointsRecordTableOfLastSeason(season);
            log.info("上赛季积分明细表创建成功。");
        } catch (Exception e) {
            log.error("创建上赛季积分明细表失败: ", e);
            // TODO 处理异常，如发送告警邮件、记录日志等
        }
        log.info("创建上赛季积分明细表任务结束");
    }

    /**
     * 迁移上赛季积分明细
     */
    @XxlJob("migrationLastSeasonPointsRecordJob")
    public void migrationLastSeasonPointsRecord() {
        log.info("迁移上赛季积分明细任务开始");
        try {
            // 1. 获取系统当前时区的上月时间
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).minusMonths(1);
            // 2. 查询赛季id
            Integer season = pointsBoardSeasonService.querySeasonByTime(time);
            if (season == null) {
                log.info("赛季信息不存在，无需迁移积分明细。");
            }
            // 3. 迁移表数据
            pointsRecordService.migrationPointsRecord(season);
        } catch (Exception e) {
            log.error("迁移上赛季积分明细失败: ", e);
            // TODO 处理异常，如发送告警邮件、记录日志等
        }
        log.info("迁移上赛季积分明细任务结束");
    }

    /**
     * 清理上赛季积分明细记录
     */
    @XxlJob("clearLastSeasonPointsRecordJob")
    public void clearLastSeasonPointsRecord() {
        log.info("清理上赛季积分明细任务开始");
        try {
            // 1. 获取系统当前时区的上月时间
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).minusMonths(1);
            // 2. 查询赛季id
            Integer season = pointsBoardSeasonService.querySeasonByTime(time);
            if (season == null) {
                log.info("赛季信息不存在，无需清理积分明细。");
            }
            // 3. 清理上赛季积分
            pointsRecordService.clearPointsRecord(season);
        } catch (Exception e) {
            log.error("清理上赛季积分明细失败: ", e);
            // TODO 处理异常，如发送告警邮件、记录日志等
        }
        log.info("清理上赛季积分明细任务结束");
    }
}
