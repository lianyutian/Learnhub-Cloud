package com.learnhub.remark.task;

import com.learnhub.remark.service.ILikedRecordService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/10 20:32]
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LikedTimesCheckTask {

    private static final List<String> BIZ_TYPES = List.of("QA");
    private static final int MAX_BIZ_SIZE = 30;

    private final ILikedRecordService recordService;


    /**
     * 定时计算业务点赞数量
     */
    @XxlJob("checkLikedTimes")
    public void checkLikedTimes() {
        log.info("定时计算业务点赞数量开始: {}", LocalDateTime.now());
        for (String bizType : BIZ_TYPES) {
            recordService.readLikedTimesAndSendMessage(bizType, MAX_BIZ_SIZE);
        }
        log.info("定时计算业务点赞数量结束: {}", LocalDateTime.now());
    }
}
