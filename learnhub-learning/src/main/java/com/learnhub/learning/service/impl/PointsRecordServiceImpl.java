package com.learnhub.learning.service.impl;

import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.DateUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.learning.constants.RedisConstants;
import com.learnhub.learning.domain.po.PointsRecord;
import com.learnhub.learning.domain.vo.PointsStatisticsVO;
import com.learnhub.learning.enums.PointsRecordType;
import com.learnhub.learning.mapper.PointsRecordMapper;
import com.learnhub.learning.service.IPointsBoardSeasonService;
import com.learnhub.learning.service.IPointsRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 17:53
 */
@Service
@RequiredArgsConstructor
public class PointsRecordServiceImpl implements IPointsRecordService {

    private final PointsRecordMapper pointsRecordMapper;
    private final StringRedisTemplate redisTemplate;
    private final IPointsBoardSeasonService pointsBoardSeasonService;

    @Override
    public void addPointsRecord(Long userId, int points, PointsRecordType type) {
        LocalDateTime now = LocalDateTime.now();
        int maxPoints = type.getMaxPoints();
        // 1.判断当前方式有没有积分上限
        int realPoints = points;
        if(maxPoints > 0) {
            // 2.有，则需要判断是否超过上限
            LocalDateTime begin = DateUtils.getDayStartTime(now);
            LocalDateTime end = DateUtils.getDayEndTime(now);
            // 2.1.查询今日已得积分
            int currentPoints = pointsRecordMapper.queryUserPointsByTypeAndDate(userId, type, begin, end);
            // 2.2.判断是否超过上限
            if(currentPoints >= maxPoints) {
                // 2.3.超过，直接结束
                return;
            }
            // 2.4.没超过，保存积分记录
            if(currentPoints + points > maxPoints){
                realPoints = maxPoints - currentPoints;
            }
        }
        // 3.没有，直接保存积分记录
        PointsRecord pointsRecord = new PointsRecord();
        pointsRecord.setPoints(realPoints);
        pointsRecord.setUserId(userId);
        pointsRecord.setType(type);
        Integer season = pointsBoardSeasonService.querySeasonByTime(now);
        pointsRecord.setSeason(season);
        pointsRecordMapper.savePointsRecord(pointsRecord);
        // 4.更新总积分到Redis
        String key = RedisConstants.POINTS_BOARD_KEY_PREFIX + now.format(DateUtils.POINTS_BOARD_SUFFIX_FORMATTER);
        redisTemplate.opsForZSet().incrementScore(key, userId.toString(), realPoints);
    }

    @Override
    public List<PointsStatisticsVO> queryMyPointsToday() {
        // 1.获取用户
        Long userId = UserContext.getUserId();
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime begin = DateUtils.getDayStartTime(now);
        LocalDateTime end = DateUtils.getDayEndTime(now);
        // 3.查询
        List<PointsRecord> pointsRecordList = pointsRecordMapper.queryUserPointsByDate(userId, begin, end);

        if (CollUtils.isEmpty(pointsRecordList)) {
            return CollUtils.emptyList();
        }
        // 4.封装返回
        List<PointsStatisticsVO> vos = new ArrayList<>(pointsRecordList.size());
        for (PointsRecord pointsRecord : pointsRecordList) {
            PointsStatisticsVO vo = new PointsStatisticsVO();
            vo.setType(pointsRecord.getType().getDesc());
            vo.setMaxPoints(pointsRecord.getType().getMaxPoints());
            vo.setPoints(pointsRecord.getPoints());
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public void createPointsRecordTableOfLastSeason(Integer season) {
        pointsRecordMapper.createPointsRecordTableOfLastSeason(season);
    }

    @Override
    public void migrationPointsRecord(Integer season) {
        pointsRecordMapper.migrationPointsRecord(season);
    }

    @Override
    public void clearPointsRecord(Integer season) {
        pointsRecordMapper.clearPointsRecord(season);
    }
}
