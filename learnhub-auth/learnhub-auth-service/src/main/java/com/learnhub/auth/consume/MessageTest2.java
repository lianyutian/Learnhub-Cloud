package com.learnhub.auth.consume;

import com.learnhub.api.dto.remark.RemarkMQMessagesDTO;
import com.learnhub.common.autoconfigure.mq.EnhanceMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/8 20:05]
 */
@Slf4j
@Component
@RocketMQMessageListener(
        consumerGroup = "remark_group",
        topic = "like_record_topic",
        selectorExpression = "*",
        consumeThreadMax = 5 //默认是64个线程并发消息，配置 consumeThreadMax 参数指定并发消费线程数，避免太大导致资源不够
)
public class MessageTest2 extends EnhanceMessageHandler<RemarkMQMessagesDTO> implements RocketMQListener<RemarkMQMessagesDTO> {

    @Override
    protected void handleMessage(RemarkMQMessagesDTO message) throws Exception {
        System.out.println(message);
    }

    @Override
    protected void handleMaxRetriesExceeded(RemarkMQMessagesDTO message) {
        // 当超过指定重试次数消息时此处方法会被调用
        // 生产中可以进行回退或其他业务操作
        log.error("消息消费失败，请执行后续处理");
    }

    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        return false;
    }

    @Override
    public void onMessage(RemarkMQMessagesDTO remarkMQMessageDTO) {
        super.dispatchMessage(remarkMQMessageDTO);
    }
}
