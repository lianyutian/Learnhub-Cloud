package com.learnhub.learning.service;

import com.learnhub.learning.domain.vo.SignResultVO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 11:04
 */
public interface ISignRecordService {
    /**
     * 添加签到记录
     *
     * @return 签到记录
     */
    SignResultVO addSignRecords();
}
