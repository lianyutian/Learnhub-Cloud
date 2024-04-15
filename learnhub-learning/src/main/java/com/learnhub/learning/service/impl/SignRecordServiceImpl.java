package com.learnhub.learning.service.impl;

import com.learnhub.common.autoconfigure.mq.RocketMQEnhanceTemplate;
import com.learnhub.common.constants.MqConstants;
import com.learnhub.common.exceptions.BizIllegalException;
import com.learnhub.common.utils.BooleanUtils;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.DateUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.learning.constants.RedisConstants;
import com.learnhub.learning.domain.vo.SignResultVO;
import com.learnhub.learning.mq.message.SignInMessage;
import com.learnhub.learning.service.ISignRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.learnhub.common.constants.MqConstants.Topic.LEARNING_TOPIC;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 11:13
 */
@Service
@RequiredArgsConstructor
public class SignRecordServiceImpl implements ISignRecordService {

    private final StringRedisTemplate redisTemplate;
    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    @Override
    public SignResultVO addSignRecords() {
        // 1.签到
        // 1.1.获取登录用户
        Long userId = UserContext.getUserId();
        // 1.2.获取日期
        LocalDate now = LocalDate.now();
        // 1.3.拼接key
        String key = RedisConstants.SIGN_RECORD_KEY_PREFIX
                + userId
                + now.format(DateUtils.SIGN_DATE_SUFFIX_FORMATTER);
        // 1.4.计算offset
        int offset = now.getDayOfMonth() - 1;
        // 1.5.保存签到信息
        // SETBIT key offset value
        Boolean exists = redisTemplate.opsForValue().setBit(key, offset, true);
        if (BooleanUtils.isTrue(exists)) {
            throw new BizIllegalException("不允许重复签到！");
        }
        // 2.计算连续签到天数
        int signDays = countSignDays(key, now.getDayOfMonth());
        // 3.计算签到得分
        int rewardPoints = switch (signDays) {
            case 7 -> 10;
            case 14 -> 20;
            case 28 -> 40;
            default -> 0;
        };
        SignInMessage signInMessage = new SignInMessage();
        signInMessage.setSource(MqConstants.Source.SIGN_IN_SOURCE);
        signInMessage.setUserId(userId);
        signInMessage.setPoints(rewardPoints + 1);
        // 4.保存积分明细记录
        rocketMQEnhanceTemplate.send(
                LEARNING_TOPIC,
                MqConstants.Tag.SIGN_IN_TAG,
                signInMessage)
        ;
        // 5.封装返回
        SignResultVO vo = new SignResultVO();
        vo.setSignDays(signDays);
        vo.setRewardPoints(rewardPoints);
        return vo;
    }

    /**
     * 计算连续签到天数
     *
     * @param key key
     * @param len 当前天数
     * @return 连续签到天数
     */
    private int countSignDays(String key, int len) {
        // 1.获取本月从第一天开始，到今天为止的所有签到记录
        // BITFIELD key GET u[dayOfMonth] 0
        List<Long> result = redisTemplate.opsForValue()
                .bitField(
                        key,
                        BitFieldSubCommands.create().get(
                                BitFieldSubCommands.BitFieldType.unsigned(len)
                        ).valueAt(0)
                );
        if (CollUtils.isEmpty(result)) {
            return 0;
        }
        int num = result.get(0).intValue();
        // 2.定义一个计数器
        int count = 0;
        // 3.循环，与1做与运算，得到最后一个bit，判断是否为0，为0则终止，为1则继续
        while ((num & 1) == 1) {
            // 4.计数器+1
            count++;
            // 5.把数字右移一位，最后一位被舍弃，倒数第二位成了最后一位
            num >>>= 1;
        }
        return count;
    }
}
