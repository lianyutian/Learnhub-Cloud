package com.learnhub.common.autoconfigure.mq;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 17:06
 */
@Slf4j
@RequiredArgsConstructor
public class RocketMQEnhanceTemplate {

    private final RocketMQTemplate template;

    /**
     * 构建目的地
     */
    public String buildDestination(String topic, String tag) {
        return topic + ":" + tag;
    }

    /**
     * 发送同步消息
     */
    public <T extends BaseMessage> SendResult send(String topic, String tag, T message) {
        // 注意分隔符
        return send(buildDestination(topic, tag), message);
    }

    public <T extends BaseMessage> SendResult send(String destination, T message) {
        // 设置业务键，此处根据公共的参数进行处理
        // 更多的其它基础业务处理...
        Message<T> sendMessage = MessageBuilder
                .withPayload(message)
                .setHeader(RocketMQHeaders.KEYS, message.getKey())
                .build();

        SendResult sendResult = template.syncSend(destination, sendMessage);

        // 此处为了方便查看给日志转了json，根据选择选择日志记录方式，例如ELK采集
        log.info("[{}]同步消息[{}]发送结果[{}]",
                destination,
                JSONObject.toJSON(message),
                JSONObject.toJSON(sendResult));

        return sendResult;
    }

    /**
     * 发送延迟消息
     */
    public <T extends BaseMessage> SendResult send(String topic, String tag, T message, int delayLevel) {
        return send(buildDestination(topic, tag), message, delayLevel);
    }

    public <T extends BaseMessage> SendResult send(String destination, T message, int delayLevel) {
        Message<T> sendMessage = MessageBuilder
                .withPayload(message)
                .setHeader(RocketMQHeaders.KEYS, message.getKey())
                .build();

        SendResult sendResult = template
                .syncSend(destination, sendMessage, 3000, delayLevel);

        log.info("[{}]延迟等级[{}]消息[{}]发送结果[{}]",
                destination,
                delayLevel,
                JSONObject.toJSON(message),
                JSONObject.toJSON(sendResult));

        return sendResult;
    }
}
