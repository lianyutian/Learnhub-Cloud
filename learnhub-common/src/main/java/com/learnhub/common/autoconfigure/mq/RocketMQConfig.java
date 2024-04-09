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
    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.name-server}")
    private String nameServer;

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
        rocketMqTemplate.setProducer(defaultMqProducer);
        return rocketMqTemplate;
    }
}
