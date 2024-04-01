package com.learnhub.auth.service;

import com.learnhub.auth.domain.po.LoginRecord;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 20:47]
 */
public interface ILoginRecordService {
    /**
     * 保存登录成功记录
     *
     * @param cellPhone 电话
     * @param userId 用户id
     */
    void saveLoginSuccessRecord(String cellPhone, Long userId);

    /**
     * 异步保存登录成功记录
     *
     * @param record 登录记录
     */
    void saveAsync(LoginRecord record);
}
