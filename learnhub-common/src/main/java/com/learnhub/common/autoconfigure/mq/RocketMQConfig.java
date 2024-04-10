package com.learnhub.common.autoconfigure.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/9 9:08
 */
@Configuration
@ConditionalOnProperty(name = "rocketmq.enable", havingValue = "true")
public class RocketMQConfig {

    @Value("${rocketmq.nameserver}")
    private String nameServer;

    @Value("${rocketmq.producer.group:CLIENT_INNER_PRODUCER_GROUP}")
    private String producerGroup;

    @Value("${rocketmq.producer.send-message-timeout:3000}")
    private Integer sendMsgTimeout;

    @Value("${rocketmq.producer.max-message-size:4194304}")
    private Integer maxMessageSize;

    @Value("${rocketmq.producer.retry-times-when-send-failed:2}")
    private Integer retryTimesWhenSendFailed ;

    @Value("${rocketmq.producer.retry-times-when-send-async-failed:2}")
    private Integer retryTimesWhenSendAsyncFailed ;

    /**
     * 由于使用的Spring版本是3.0.0以上，与rocketMq不是很兼容，对于rocketMqTemplate
     * 的自动注入存在差异，如果不采用这种方式注入则会报出缺少bean的信息
     */
    @Bean("RocketMQTemplate")
    public RocketMQTemplate rocketMqTemplate(){
        RocketMQTemplate rocketMqTemplate = new RocketMQTemplate();
        DefaultMQProducer defaultMqProducer = new DefaultMQProducer();
        defaultMqProducer.setProducerGroup(producerGroup);
        defaultMqProducer.setNamesrvAddr(nameServer);
        defaultMqProducer.setSendMsgTimeout(this.sendMsgTimeout);
        defaultMqProducer.setMaxMessageSize(this.maxMessageSize);
        defaultMqProducer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        defaultMqProducer.setRetryTimesWhenSendAsyncFailed(this.retryTimesWhenSendAsyncFailed);
        rocketMqTemplate.setProducer(defaultMqProducer);
        return rocketMqTemplate;
    }
}
