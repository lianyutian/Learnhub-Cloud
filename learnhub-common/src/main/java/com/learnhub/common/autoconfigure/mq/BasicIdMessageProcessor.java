package com.learnhub.common.autoconfigure.mq;

import cn.hutool.core.lang.UUID;
import org.slf4j.MDC;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

import static com.learnhub.common.constants.Constant.REQUEST_ID_HEADER;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 17:26
 */
public class BasicIdMessageProcessor implements MessagePostProcessor {
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        String requestId = MDC.get(REQUEST_ID_HEADER);
        if (requestId == null) {
            requestId = UUID.randomUUID().toString(true);
        }
        // 写入RequestID标示
        message.getMessageProperties().setHeader(REQUEST_ID_HEADER, requestId);
        return message;
    }
}