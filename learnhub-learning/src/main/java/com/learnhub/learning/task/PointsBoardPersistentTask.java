package com.learnhub.learning.task;

import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.DateUtils;
import com.learnhub.learning.constants.RedisConstants;
import com.learnhub.learning.domain.po.PointsBoard;
import com.learnhub.learning.mapper.PointsBoardMapper;
import com.learnhub.learning.service.IPointsBoardSeasonService;
import com.learnhub.learning.service.IPointsBoardService;
import com.learnhub.learning.utils.TableInfoContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.learnhub.common.utils.DateUtils.POINTS_BOARD_SUFFIX_FORMATTER;
import static com.learnhub.learning.constants.LearningConstants.POINTS_BOARD_TABLE_PREFIX;
import static com.learnhub.learning.constants.RedisConstants.POINTS_BOARD_KEY_PREFIX;

/**
 * 历史积分榜单持久化任务
 * 分成三个步骤:
 * 1. 创建上月积分排行榜表
 * 2. 持久化上月积分排行榜数据
 * 3. 清除redis中历史积分排行榜数据
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/18 13:28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PointsBoardPersistentTask {

    private final IPointsBoardService pointsBoardService;
    private final IPointsBoardSeasonService pointsBoardSeasonService;
    private final PointsBoardMapper pointsBoardMapper;
    private final StringRedisTemplate redisTemplate;

    /**
     * 创建上月积分排行榜表
     * 该方法没有参数和返回值。
     * 该方法通过定时任务触发，每月1号凌晨3点执行，用于创建上一个月的积分排行榜表。
     * 每月1号，凌晨3点执行
     */
    @XxlJob("createPointsBoardTableJob")
    public void createPointsBoardTableOfLastSeason() {
        log.info("创建上月积分排行榜表任务开始");
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
            // TODO 处理异常，如发送告警邮件、记录日志等
        }
        log.info("创建上月积分排行榜表任务结束");
    }

    /**
     * 持久化积分榜单数据到数据库
     */
    @XxlJob("savePointsBoard2DB")
    public void savePointsBoard2DB(){
        log.info("持久化积分榜单数据到数据库任务开始");
        // 1.获取上月时间
        LocalDateTime time = LocalDateTime.now().minusMonths(1);

        // 2.计算动态表名
        // 2.1.查询赛季信息
        Integer season = pointsBoardSeasonService.querySeasonByTime(time);
        if (season == null) {
            log.error("Season not found for time: {}", time);
            // 提前返回，避免后续操作
            return;
        }
        // 2.2.将表名存入ThreadLocal
        String tableName = POINTS_BOARD_TABLE_PREFIX + season;
        TableInfoContext.setInfo(tableName);

        try {
            // 3.查询榜单数据
            // 3.1.拼接KEY
            String key = RedisConstants.POINTS_BOARD_KEY_PREFIX +
                    time.format(DateUtils.POINTS_BOARD_SUFFIX_FORMATTER);
            // 3.2.查询数据
            int index = XxlJobHelper.getShardIndex();
            int total = XxlJobHelper.getShardTotal();
            // 起始页，就是分片序号+1
            int pageNo = index + 1;
            int pageSize = 10;
            boolean hasMore;
            while (true) {
                List<PointsBoard> boardList = pointsBoardService.queryCurrentBoardList(key, pageNo, pageSize);
                hasMore = CollUtils.isNotEmpty(boardList);
                if (!hasMore) {
                    break;
                }
                // 4.持久化到数据库
                // 4.1.把排名信息写入id
                boardList.forEach(board -> {
                    board.setId(board.getRank().longValue());
                    board.setRank(null);
                });
                // 4.2.持久化
                try {
                    pointsBoardMapper.saveBatch(tableName, boardList);
                } catch (Exception e) {
                    log.error("Failed to save batch to table: {}", tableName, e);
                    // 出现异常时，考虑是否需要进行回滚或重试逻辑
                    break; // 在这里跳出循环，或者进行重试逻辑
                }
                // 5.翻页，跳过N个页，N就是分片数量
                pageNo+=total;
            }
        } finally {
            // 任务结束，移除动态表名
            TableInfoContext.remove();
        }
        log.info("持久化积分榜单数据到数据库任务结束");
    }

    /**
     * 清除redis中历史榜单
     */
    @XxlJob("clearPointsBoardFromRedis")
    public void clearPointsBoardFromRedis() {
        log.info("清除redis中历史榜单任务开始");
        try {
            // 1. 获取上月时间
            LocalDateTime time = LocalDateTime.now().minusMonths(1);
            // 2. 计算key
            String pointsBoardKey = POINTS_BOARD_KEY_PREFIX
                    + time.format(POINTS_BOARD_SUFFIX_FORMATTER);

            // 3. 检查Key是否存在
            Boolean keyExists = redisTemplate.hasKey(pointsBoardKey);
            if (Boolean.TRUE.equals(keyExists)) {
                log.info("准备删除Redis中的积分板缓存: {}", pointsBoardKey);
                // 4. 删除
                redisTemplate.unlink(pointsBoardKey);
                log.info("已成功删除Redis中的积分板缓存: {}", pointsBoardKey);
            } else {
                log.warn("指定的key不存在: {}", pointsBoardKey);
            }
        } catch (Exception e) {
            log.error("清除Redis缓存时发生异常: ", e);
        }
        log.info("清除redis中历史榜单任务结束");
    }
}
