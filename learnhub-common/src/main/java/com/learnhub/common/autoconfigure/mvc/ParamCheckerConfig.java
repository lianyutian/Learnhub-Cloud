package com.learnhub.common.autoconfigure.mvc;

import com.learnhub.common.autoconfigure.mvc.aspects.CheckerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ParamCheckerConfig
 *
 * @author lm
 * @since 2024-05-15 17:35:27
 * @version 1.0
 */
@Configuration
public class ParamCheckerConfig {
    @Bean
    public CheckerAspect checkerAspect(){
        return new CheckerAspect();
    }
}
