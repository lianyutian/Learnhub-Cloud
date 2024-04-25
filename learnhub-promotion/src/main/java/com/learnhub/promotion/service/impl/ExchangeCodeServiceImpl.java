package com.learnhub.promotion.service.impl;

import com.learnhub.promotion.constants.PromotionConstants;
import com.learnhub.promotion.domain.po.Coupon;
import com.learnhub.promotion.domain.po.ExchangeCode;
import com.learnhub.promotion.mapper.ExchangeCodeMapper;
import com.learnhub.promotion.service.IExchangeCodeService;
import com.learnhub.promotion.utils.CodeUtil;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 16:09
 */
@Service
public class ExchangeCodeServiceImpl implements IExchangeCodeService {

    private final StringRedisTemplate redisTemplate;
    private final BoundValueOperations<String, String> serialOps;
    private final ExchangeCodeMapper exchangeCodeMapper;

    public ExchangeCodeServiceImpl(StringRedisTemplate redisTemplate, ExchangeCodeMapper exchangeCodeMapper) {
        this.redisTemplate = redisTemplate;
        this.serialOps = redisTemplate.boundValueOps(PromotionConstants.COUPON_CODE_SERIAL_KEY);
        this.exchangeCodeMapper = exchangeCodeMapper;
    }

    @Override
    @Async("generateExchangeCodeExecutor")
    public void asyncGenerateCode(Coupon coupon) {
        // 发放数量
        Integer totalNum = coupon.getTotalNum();
        // 1.获取Redis自增序列号
        Long result = serialOps.increment(totalNum);
        if (result == null) {
            return;
        }
        int maxSerialNum = result.intValue();
        List<ExchangeCode> list = new ArrayList<>(totalNum);
        for (int serialNum = maxSerialNum - totalNum + 1; serialNum <= maxSerialNum; serialNum++) {
            // 2.生成兑换码
            String code = CodeUtil.generateCode(serialNum, coupon.getId());
            ExchangeCode exchangeCode = new ExchangeCode();
            exchangeCode.setCode(code);
            exchangeCode.setId(serialNum);
            exchangeCode.setExchangeTargetId(coupon.getId());
            exchangeCode.setExpiredTime(coupon.getIssueEndTime());
            list.add(exchangeCode);
        }
        // 3.保存数据库
        exchangeCodeMapper.saveExchangeCodes(list);

        // 4.写入Redis缓存，member：couponId，score：兑换码的最大序列号
        redisTemplate.opsForZSet().add(PromotionConstants.COUPON_RANGE_KEY, coupon.getId().toString(), maxSerialNum);
    }
}
