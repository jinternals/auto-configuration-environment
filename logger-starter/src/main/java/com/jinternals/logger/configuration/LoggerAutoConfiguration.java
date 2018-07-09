package com.jinternals.logger.configuration;

import com.jinternals.logger.processor.LoggerInjectorBeanPostProcessor;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Logger.class)
public class LoggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoggerInjectorBeanPostProcessor getLoggerInjectorBeanPostProcessor(){
        return new LoggerInjectorBeanPostProcessor();
    }

}