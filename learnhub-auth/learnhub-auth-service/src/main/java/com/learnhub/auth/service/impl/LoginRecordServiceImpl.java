package com.learnhub.auth.service.impl;

import com.learnhub.auth.domain.po.LoginRecord;
import com.learnhub.auth.mapper.LoginRecordMapper;
import com.learnhub.auth.service.ILoginRecordService;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.MarkedRunnable;
import com.learnhub.common.utils.WebUtils;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 20:49]
 */
@Service
@AllArgsConstructor
public class LoginRecordServiceImpl implements ILoginRecordService {

    private static final Executor WRITE_RECORD_EXECUTOR;

    static {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(20);
        //配置最大线程数
        executor.setMaxPoolSize(40);
        //配置队列大小
        executor.setQueueCapacity(99999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("login-record-write-worker-");
        // 设置拒绝策略：放弃任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        //执行初始化
        executor.initialize();
        WRITE_RECORD_EXECUTOR = executor;
    }

    private final LoginRecordMapper loginRecordMapper;

    @Override
    public void saveLoginSuccessRecord(String cellPhone, Long userId) {
        LoginRecord record = new LoginRecord();
        LocalDateTime now = LocalDateTime.now();
        record.setLoginTime(now);
        record.setLoginDate(now.toLocalDate());
        record.setUserId(userId);
        record.setCellPhone(cellPhone);
        record.setIpv4(WebUtils.getRemoteAddr());
        saveAsync(record);
    }

    @Override
    @Transactional(rollbackFor = {DbException.class, Exception.class})
    public void saveAsync(LoginRecord record) {
        WRITE_RECORD_EXECUTOR.execute(new MarkedRunnable(() -> loginRecordMapper.saveLoginSuccessRecord(record)));
    }
}
