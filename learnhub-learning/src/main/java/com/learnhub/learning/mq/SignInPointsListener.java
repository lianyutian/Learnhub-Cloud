package com.learnhub.learning.mq;

import com.learnhub.common.autoconfigure.mq.EnhanceMessageHandler;
import com.learnhub.learning.enums.PointsRecordType;
import com.learnhub.learning.mq.message.SignInMessage;
import com.learnhub.learning.service.IPointsRecordService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 签到积分监听
 *
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/15 20:22]
 */
@Component
@RocketMQMessageListener(
        consumerGroup = "signIn_point_group",
        topic = "learning_topic",
        selectorExpression = "sign_in",
        consumeThreadMax = 5 //默认是64个线程并发消息，配置 consumeThreadMax 参数指定并发消费线程数，避免太大导致资源不够
)
@RequiredArgsConstructor
public class SignInPointsListener extends EnhanceMessageHandler<SignInMessage> implements RocketMQListener<SignInMessage> {

    private final IPointsRecordService pointsRecordService;

    @Override
    protected void handleMessage(SignInMessage message) throws Exception {
        pointsRecordService.addPointsRecord(message.getUserId(), message.getPoints(), PointsRecordType.SIGN);
    }

    @Override
    protected void handleMaxRetriesExceeded(SignInMessage message) {

    }

    @Override
    protected boolean isRetry() {
        return false;
    }

    @Override
    protected boolean throwException() {
        return false;
    }

    @Override
    public void onMessage(SignInMessage message) {
        super.dispatchMessage(message);
    }
}
