package com.learnhub.common.autoconfigure.mvc;

import com.learnhub.common.autoconfigure.mvc.advice.CommonExceptionAdvice;
import com.learnhub.common.autoconfigure.mvc.advice.WrapperResponseBodyAdvice;
import com.learnhub.common.filters.RequestIdFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/1 22:08]
 */
@ConditionalOnClass({CommonExceptionAdvice.class, Filter.class})
@Configuration
public class MvcConfig {
    /**
     * 通用的ControllerAdvice异常处理器
     */
    @Bean
    public CommonExceptionAdvice commonExceptionAdvice() {
        return new CommonExceptionAdvice();
    }

    @Bean
    public RequestIdFilter requestIdFilter() {
        return new RequestIdFilter();
    }

//    @Bean
//    @ConditionalOnMissingClass("org.springframework.cloud.gateway.filter.GlobalFilter")
//    public WrapperResponseMessageConverter wrapperResponseMessageConverter(
//            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
//        return new WrapperResponseMessageConverter(mappingJackson2HttpMessageConverter);
//    }

    @Bean
    public WrapperResponseBodyAdvice wrapperResponseBodyAdvice() {
        return new WrapperResponseBodyAdvice();
    }
}
