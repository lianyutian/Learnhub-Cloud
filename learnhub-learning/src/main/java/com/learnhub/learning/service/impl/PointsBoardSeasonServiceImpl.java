package com.learnhub.learning.service.impl;

import com.learnhub.learning.mapper.PointsBoardSeasonMapper;
import com.learnhub.learning.service.IPointsBoardSeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/17 14:58
 */
@Service
@RequiredArgsConstructor
public class PointsBoardSeasonServiceImpl implements IPointsBoardSeasonService {

    private final PointsBoardSeasonMapper pointsBoardSeasonMapper;

    @Override
    public Integer querySeasonByTime(LocalDateTime time) {
        return pointsBoardSeasonMapper.querySeasonByTime(time);
    }
}
