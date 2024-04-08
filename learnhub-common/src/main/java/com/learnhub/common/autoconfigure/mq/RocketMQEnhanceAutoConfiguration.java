package com.learnhub.common.autoconfigure.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 17:16
 */
@Configuration
public class RocketMQEnhanceAutoConfiguration {
    /**
     * 注入增强的RocketMQEnhanceTemplate
     */
    @Bean
    public RocketMQEnhanceTemplate rocketMQEnhanceTemplate(RocketMQTemplate rocketMQTemplate) {
        return new RocketMQEnhanceTemplate(rocketMQTemplate);
    }

    /**
     * 解决RocketMQ Jackson不支持Java时间类型配置
     */
    @Bean
    @Primary
    public RocketMQMessageConverter enhanceRocketMQMessageConverter() {
        RocketMQMessageConverter converter = new RocketMQMessageConverter();
        CompositeMessageConverter compositeMessageConverter = (CompositeMessageConverter) converter.getMessageConverter();
        List<MessageConverter> messageConverterList = compositeMessageConverter.getConverters();
        for (MessageConverter messageConverter : messageConverterList) {
            if (messageConverter instanceof MappingJackson2MessageConverter jackson2MessageConverter) {
                ObjectMapper objectMapper = jackson2MessageConverter.getObjectMapper();
                objectMapper.registerModules(new JavaTimeModule());
            }
        }
        return converter;
    }
}
